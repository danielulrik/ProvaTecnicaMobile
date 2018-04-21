package com.ulrik.provatecnicamobile.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ulrik.provatecnicamobile.R;
import com.ulrik.provatecnicamobile.model.Post;
import com.ulrik.provatecnicamobile.viewmodel.PostsViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class PostsFragment extends Fragment {

    private OnPostListener mListener;
    private PostsViewModel postsViewModel;
    private View view;
    private Disposable disposable;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    public PostsFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        if (postsViewModel != null) {
            loadPosts();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_post_list, container, false);
        ButterKnife.bind(this, view);
        postsViewModel = new PostsViewModel();
        loadPosts();
        return view;
    }

    private void loadPosts() {
        disposable = postsViewModel.getPosts().subscribeWith(new DisposableObserver<List<Post>>() {
            @Override
            public void onNext(List<Post> posts) {
                Context context = view.getContext();
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(new PostsRecyclerViewAdapter(posts, mListener));
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
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

    public interface OnPostListener {
        void onPostClicked(Post item);
    }

}
