package com.example.musicplayer.model;

import android.graphics.Bitmap;

import java.util.UUID;

public class Album {
    private UUID id;
    private String title;
    private String artist;
    private Bitmap bitmap;


    public Album(String title, String artist) {
        this.title = title;
        this.artist = artist;
        this.id = UUID.randomUUID();
    }

    public Album() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }



    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
