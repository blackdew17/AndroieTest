package com.eskim.picturesample.view;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.eskim.picturesample.R;
import com.eskim.picturesample.api.PictureInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    @Nullable
    @BindView(R.id.pictureImageView)
    ImageView pictureImageView;

    public final static String KEY_PICTURE = "key_picture";

    private PictureInfo pictureInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        getUserFromIntent();
    }

    private void getUserFromIntent() {
        pictureInfo = (PictureInfo) getIntent().getSerializableExtra(KEY_PICTURE);
        setTitle(pictureInfo.getAuthor());
        initPictureInfo(pictureInfo);
    }

    private void initPictureInfo(PictureInfo picture) {
        Glide.with(getApplicationContext())
                .load(picture.getDownloadUrl())
                .into(pictureImageView);
    }
}