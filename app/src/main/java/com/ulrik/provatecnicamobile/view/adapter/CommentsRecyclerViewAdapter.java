package com.ulrik.provatecnicamobile.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ulrik.provatecnicamobile.R;
import com.ulrik.provatecnicamobile.model.Comment;
import com.ulrik.provatecnicamobile.model.Post;
import com.ulrik.provatecnicamobile.view.fragments.PostsFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentsRecyclerViewAdapter extends RecyclerView.Adapter<CommentsRecyclerViewAdapter.ViewHolder> {

    private final List<Comment> comments;

    public CommentsRecyclerViewAdapter(List<Comment> items) {
        comments = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = comments.get(position);
        holder.textViewName.setText(comments.get(position).getName());
        holder.textViewEmail.setText(comments.get(position).getEmail());
        holder.textViewBody.setText(comments.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textViewName)
        TextView textViewName;
        @BindView(R.id.textViewEmail)
        TextView textViewEmail;
        @BindView(R.id.textViewBody)
        TextView textViewBody;

        final View mView;
        public Comment mItem;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mView = view;
        }
    }
}
