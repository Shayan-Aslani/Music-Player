package com.example.musicplayer;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.musicplayer.model.Repository;
import com.example.musicplayer.model.Song;
import com.google.android.material.button.MaterialButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class NowplayingFragment extends Fragment {

    private MaterialButton playButton ;
    private SeekBar timeSeekBar ;
    private TextView nameTextView ;
    private TextView artistNameTextView ;
    private TextView elapsedTimeTextView , songTimeTextView ;
    private Handler mHandler = new Handler() ;
    private Song nowPlayingSong;

    public NowplayingFragment() {
        // Required empty public constructor
    }

    public static NowplayingFragment newInstance() {
        
        Bundle args = new Bundle();
        
        NowplayingFragment fragment = new NowplayingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nowPlayingSong = Repository.getInstance(getContext()).getCurrntSong();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_nowplaying, container, false);

        initUi(view);
        setDetail(nowPlayingSong);


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (MainFragment.mediaPlayer != null) {
                    timeSeekBar.setProgress(MainFragment.mediaPlayer.getCurrentPosition() / 1000);
                    elapsedTimeTextView.setText(String.valueOf((timeSeekBar.getProgress())));
                    //    timeTextview.setText(String.valueOf(mediaPlayer.getCurrentPosition() / 1000));

                }
                mHandler.postDelayed(this, 1000);
            }
        });

        return view;
    }

    public void initUi(View view){
        timeSeekBar = view.findViewById(R.id.time_seekbar_nowPlaying);
        playButton = view.findViewById(R.id.play_Button_nowPlaying);
        nameTextView = view.findViewById(R.id.songName_TextView_NowPlaying);
        artistNameTextView = view.findViewById(R.id.artistName_TextView_NowPalying);
        elapsedTimeTextView = view.findViewById(R.id.elapsedTime_TextView);
        songTimeTextView = view.findViewById(R.id.songTime_TextView);

    }

    public void setDetail(Song song)
    {
        nameTextView.setText(song.getName());
        artistNameTextView.setText(song.getArtist());
        timeSeekBar.setMax(MainFragment.mediaPlayer.getDuration() / 1000);
        Repository.getInstance(getContext()).setCurrentSong(song);
        songTimeTextView.setText(String.valueOf(MainFragment.mediaPlayer.getDuration()/1000));
        elapsedTimeTextView.setText((String.valueOf(timeSeekBar.getProgress())));
        nowPlayingSong = song;
    }

}
