package com.volleycn.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.volleycn.animimageview.AnimationImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnimationImageView imageView = findViewById(R.id.AnimationImageView);
        imageView.setEnablePlay(true);
        AnimationImageView imageView1 = findViewById(R.id.AnimationImageView1);
        imageView1.setEnablePlay(true);
    }
}
