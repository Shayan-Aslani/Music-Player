package com.example.musicplayer.controller;


import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.model.Repository;
import com.example.musicplayer.model.Song;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import static com.example.musicplayer.controller.MainFragment.mediaPlayer;


/**
 * A simple {@link Fragment} subclass.
 */
public class NowplayingFragment extends Fragment {

    private MaterialButton playButton, nextButton, previousButton;
    private SeekBar timeSeekBar;
    private TextView nameTextView;
    private TextView artistNameTextView;
    private TextView elapsedTimeTextView, songTimeTextView;
    private ImageView songImageView ;
    private Handler mHandler = new Handler();
    private List<Song> songList ;
    private Song nowPlayingSong;
    private int songIndex ;

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
        songList = Repository.getInstance(getContext()).getSongs();
        nowPlayingSong = Repository.getInstance(getContext()).getCurrntSong();
        songIndex = songList.indexOf(nowPlayingSong);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nowplaying, container, false);

        initUi(view);
        setDetail(nowPlayingSong);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPlayButtonClicked();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNextButtonClicked() ;
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPreviousButtonClicked();
            }
        });


        timeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    mediaPlayer.seekTo(seekBar.getProgress() * 1000);
                    setSeekBar();
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setSeekBar();
                mHandler.postDelayed(this, 1000);
            }
        });

        return view;
    }

    public void initUi(View view) {
        timeSeekBar = view.findViewById(R.id.time_seekbar_nowPlaying);
        playButton = view.findViewById(R.id.play_Button_nowPlaying);
        nextButton = view.findViewById(R.id.next_button_nowPlaying);
        previousButton = view.findViewById(R.id.previous_button_nowPlaying);
        nameTextView = view.findViewById(R.id.songName_TextView_NowPlaying);
        artistNameTextView = view.findViewById(R.id.artistName_TextView_NowPalying);
        elapsedTimeTextView = view.findViewById(R.id.elapsedTime_TextView);
        songTimeTextView = view.findViewById(R.id.songTime_TextView);
        songImageView = view.findViewById(R.id.imageView_nowPlaying);
    }

    public void setSeekBar() {
        if (mediaPlayer != null) {
            timeSeekBar.setProgress(mediaPlayer.getCurrentPosition() / 1000);
            elapsedTimeTextView.setText(convertDuration(mediaPlayer.getCurrentPosition() / 1000));
        }
    }



    public void setDetail(Song song) {
        nameTextView.setText(song.getName());
        artistNameTextView.setText(song.getArtist());
        Picasso.get().load(Uri.parse(song.getSongFullPath())).fit().into(songImageView);
        timeSeekBar.setMax(mediaPlayer.getDuration() / 1000);
        Repository.getInstance(getContext()).setCurrentSong(song);
        songTimeTextView.setText(convertDuration(mediaPlayer.getDuration() / 1000));
        elapsedTimeTextView.setText(convertDuration(timeSeekBar.getProgress()));
        nowPlayingSong = song;
        if (mediaPlayer.isPlaying())
            playButton.setIcon(getResources().getDrawable(android.R.drawable.ic_media_pause));
    }

    public void onPlayButtonClicked (){
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            playButton.setIcon(getResources().getDrawable(android.R.drawable.ic_media_play));
        } else {
            mediaPlayer.start();
            playButton.setIcon(getResources().getDrawable(android.R.drawable.ic_media_pause));
        }
    }

    public void onNextButtonClicked() {
        if (songIndex == songList.size() - 1) {
            //if last in playlist
            songIndex = 0;
            nowPlayingSong = songList.get(songIndex);
        } else {
            //get next in playlist
            nowPlayingSong = songList.get(++songIndex);
        }

        mediaPlayer.stop();
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(nowPlayingSong.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            setDetail(nowPlayingSong);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void onPreviousButtonClicked() {
        if (songIndex == 0) {
            //if last in playlist
            songIndex = songList.size() -1 ;
            nowPlayingSong = songList.get(songIndex);
        } else {
            //get next in playlist
            nowPlayingSong = songList.get(--songIndex);
        }

        mediaPlayer.stop();
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(nowPlayingSong.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            setDetail(nowPlayingSong);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public String convertDuration(long duration) {
        String out = null;
        long hours = 0;
        if (duration > 3600)
            hours = (duration / 3600);
        long remaining_minutes = (duration - (hours * 3600)) / 60;
        String minutes = String.valueOf(remaining_minutes);
        if (minutes.equals(0)) {
            minutes = "00";
        }
        long remaining_seconds = (duration - (hours * 3600) - (remaining_minutes * 60));
        String seconds = String.valueOf(remaining_seconds);
        if (seconds.length() < 2) {
            seconds = "0" + seconds;
        } else {
            seconds = seconds.substring(0, 2);
        }
        if (hours > 0) {
            out = hours + ":" + minutes + ":" + seconds;
        } else {
            out = minutes + ":" + seconds;
        }
        return out;
    }

}
