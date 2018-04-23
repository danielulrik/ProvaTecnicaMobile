package com.ulrik.provatecnicamobile.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ulrik.provatecnicamobile.R;
import com.ulrik.provatecnicamobile.model.Post;
import com.ulrik.provatecnicamobile.view.SimpleDividerItemDecoration;
import com.ulrik.provatecnicamobile.view.adapter.CommentsRecyclerViewAdapter;
import com.ulrik.provatecnicamobile.viewmodel.ResourcesViewModel;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

public class PostDetailActivity extends AppCompatActivity {

    public static final String EXTRA_POST = "post";
    @BindView(R.id.recyclerViewComments)
    RecyclerView recyclerView;
    @BindView(R.id.textViewUser)
    TextView textViewUser;
    @BindView(R.id.textViewTitle)
    TextView textViewTitle;
    @BindView(R.id.textViewBody)
    TextView textViewBody;
    @BindView(R.id.textViewCommentsCount)
    TextView textViewCommentsCount;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        ButterKnife.bind(this);
        Post post = (Post) getIntent().getSerializableExtra(EXTRA_POST);
        load(post);
    }

    private void load(Post post) {
        textViewUser.setText(post.getUser());
        textViewTitle.setText(post.getTitle());
        textViewBody.setText(post.getBody());

        ResourcesViewModel resourcesViewModel = new ResourcesViewModel();
        disposable = resourcesViewModel.getComments(post.getId()).subscribe(comments -> {
            textViewCommentsCount.setText(String.format(Locale.getDefault(), "%d", comments.size()));
            recyclerView.setLayoutManager(new LinearLayoutManager(PostDetailActivity.this));
            recyclerView.addItemDecoration(new SimpleDividerItemDecoration(PostDetailActivity.this));
            recyclerView.setAdapter(new CommentsRecyclerViewAdapter(comments));
            progressBar.setVisibility(View.GONE);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
