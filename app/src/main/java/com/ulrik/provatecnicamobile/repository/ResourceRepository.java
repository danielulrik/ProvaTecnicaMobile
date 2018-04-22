package com.ulrik.provatecnicamobile.repository;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.rx2.language.RXSQLite;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.FastStoreModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.ulrik.provatecnicamobile.cache.Cache;
import com.ulrik.provatecnicamobile.database.AppDatabase;
import com.ulrik.provatecnicamobile.model.Album;
import com.ulrik.provatecnicamobile.model.Comment;
import com.ulrik.provatecnicamobile.model.Photo;
import com.ulrik.provatecnicamobile.model.Post;
import com.ulrik.provatecnicamobile.model.Todo;
import com.ulrik.provatecnicamobile.model.User;
import com.ulrik.provatecnicamobile.service.ServiceFactory;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function6;

public class ResourceRepository {

    private final DatabaseWrapper database = FlowManager.getWritableDatabase(AppDatabase.NAME);

    private Single<Boolean> syncDb() {
        if (SQLite.select().from(Post.class).count() > 0) {
            if (Cache.getUsers().size() == 0) {
                Cache.setUsers(SQLite.select().from(User.class).queryList());
            }
            return Single.just(true);
        }
        return Single.zip(ServiceFactory.getApi().getUsers(),
                ServiceFactory.getApi().getPosts(),
                ServiceFactory.getApi().getComments(),
                ServiceFactory.getApi().getTodoList(),
                ServiceFactory.getApi().getAlbums(),
                ServiceFactory.getApi().getPhotos(), (users, posts, comments, todoList, albums, photos) -> {
                    Cache.setUsers(users);
                    putAll(users, posts, comments, todoList, albums, photos);
                    return true;
                });
    }

    public Single<List<Post>> getPosts() {
        return syncDb().flatMap((Function<Boolean, SingleSource<List<Post>>>) aBoolean ->
                RXSQLite.rx(SQLite.select().from(Post.class)).queryList());
    }

    public Single<List<Album>> getAlbums() {
        return RXSQLite.rx(SQLite.select().from(Album.class)).queryList();
    }

    public Single<List<Todo>> getTodoList() {
        return RXSQLite.rx(SQLite.select().from(Todo.class)).queryList();
    }

    private void putAll(List<User> users, List<Post> posts, List<Comment> comments,
                        List<Todo> todos, List<Album> albums, List<Photo> photos) {
        putAllUsers(users);
        putAllPosts(posts);
        putAllComments(comments);
        putAllTodo(todos);
        putAllAlbums(albums);
        putAllPhotos(photos);
    }

    private void putAllPosts(List<Post> modelList) {
        FastStoreModelTransaction
                .insertBuilder(FlowManager.getModelAdapter(Post.class))
                .addAll(modelList)
                .build().execute(database);
    }

    private void putAllAlbums(List<Album> modelList) {
        FastStoreModelTransaction
                .insertBuilder(FlowManager.getModelAdapter(Album.class))
                .addAll(modelList)
                .build().execute(database);
    }

    private void putAllPhotos(List<Photo> modelList) {
        FastStoreModelTransaction
                .insertBuilder(FlowManager.getModelAdapter(Photo.class))
                .addAll(modelList)
                .build().execute(database);
    }

    private void putAllTodo(List<Todo> modelList) {
        FastStoreModelTransaction
                .insertBuilder(FlowManager.getModelAdapter(Todo.class))
                .addAll(modelList)
                .build().execute(database);
    }

    private void putAllUsers(List<User> modelList) {
        FastStoreModelTransaction
                .insertBuilder(FlowManager.getModelAdapter(User.class))
                .addAll(modelList)
                .build().execute(database);
    }

    private void putAllComments(List<Comment> modelList) {
        FastStoreModelTransaction
                .insertBuilder(FlowManager.getModelAdapter(Comment.class))
                .addAll(modelList)
                .build().execute(database);
    }

}
