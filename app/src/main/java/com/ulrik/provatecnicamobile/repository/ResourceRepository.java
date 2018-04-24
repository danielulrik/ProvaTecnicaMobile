package com.ulrik.provatecnicamobile.repository;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.rx2.language.RXSQLite;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.FastStoreModelTransaction;
import com.ulrik.provatecnicamobile.App;
import com.ulrik.provatecnicamobile.cache.Cache;
import com.ulrik.provatecnicamobile.database.AppDatabase;
import com.ulrik.provatecnicamobile.model.Album;
import com.ulrik.provatecnicamobile.model.Comment;
import com.ulrik.provatecnicamobile.model.Comment_Table;
import com.ulrik.provatecnicamobile.model.Photo;
import com.ulrik.provatecnicamobile.model.Photo_Table;
import com.ulrik.provatecnicamobile.model.Post;
import com.ulrik.provatecnicamobile.model.Todo;
import com.ulrik.provatecnicamobile.model.User;
import com.ulrik.provatecnicamobile.service.ServiceFactory;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ResourceRepository {

    private final static DatabaseWrapper database = FlowManager.getWritableDatabase(AppDatabase.NAME);

    public Single<Boolean> syncDb() {
        return Single.zip(
                ServiceFactory.getApi().getPosts().subscribeOn(Schedulers.newThread()),
                ServiceFactory.getApi().getComments().subscribeOn(Schedulers.newThread()),
                ServiceFactory.getApi().getTodoList().subscribeOn(Schedulers.newThread()),
                ServiceFactory.getApi().getAlbums().subscribeOn(Schedulers.newThread()),
                ServiceFactory.getApi().getPhotos().subscribeOn(Schedulers.newThread()), (posts, comments, todos, albums, photos) -> {
                    putAll(posts, comments, todos, albums, photos);
                    return true;
                });
    }

    public Single<List<Post>> getPosts() {
        if (!App.isSynced() && !App.isSyncing()) {
            App.getPreferences().edit().putBoolean("isSyncing", true).apply();
            return ServiceFactory.getApi().getUsers().flatMap((Function<List<User>, Single<List<Post>>>) users -> {
                Cache.setUsers(users);
                putAll(users, User.class);
                return ServiceFactory.getApi().getPosts();
            });
        }
        Cache.setUsers(SQLite.select().from(User.class).queryList());
        return RXSQLite.rx(SQLite.select().from(Post.class)).queryList();
    }

    public Single<List<Album>> getAlbums() {
        if (!App.isSynced()) return ServiceFactory.getApi().getAlbums();
        return RXSQLite.rx(SQLite.select().from(Album.class)).queryList();
    }

    public Single<List<Photo>> getPhotos(int albumId) {
        if (!App.isSynced()) return ServiceFactory.getApi().getPhotosByAlbum(albumId);
        return RXSQLite.rx(SQLite.select().from(Photo.class).where(Photo_Table.albumId.eq(albumId))).queryList();
    }

    public Single<List<Todo>> getTodoList() {
        if (!App.isSynced()) return ServiceFactory.getApi().getTodoList();
        return RXSQLite.rx(SQLite.select().from(Todo.class)).queryList();
    }

    public Single<List<Comment>> getComments(int postId) {
        if (!App.isSynced()) return ServiceFactory.getApi().getCommentsByPost(postId);
        return RXSQLite.rx(SQLite.select().from(Comment.class)
                .where(Comment_Table.postId.eq(postId))).queryList();
    }

    private void putAll(List<Post> posts, List<Comment> comments,
                        List<Todo> todos, List<Album> albums, List<Photo> photos) {
        putAll(posts, Post.class);
        putAll(comments, Comment.class);
        putAll(todos, Todo.class);
        putAll(albums, Album.class);
        putAll(photos, Photo.class);
    }

    private <T> void putAll(List<T> modelList, Class<T> type) {
        FastStoreModelTransaction
                .insertBuilder(FlowManager.getModelAdapter(type))
                .addAll(modelList)
                .build().execute(database);
    }
}
