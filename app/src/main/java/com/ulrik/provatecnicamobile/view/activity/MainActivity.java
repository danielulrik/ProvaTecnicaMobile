package com.ulrik.provatecnicamobile.view.activity;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ulrik.provatecnicamobile.R;
import com.ulrik.provatecnicamobile.model.Post;
import com.ulrik.provatecnicamobile.view.fragments.PostsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements PostsFragment.OnPostListener {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_posts:
                    final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.container, new PostsFragment(), "PostsFragment");
                    ft.commit();
                case R.id.action_albuns:

                case R.id.action_todo:
            }
            return true;
        });
    }

    @Override
    public void onPostClicked(Post item) {
    }

}
