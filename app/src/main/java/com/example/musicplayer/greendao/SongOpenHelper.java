package com.example.musicplayer.greendao;

import android.content.Context;

import com.example.musicplayer.model.DaoMaster;

public class SongOpenHelper extends DaoMaster.DevOpenHelper {


    private static final String NAME = "Song.db";

    public SongOpenHelper(Context context) {
        super(context, NAME);
    }

}
