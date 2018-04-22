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
import retrofit2.http.Query;

public interface FakeApi {

    String URL_BASE = "https://jsonplaceholder.typicode.com/";

    @GET("users")
    Single<List<User>> getUsers();

    @GET("posts")
    Single<List<Post>> getPosts();

    @GET("comments")
    Single<List<Comment>> getComments();

    @GET("todos")
    Single<List<Todo>> getTodoList();

    @GET("albums")
    Single<List<Album>> getAlbums();

    @GET("photos")
    Single<List<Photo>> getPhotos();

}
