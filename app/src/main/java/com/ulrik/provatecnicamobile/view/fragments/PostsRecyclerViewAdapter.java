package com.ulrik.provatecnicamobile.view.fragments;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulrik.provatecnicamobile.R;
import com.ulrik.provatecnicamobile.model.Post;

import java.util.List;

public class PostsRecyclerViewAdapter extends RecyclerView.Adapter<PostsRecyclerViewAdapter.ViewHolder> {

    private final List<Post> mValues;
    private final PostsFragment.OnPostListener mListener;

    public PostsRecyclerViewAdapter(List<Post> items, PostsFragment.OnPostListener listener) {
        mValues = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        // holder.mIdView.setText(mValues.get(position).id);
        // holder.mContentView.setText(mValues.get(position).content);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public Post mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
        }
    }
}
