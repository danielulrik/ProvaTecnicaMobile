package com.ulrik.provatecnicamobile.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.ulrik.provatecnicamobile.R;
import com.ulrik.provatecnicamobile.model.Album;
import com.ulrik.provatecnicamobile.model.Photo;
import com.ulrik.provatecnicamobile.view.adapter.PhotosAdapter;
import com.ulrik.provatecnicamobile.viewmodel.ResourcesViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class PhotosActivity extends AppCompatActivity {

    public static final String EXTRA_ALBUM = "album";
    @BindView(R.id.gridView)
    GridView gridView;
    private Disposable disposable;
    private PhotosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        ButterKnife.bind(this);

        Album album = (Album) getIntent().getSerializableExtra(EXTRA_ALBUM);
        loadPhotos(album);
    }

    @OnItemClick(R.id.gridView)
    public void onItemClickListener(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(view.getContext(), PhotoActivity.class);
        intent.putExtra(PhotoActivity.EXTRA_PHOTO, adapter.getData().get(position));
        startActivity(intent);
    }

    private void loadPhotos(Album album) {
        ResourcesViewModel resourcesViewModel = new ResourcesViewModel();
        disposable = resourcesViewModel.getPhotos(album.getId()).subscribe(photos ->
                gridView.setAdapter(adapter = new PhotosAdapter(PhotosActivity.this,
                R.layout.photo_adapter, photos)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
