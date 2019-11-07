package com.example.musicplayer.model;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.musicplayer.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private static Repository ourInstance;

    private Context mContext;
    private Song currentSong ;
    private List<Song> songList = new ArrayList<>();
    private List<Album> albumList =  new ArrayList<>();

    public static Repository getInstance(Context context) {
        if (ourInstance == null)
            ourInstance = new Repository(context);

        return ourInstance;
    }

    private Repository(Context context) {
        mContext = context.getApplicationContext();

        if(ContextCompat.checkSelfPermission(
                mContext, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
            loadSongs();


            if(currentSong == null)
                currentSong = songList.get(0);

    }


    public void loadSongs () {

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.AudioColumns.DATA, MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.AudioColumns.ALBUM, MediaStore.Audio.ArtistColumns.ARTIST , MediaStore.Audio.Media.ALBUM_ID };
        Cursor c = mContext.getContentResolver().query(uri, projection,
                null , null , null);

        if (c != null) {
            while (c.moveToNext()) {
                Song song = new Song();
                Album mAlbum = new Album();

                String path = c.getString(0);
                String name = c.getString(1);
                String album = c.getString(2);
                String artist = c.getString(3);

                Long albumId = c.getLong(4);
                Uri sArtworkUri = Uri
                        .parse("content://media/external/audio/albumart");
                Uri albumArtUri = ContentUris.withAppendedId(sArtworkUri, albumId);

                song.setName(name);
                song.setAlbum(album);
                song.setArtist(artist);
                song.setPath(path);
                song.setSongFullPath(albumArtUri.toString());

                mAlbum.setTitle(album);
                mAlbum.setArtist(artist);



                Log.e("Name :" + name, " Album :" + album);
                Log.e("Path :" + path, " Artist :" + artist);

                songList.add(song);
                albumList.add(mAlbum);
            }
            c.close();
        }
    }

    public List<Song> getSongs(){
        return songList;
    }
    public List<Album> getAlbumList(){
        return albumList;
    }
    public void updateSong(Song song)
    {

    }



    public void updateSongList(){

    }

    public void setCurrentSong(Song song){
        currentSong.setNowPlaying(false);
        updateSong(currentSong);
        currentSong = song ;
        currentSong.setNowPlaying(true);
        updateSong(currentSong);
    }

    public Song getCurrntSong(){
        return currentSong;
    }

}
