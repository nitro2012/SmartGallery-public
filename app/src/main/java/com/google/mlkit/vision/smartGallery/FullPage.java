package com.google.mlkit.vision.smartGallery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.File;

public class FullPage extends AppCompatActivity {
    private ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_page);
        image=findViewById(R.id.imageV);



        File im = new File(getIntent().getStringExtra("image"));
        Glide.with(FullPage.this).load(im).into(image);

    }
}