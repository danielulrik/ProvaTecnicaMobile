package com.ulrik.provatecnicamobile.view.fragments;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.ulrik.provatecnicamobile.model.Post;
import com.ulrik.provatecnicamobile.view.adapter.PostsRecyclerViewAdapter;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostsFragment extends ResourceFragment {

    private OnPostListener mListener;
    private Disposable disposable;


    public PostsFragment() {
    }

    public interface OnPostListener {
        void onPostClicked(Post item);
    }

    public void load() {
        disposable = resourcesViewModel.getPosts().subscribe(new Consumer<List<Post>>() {
            @Override
            public void accept(List<Post> posts) throws Exception {
                Context context = getContext();
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(new PostsRecyclerViewAdapter(posts, mListener));
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPostListener) {
            mListener = (OnPostListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnPostListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

}
