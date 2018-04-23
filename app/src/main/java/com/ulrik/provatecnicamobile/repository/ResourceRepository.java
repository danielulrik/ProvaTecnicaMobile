package com.ulrik.provatecnicamobile.repository;

import android.util.Log;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.rx2.language.RXSQLite;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.FastStoreModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;
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

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function6;
import io.reactivex.schedulers.Schedulers;

public class ResourceRepository {

    private final DatabaseDefinition database = FlowManager.getDatabase(AppDatabase.NAME);

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
                ServiceFactory.getApi().getPhotos().subscribeOn(Schedulers.newThread()), (users, posts, comments, todos, albums, photos) -> {
                    Cache.setUsers(users);
                    // putAll(users, posts, comments, todos, albums, photos);
                    // putAllListObservable(users, "users", User.class).concatWith(putAllListObservable(posts, "posts", Post.class));
                    return new Resources(users, posts, comments, todos, albums, photos);
                }).flatMap((Function<Resources, Single<Boolean>>) resources ->
                Single.zip(put(resources.users, User.class, ""),
                        put(resources.posts, Post.class, ""),
                        put(resources.comments, Comment.class, ""),
                        put(resources.todos, Todo.class, ""),
                        put(resources.albums, Album.class, ""),
                        put(resources.photos, Photo.class, ""), new Function6<String, String, String, String, String, String, Boolean>() {
                            @Override
                            public Boolean apply(String s, String s2, String s3, String s4, String s5, String s6) throws Exception {
                                return true;
                            }
                        }));
    }

    public <T> Single<String> put(List<T> list, Class<T> type, String message) {
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> emitter) throws Exception {
                Log.i("thread", Thread.currentThread().getName() + " type: " + type);
                putAll(list, type, emitter);
                Log.i("thread", Thread.currentThread().getName() + " type: " + type);
//                if (!emitter.isDisposed()) {
//                    // emitter.onSuccess(message);
//                }
            }
        });
    }

    private <T> void putAll(List<T> modelList, Class<T> type, SingleEmitter<String> emitter) {
        Transaction transaction = database.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                FastStoreModelTransaction
                .insertBuilder(FlowManager.getModelAdapter(type))
                .addAll(modelList)
                .build().execute(databaseWrapper);
            }
        }).build();
        transaction.execute();

//        FastStoreModelTransaction
//                .insertBuilder(FlowManager.getModelAdapter(type))
//                .addAll(modelList)
//                .build().execute(database);
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

//        putAll(users, User.class);
//        putAll(posts, Post.class);
//        putAll(comments, Comment.class);
//        putAll(todos, Todo.class);
//        putAll(albums, Album.class);
//        putAll(photos, Photo.class);
    }

    private static class Resources {
        List<User> users;
        List<Post> posts;
        List<Comment> comments;
        List<Todo> todos;
        List<Album> albums;
        List<Photo> photos;

        public Resources(List<User> users, List<Post> posts, List<Comment> comments, List<Todo> todos, List<Album> albums, List<Photo> photos) {
            this.users = users;
            this.posts = posts;
            this.comments = comments;
            this.todos = todos;
            this.albums = albums;
            this.photos = photos;
        }

        public Resources() {
        }
    }
}
