package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class NowplayingActivity extends AppCompatActivity {


    public static Intent newIntent(Context context) {
        return new Intent(context, NowplayingActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nowplaying);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nowPlaying_container_Layout , NowplayingFragment.newInstance())
                .commit();
    }
}
