package com.example.musicplayer.model;

import com.example.musicplayer.greendao.UUIDConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;

import java.util.UUID;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "song")
public class Song {

    @Id(autoincrement = true)
    private Long _id;
    @Property(nameInDb = "path")
    private String path ;
    @Property(nameInDb = "name")
    private String name ;
    @Property(nameInDb = "artist")
    private String artist ;
    @Property(nameInDb = "album")
    private String album ;
    @Property(nameInDb = "nowPlaying")
    private boolean nowPlaying = false;
    @Property(nameInDb = "id")
    @Index(unique = true)
    @Convert(converter = UUIDConverter.class, columnType = String.class)
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

    @Generated(hash = 2119277412)
    public Song(Long _id, String path, String name, String artist, String album,
            boolean nowPlaying, UUID songId) {
        this._id = _id;
        this.path = path;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.nowPlaying = nowPlaying;
        this.songId = songId;
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
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
