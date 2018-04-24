package com.ulrik.provatecnicamobile.service;

import com.ulrik.provatecnicamobile.model.Album;
import com.ulrik.provatecnicamobile.model.Comment;
import com.ulrik.provatecnicamobile.model.Photo;
import com.ulrik.provatecnicamobile.model.Post;
import com.ulrik.provatecnicamobile.model.Todo;
import com.ulrik.provatecnicamobile.model.User;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FakeApi {

    String URL_BASE = "https://jsonplaceholder.typicode.com/";

    @GET("users")
    Single<List<User>> getUsers();

    @GET("users/{id}")
    Single<User> getUser(@Path("id") int id);

    @GET("posts")
    Single<List<Post>> getPosts();

    @GET("comments")
    Single<List<Comment>> getComments();

    @GET("posts/{postId}/comments")
    Single<List<Comment>> getCommentsByPost(@Path("postId") int postId);

    @GET("todos")
    Single<List<Todo>> getTodoList();

    @GET("albums")
    Single<List<Album>> getAlbums();

    @GET("photos")
    Single<List<Photo>> getPhotos();

    @GET("photos")
    Single<List<Photo>> getPhotosByAlbum(@Query("albumId") int albumId);

}
