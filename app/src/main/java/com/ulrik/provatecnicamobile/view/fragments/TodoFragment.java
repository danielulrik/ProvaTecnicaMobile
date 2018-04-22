package com.ulrik.provatecnicamobile.view.fragments;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.ulrik.provatecnicamobile.model.Todo;
import com.ulrik.provatecnicamobile.view.adapter.TodoRecyclerViewAdapter;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

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
        disposable = resourcesViewModel.getTodoList().subscribe(todos -> {
            Context context = getContext();
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new TodoRecyclerViewAdapter(todos));
            progressBar.setVisibility(View.GONE);
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
