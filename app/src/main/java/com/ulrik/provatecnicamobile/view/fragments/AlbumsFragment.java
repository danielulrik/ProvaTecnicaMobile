package com.ulrik.provatecnicamobile.view.fragments;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.ulrik.provatecnicamobile.model.Album;
import com.ulrik.provatecnicamobile.view.adapter.AlbumsRecyclerViewAdapter;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumsFragment extends ResourceFragment {

    private OnAlbumListener mListener;
    private Disposable disposable;

    public AlbumsFragment() {
        // Required empty public constructor
    }

    public interface OnAlbumListener {
        void onAlbumClicked(Album item);
    }

    public void load() {
        disposable = resourcesViewModel.getAlbums().subscribe(new Consumer<List<Album>>() {
            @Override
            public void accept(List<Album> albums) throws Exception {
                Context context = getContext();
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(new AlbumsRecyclerViewAdapter(albums, mListener));
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAlbumListener) {
            mListener = (OnAlbumListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnAlbumListener");
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
