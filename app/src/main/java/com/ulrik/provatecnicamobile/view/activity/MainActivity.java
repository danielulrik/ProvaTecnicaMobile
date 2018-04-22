package com.ulrik.provatecnicamobile.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.ulrik.provatecnicamobile.R;
import com.ulrik.provatecnicamobile.model.Album;
import com.ulrik.provatecnicamobile.model.Post;
import com.ulrik.provatecnicamobile.model.Todo;
import com.ulrik.provatecnicamobile.view.fragments.AlbumsFragment;
import com.ulrik.provatecnicamobile.view.fragments.PostsFragment;
import com.ulrik.provatecnicamobile.view.fragments.TodoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements PostsFragment.OnPostListener,
        AlbumsFragment.OnAlbumListener, TodoFragment.OnTodoListener {

    private static final String POSTS_TAG = "PostsFragment";
    private static final String ALBUMS_TAG = "AlbumsFragment";
    private static final String TODO_TAG = "TodoFragment";

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;
    private Fragment postsFragment;
    private Fragment albumsFragment;
    private Fragment todoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initListeners();

        postsFragment = new PostsFragment();
        albumsFragment = new AlbumsFragment();
        todoFragment = new AlbumsFragment();

        loadFragment(postsFragment, POSTS_TAG);
    }

    private void initListeners() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_posts:
                    loadFragment(postsFragment, POSTS_TAG);
                    return true;
                case R.id.action_albuns:
                    loadFragment(albumsFragment, ALBUMS_TAG);
                    return true;
                case R.id.action_todo:
                    loadFragment(todoFragment, TODO_TAG);
                    return true;
            }
            return false;
        });
    }

    private void loadFragment(Fragment fragment, String tag) {
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }

    @Override
    public void onPostClicked(Post item) {
        Intent intent = new Intent(this, PostDetailActivity.class);
        intent.putExtra(PostDetailActivity.EXTRA_POST, item);
        startActivity(intent);
    }


    @Override
    public void onAlbumClicked(Album item) {
        Intent intent = new Intent(this, PhotosActivity.class);
        intent.putExtra(PhotosActivity.EXTRA_ALBUM, item);
        startActivity(intent);
    }

    @Override
    public void onTodoClicked(Todo item) {

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.text_atencao)
                .setMessage(R.string.text_deseja_sair)
                .setPositiveButton(R.string.text_sim, (dialog, which) -> finish())
                .setNegativeButton(R.string.text_nao, null).show();
    }
}
