package com.example.musicplayer;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.musicplayer.model.Song;
import com.example.musicplayer.model.Repository;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {

    public static final String ARG_TAB_STATUS = "tabStatus";

    private RecyclerView recyclerView ;
    private TabStatus tabStatus ;
    private List<Song> songsList ;

    private Callbacks mCallbacks;

    public static TabFragment newInstance(TabStatus tabStatus) {
        
        Bundle args = new Bundle();
        args.putSerializable(ARG_TAB_STATUS, tabStatus);
        TabFragment fragment = new TabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Callbacks)
            mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tabStatus = (TabStatus) getArguments().get(ARG_TAB_STATUS);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_tab, container, false);
        initUi(view);

        songsList = Repository.getInstance(getContext()).getSongs();
        switch (tabStatus){
            case SONGS:
                setSongsRecyclerView();
                break;
            case ALBUMS:
                break;
            case ARTISTS:
                break;
        }

        return view;
    }

    private void initUi(View view){
        recyclerView = view.findViewById(R.id.main_recyclerView);
    }

    private void setSongsRecyclerView(){
        SongsAdapter songsAdapter = new SongsAdapter(getContext() , songsList , this , recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(songsAdapter);
    }

    public interface Callbacks {
        void selectSong(Song song);
    }

}
