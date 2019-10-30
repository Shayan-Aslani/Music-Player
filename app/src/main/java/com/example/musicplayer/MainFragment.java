package com.example.musicplayer;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicplayer.model.Repository;
import com.example.musicplayer.model.Song;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;

public class MainFragment extends Fragment {


    private ViewPager viewPager ;
    private ViewPagerAdapter viewPagerAdapter;
    private SeekBar timeSeekBar ;
    public static MediaPlayer mediaPlayer ;
    private MaterialButton playButton ;
    private TextView nameTextView ;
    private TextView artistNameTextView ;
    private Handler mHandler = new Handler() ;
    private Song nowPlayingSong;
    private ConstraintLayout nowPlayingBar ;
    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mediaPlayer = new MediaPlayer();
        nowPlayingSong = Repository.getInstance(getContext()).getCurrntSong();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_main, container, false);

        initUi(view);
        setupViewPager(view);

        if(nowPlayingSong!=null)
            setDetail(nowPlayingSong);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
            }
        });

        nowPlayingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(NowplayingActivity.newIntent(getContext()));
            }
        });

        nameTextView.setSelected(true);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    timeSeekBar.setProgress(mediaPlayer.getCurrentPosition() / 1000);
                //    timeTextview.setText(String.valueOf(mediaPlayer.getCurrentPosition() / 1000));
                }
                mHandler.postDelayed(this, 1000);
            }
        });




        timeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    mediaPlayer.seekTo(seekBar.getProgress() * 1000);
                  //  timeTextview.setText(String.valueOf(mediaPlayer.getCurrentPosition() / 1000));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }

    private void initUi(View view){
        viewPager = view.findViewById(R.id.main_viewpager);
        timeSeekBar = view.findViewById(R.id.time_seekBar);
        playButton = view.findViewById(R.id.play_Button);
        nameTextView = view.findViewById(R.id.name_textView_main);
        artistNameTextView = view.findViewById(R.id.artistname_textView_main);
        nowPlayingBar = view.findViewById(R.id.nowPlayingBar);

    }

    public void setDetail(Song song)
    {
        nameTextView.setText(song.getName());
        artistNameTextView.setText(song.getArtist());
        timeSeekBar.setMax(mediaPlayer.getDuration() / 1000);
        Repository.getInstance(getContext()).setCurrentSong(song);
        nowPlayingSong = song;
    }

    public void setupViewPager(View view){
        viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        TabLayout tabLayout = view.findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void setMediaPlayer(Song song){
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(song.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            setDetail(song);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
