package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.musicplayer.model.Song;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Song> list ;

    private int STORAGE_PERMISSION_CODE=23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED) {
            ActivityCompat
                    .requestPermissions(
                            MainActivity.this,
                            new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                            STORAGE_PERMISSION_CODE);
        }

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container_layout);
        if (fragment == null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_container_layout, MainFragment.newInstance())
                    .commit();
    }





}
