package com.ulrik.provatecnicamobile.view.fragments;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ulrik.provatecnicamobile.R;
import com.ulrik.provatecnicamobile.model.Post;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostsRecyclerViewAdapter extends RecyclerView.Adapter<PostsRecyclerViewAdapter.ViewHolder> {

    private final List<Post> posts;
    private final PostsFragment.OnPostListener mListener;

    public PostsRecyclerViewAdapter(List<Post> items, PostsFragment.OnPostListener listener) {
        posts = items;
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
        holder.mItem = posts.get(position);
        holder.textViewTitle.setText(posts.get(position).getTitle());
        holder.textViewBody.setText(posts.get(position).getBody());
        holder.textViewSubtitle.setText(String.format(Locale.getDefault(), "User id: %d", posts.get(position).getUserId()));

        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                // mListener.onListFragmentInteraction(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textViewTitle)
        TextView textViewTitle;
        @BindView(R.id.textViewBody)
        TextView textViewBody;
        @BindView(R.id.textViewSubtitle)
        TextView textViewSubtitle;

        final View mView;
        public Post mItem;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mView = view;
        }
    }
}
