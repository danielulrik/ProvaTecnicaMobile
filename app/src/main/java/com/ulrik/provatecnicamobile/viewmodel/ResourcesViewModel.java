package com.ulrik.provatecnicamobile.viewmodel;

import com.ulrik.provatecnicamobile.model.Album;
import com.ulrik.provatecnicamobile.model.Comment;
import com.ulrik.provatecnicamobile.model.Post;
import com.ulrik.provatecnicamobile.model.Todo;
import com.ulrik.provatecnicamobile.repository.ResourceRepository;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ResourcesViewModel {

    private final ResourceRepository resourceRepository = new ResourceRepository();

    public Single<List<Post>> getPosts() {
        return resourceRepository.getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<Album>> getAlbums() {
        return resourceRepository.getAlbums()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<Todo>> getTodoList() {
        return resourceRepository.getTodoList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<Comment>> getComments(Post post) {
        return resourceRepository.getComments(post)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
