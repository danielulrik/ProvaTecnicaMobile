package com.ulrik.provatecnicamobile.service;

import com.ulrik.provatecnicamobile.model.Post;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FakeApi {

    String URL_BASE = "https://jsonplaceholder.typicode.com/";

    @GET("posts")
    Observable<List<Post>> getPosts();

}
