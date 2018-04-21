package com.ulrik.provatecnicamobile.viewmodel;

import com.ulrik.provatecnicamobile.model.Album;
import com.ulrik.provatecnicamobile.model.Post;
import com.ulrik.provatecnicamobile.model.Todo;
import com.ulrik.provatecnicamobile.service.ServiceFactory;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ResourcesViewModel {

    // TODO GET DO DB, SE NÃO ENCONTRAR PEGAR DA API
    // TODO PERSIST BEFORE RETURNING
    public Observable<List<Post>> getPosts() {
        return ServiceFactory.getApi().getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Album>> getAlbums() {
        return ServiceFactory.getApi().getAlbums()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Todo>> getTodoList() {
        return ServiceFactory.getApi().getTodoList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
