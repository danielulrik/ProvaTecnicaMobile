package com.ulrik.provatecnicamobile.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ulrik.provatecnicamobile.R;
import com.ulrik.provatecnicamobile.model.Album;
import com.ulrik.provatecnicamobile.model.Todo;
import com.ulrik.provatecnicamobile.view.fragments.AlbumsFragment;
import com.ulrik.provatecnicamobile.view.fragments.TodoFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodoRecyclerViewAdapter extends RecyclerView.Adapter<TodoRecyclerViewAdapter.ViewHolder> {

    private final List<Todo> todoList;
    private final TodoFragment.OnTodoListener mListener;

    public TodoRecyclerViewAdapter(List<Todo> items, TodoFragment.OnTodoListener listener) {
        todoList = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = todoList.get(position);
        holder.textViewUser.setText(todoList.get(position).getUser());
        holder.textViewTitle.setText(todoList.get(position).getTitle());
        holder.textViewStatus.setText(todoList.get(position).getStatus());

        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                // mListener.onListFragmentInteraction(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textViewUser)
        TextView textViewUser;
        @BindView(R.id.textViewTitle)
        TextView textViewTitle;
        @BindView(R.id.textViewStatus)
        TextView textViewStatus;

        final View mView;
        public Todo mItem;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mView = view;
        }
    }
}
