package com.example.musicplayer.controller;


import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.model.Repository;
import com.example.musicplayer.model.Song;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.List;

public class MainFragment extends Fragment {


    private ViewPager viewPager ;
    private ViewPagerAdapter viewPagerAdapter;
    private ProgressBar timeProgressbar;
    public static MediaPlayer mediaPlayer ;
    private MaterialButton playButton , nextButton, previousButton;
    private TextView nameTextView ;
    private TextView artistNameTextView ;
    private Handler mHandler = new Handler() ;
    private Song nowPlayingSong;
    private int songIndex ;
    private List<Song> songList ;
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
        songList = Repository.getInstance(getContext()).getSongs();
        nowPlayingSong = Repository.getInstance(getContext()).getCurrntSong();
        songIndex = songList.indexOf(nowPlayingSong);
    }

    @Override
    public void onResume() {
        super.onResume();
        setDetail(Repository.getInstance(getContext()).getCurrntSong());
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
                onPlayButtonClicked();
            }
        });

        nowPlayingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(NowplayingActivity.newIntent(getContext()));
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

        nameTextView.setSelected(true);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    timeProgressbar.setProgress(mediaPlayer.getCurrentPosition() / 1000);
                }
                mHandler.postDelayed(this, 1000);
            }
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setMediaPlayer(nowPlayingSong);


    }

    private void initUi(View view){
        viewPager = view.findViewById(R.id.main_viewpager);
        timeProgressbar = view.findViewById(R.id.time_ProgressBar);
        playButton = view.findViewById(R.id.play_Button);
        nameTextView = view.findViewById(R.id.name_textView_main);
        artistNameTextView = view.findViewById(R.id.artistname_textView_main);
        nowPlayingBar = view.findViewById(R.id.nowPlayingBar);
        nextButton = view.findViewById(R.id.next_button);
        previousButton = view.findViewById(R.id.previous_button);

    }

    public void setDetail(Song song)
    {
        nameTextView.setText(song.getName());
        artistNameTextView.setText(song.getArtist());
        timeProgressbar.setMax(mediaPlayer.getDuration() / 1000);
        Repository.getInstance(getContext()).setCurrentSong(song);
        nowPlayingSong = song;
        songIndex = songList.indexOf(song);
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
            setDetail(song);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
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

}
