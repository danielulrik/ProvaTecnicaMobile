package com.ulrik.provatecnicamobile.repository;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.rx2.language.RXSQLite;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.FastStoreModelTransaction;
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
import java.util.concurrent.Callable;

import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function6;
import io.reactivex.schedulers.Schedulers;

public class ResourceRepository {

    private final DatabaseWrapper database = FlowManager.getWritableDatabase(AppDatabase.NAME);

    private Single<Boolean> syncDb() {
        if (SQLite.select().from(Post.class).count() > 0) {
            if (Cache.getUsers().size() == 0) {
                Cache.setUsers(SQLite.select().from(User.class).queryList());
            }
            return Single.just(true);
        }
        return Single.zip(ServiceFactory.getApi().getUsers().subscribeOn(Schedulers.newThread()),
                ServiceFactory.getApi().getPosts().subscribeOn(Schedulers.newThread()),
                ServiceFactory.getApi().getComments().subscribeOn(Schedulers.newThread()),
                ServiceFactory.getApi().getTodoList().subscribeOn(Schedulers.newThread()),
                ServiceFactory.getApi().getAlbums().subscribeOn(Schedulers.newThread()),
                ServiceFactory.getApi().getPhotos().subscribeOn(Schedulers.newThread()), new Function6<List<User>, List<Post>, List<Comment>, List<Todo>, List<Album>, List<Photo>, Boolean>() {
                    @Override
                    public Boolean apply(List<User> users, List<Post> posts, List<Comment> comments, List<Todo> todos, List<Album> albums, List<Photo> photos) throws Exception {
                        Cache.setUsers(users);
                        putAll(users, posts, comments, todos, albums, photos);
                        // putAllListObservable(users, "users", User.class).concatWith(putAllListObservable(posts, "posts", Post.class));
                        return true;
                    }
                });
    }

    public Single<List<Post>> getPosts() {
        return syncDb().flatMap((Function<Boolean, SingleSource<List<Post>>>) aBoolean ->
                RXSQLite.rx(SQLite.select().from(Post.class)).queryList());
    }

    public Single<List<Album>> getAlbums() {
        return RXSQLite.rx(SQLite.select().from(Album.class)).queryList();
    }

    public Single<List<Photo>> getPhotos(int albumId) {
        return RXSQLite.rx(SQLite.select().from(Photo.class).where(Photo_Table.albumId.eq(albumId))).queryList();
    }

    public Single<List<Todo>> getTodoList() {
        return RXSQLite.rx(SQLite.select().from(Todo.class)).queryList();
    }

    public Single<List<Comment>> getComments(int postId) {
        return RXSQLite.rx(SQLite.select().from(Comment.class)
                .where(Comment_Table.postId.eq(postId))).queryList();
    }

    private void putAll(List<User> users, List<Post> posts, List<Comment> comments,
                        List<Todo> todos, List<Album> albums, List<Photo> photos) {

        putAll(users, User.class);
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

//    private <T> Maybe<String> putAllListObservable(List<T> modelList, String name, Class<T> type) {
//        return Maybe.create(emitter -> {
//            FastStoreModelTransaction
//                    .insertBuilder(FlowManager.getModelAdapter(type))
//                    .addAll(modelList)
//                    .build().execute(database);
//
//            if (!emitter.isDisposed()) {
//                emitter.onSuccess(name);
//            }
//        });
//    }

//    private <T> Single<Boolean> putAllListObservable(List<T> modelList, String name, Class<T> type) {
//        return Single.create(new SingleOnSubscribe<Boolean>() {
//            @Override
//            public void subscribe(SingleEmitter<Boolean> emitter) throws Exception {
//                FastStoreModelTransaction
//                        .insertBuilder(FlowManager.getModelAdapter(type))
//                        .addAll(modelList)
//                        .build().execute(database);
//                if (!emitter.isDisposed()) {
//                    emitter.onSuccess(true);
//                }
//            }
//        });
//    }

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
