package com.ulrik.provatecnicamobile.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ulrik.provatecnicamobile.R;
import com.ulrik.provatecnicamobile.model.Album;
import com.ulrik.provatecnicamobile.view.fragments.AlbumsFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlbumsRecyclerViewAdapter extends RecyclerView.Adapter<AlbumsRecyclerViewAdapter.ViewHolder> {

    private final List<Album> albums;
    private final AlbumsFragment.OnAlbumListener mListener;

    public AlbumsRecyclerViewAdapter(List<Album> items, AlbumsFragment.OnAlbumListener listener) {
        albums = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = albums.get(position);
        holder.textViewTitle.setText(albums.get(position).getTitle());
        holder.textViewUser.setText(albums.get(position).getUser());

        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                mListener.onAlbumClicked(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textViewUser)
        TextView textViewUser;
        @BindView(R.id.textViewTitle)
        TextView textViewTitle;

        final View mView;
        Album mItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mView = view;
        }
    }
}
