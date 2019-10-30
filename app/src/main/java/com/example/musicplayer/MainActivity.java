package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.musicplayer.model.Song;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Song> list ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container_layout);
        if (fragment == null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_container_layout, MainFragment.newInstance())
                    .commit();
    }





}
