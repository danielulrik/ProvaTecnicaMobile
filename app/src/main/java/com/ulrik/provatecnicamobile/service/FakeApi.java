package com.ulrik.provatecnicamobile.service;

import com.ulrik.provatecnicamobile.model.Album;
import com.ulrik.provatecnicamobile.model.Comment;
import com.ulrik.provatecnicamobile.model.Photo;
import com.ulrik.provatecnicamobile.model.Post;
import com.ulrik.provatecnicamobile.model.Todo;
import com.ulrik.provatecnicamobile.model.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FakeApi {

    String URL_BASE = "https://jsonplaceholder.typicode.com/";

    @GET("users")
    Observable<List<User>> getUsers();

    @GET("posts")
    Observable<List<Post>> getPosts();

    @GET("comments")
    Observable<List<Comment>> getComments();

    @GET("todos")
    Observable<List<Todo>> getTodoList();

    @GET("albums")
    Observable<List<Album>> getAlbums();

    @GET("photos")
    Observable<List<Photo>> getPhotos();

}
