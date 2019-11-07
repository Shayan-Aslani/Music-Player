package com.example.musicplayer.model;

import java.util.UUID;
public class Song {

    private String path ;
    private String name ;
    private String artist ;
    private String album ;
    private String songFullPath ;

    public String getSongFullPath() {
        return songFullPath;
    }

    public void setSongFullPath(String songFullPath) {
        this.songFullPath = songFullPath;
    }

    private boolean nowPlaying = false;
    private UUID songId;

    public Song(String path, String name, String artist, String album) {
        this.path = path;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.songId = UUID.randomUUID();
    }

    public Song(){

    }


    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return this.artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return this.album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public boolean getNowPlaying() {
        return this.nowPlaying;
    }

    public void setNowPlaying(boolean nowPlaying) {
        this.nowPlaying = nowPlaying;
    }

    public UUID getSongId() {
        return this.songId;
    }

    public void setSongId(UUID songId) {
        this.songId = songId;
    }
}
