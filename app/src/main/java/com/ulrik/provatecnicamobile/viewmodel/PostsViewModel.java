package com.ulrik.provatecnicamobile.viewmodel;

import com.ulrik.provatecnicamobile.model.Post;
import com.ulrik.provatecnicamobile.service.ServiceFactory;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PostsViewModel {

    public Observable<List<Post>> getPosts() {
        // TODO GET DO DB, SE NÃO ENCONTRAR PEGAR DA API
        // TODO PERSIST BEFORE RETURNING
        return ServiceFactory.getApi().getPosts().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
