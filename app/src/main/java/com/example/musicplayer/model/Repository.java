package com.example.musicplayer.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.musicplayer.greendao.SongOpenHelper;

import java.util.List;

public class Repository {
    private static Repository ourInstance;

    private SongDao songDao;
    private Context mContext;
    private Song currentSong ;

    public static Repository getInstance(Context context) {
        if (ourInstance == null)
            ourInstance = new Repository(context);

        return ourInstance;
    }

    private Repository(Context context) {
        mContext = context.getApplicationContext();
        SQLiteDatabase db = new SongOpenHelper(mContext).getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();

        songDao = daoSession.getSongDao();
        loadSongs();

        currentSong = songDao.queryBuilder()
                .where(SongDao.Properties.NowPlaying.eq(true))
                .unique();
        if(currentSong == null)
            currentSong = songDao.loadAll().get(0);

    }


    public void loadSongs () {

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.AudioColumns.DATA, MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.AudioColumns.ALBUM, MediaStore.Audio.ArtistColumns.ARTIST,};
        Cursor c = mContext.getContentResolver().query(uri, projection,
                null , null , null);

        if (c != null) {
            while (c.moveToNext()) {
                Song song = new Song();

                String path = c.getString(0);
                String name = c.getString(1);
                String album = c.getString(2);
                String artist = c.getString(3);

                song.setName(name);
                song.setAlbum(album);
                song.setArtist(artist);
                song.setPath(path);

                Log.e("Name :" + name, " Album :" + album);
                Log.e("Path :" + path, " Artist :" + artist);

                songDao.insert(song);
            }
            c.close();
        }
    }

    public List<Song> getSongs(){
        return songDao.loadAll();
    }

    public void updateSong(Song song)
    {
        songDao.update(song);
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
