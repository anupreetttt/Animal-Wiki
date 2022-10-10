package com.example.animalwikipedia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class PreviewImage extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);              //To go back to the previous page from the image preview activity, using back button on the task bar
        Intent intent = getIntent();
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(intent.getIntExtra(MyAdapter.EXTRA_RES_ID, 0));
        setContentView(imageView);
    }
}