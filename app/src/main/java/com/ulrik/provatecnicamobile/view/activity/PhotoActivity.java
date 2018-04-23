package com.ulrik.provatecnicamobile.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ulrik.provatecnicamobile.R;
import com.ulrik.provatecnicamobile.model.Photo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoActivity extends AppCompatActivity {

    public static final String EXTRA_PHOTO = "photo";

    @BindView(R.id.imagePhoto)
    ImageView imageViewPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);

        Photo photo = (Photo) getIntent().getSerializableExtra(EXTRA_PHOTO);
        Glide.with(this).load(photo.getUrl()).into(imageViewPhoto);
    }
}
