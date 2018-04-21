package com.ulrik.provatecnicamobile.view.fragments;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.ulrik.provatecnicamobile.model.Album;
import com.ulrik.provatecnicamobile.model.Todo;
import com.ulrik.provatecnicamobile.view.adapter.AlbumsRecyclerViewAdapter;
import com.ulrik.provatecnicamobile.view.adapter.TodoRecyclerViewAdapter;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodoFragment extends ResourceFragment {

    private OnTodoListener mListener;
    private Disposable disposable;

    public TodoFragment() {
        // Required empty public constructor
    }

    public interface OnTodoListener {
        void onTodoClicked(Todo item);
    }

    public void load() {
        disposable = resourcesViewModel.getTodoList().subscribeWith(new DisposableObserver<List<Todo>>() {
            @Override
            public void onNext(List<Todo> todoList) {
                Context context = getContext();
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(new TodoRecyclerViewAdapter(todoList, mListener));
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTodoListener) {
            mListener = (OnTodoListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnTodoListener");
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
