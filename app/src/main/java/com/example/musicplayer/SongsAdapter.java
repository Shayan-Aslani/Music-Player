package com.example.musicplayer;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.model.Song;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongViewHolder> implements Filterable {




    private List<Song> songList;
    private List<Song> filteredSongList;
    private Fragment fragment;
    private RecyclerView recyclerView;
    private Context mContext ;

    public SongsAdapter(Context context , List<Song> songList, Fragment fragment, RecyclerView recyclerView) {
        this.mContext = context;
        this.songList = songList;
        this.filteredSongList = songList;
        this.fragment = fragment;
        this.recyclerView = recyclerView;
    }


    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Activity activity = (Activity) parent.getContext();
        View view = activity.getLayoutInflater().inflate(R.layout.songs_row, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = filteredSongList.get(position);
        holder.bind(song);
    }


    @Override
    public int getItemCount() {
   /*     if (filteredSongList.size() > 0)
            recyclerView.setBackgroundColor(Color.WHITE);
        else
            recyclerView.setBackgroundResource(R.drawable.ic_custom_background);

    */

        return filteredSongList == null ? 0 : filteredSongList.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();



                if (charString.isEmpty()) {
                    filteredSongList = songList;
                } else {
                    List<Song> filteredList = new ArrayList<>();
                    for (Song row : songList) {

                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    filteredSongList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                        filterResults.values = filteredSongList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredSongList = (List<Song>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }



    public class SongViewHolder extends RecyclerView.ViewHolder {
        private TextView songNameTextView;
        private TextView artistTextView;
        private ImageView songImageView;

        public SongViewHolder(@NonNull final View itemView) {
            super(itemView);
            songNameTextView = itemView.findViewById(R.id.songname_textview);
            artistTextView = itemView.findViewById(R.id.artist_textView);
            songImageView = itemView.findViewById(R.id.song_imageView);


        }


        public void bind(final Song song) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainFragment mainFragment = (MainFragment) fragment.getActivity().getSupportFragmentManager().findFragmentById(R.id.main_container_layout);
                    mainFragment.setMediaPlayer(song);
                    mainFragment.onPlayButtonClicked();
                    Toast.makeText(mContext, song.getName(), Toast.LENGTH_SHORT).show();
                }
            });

            Picasso.get().load(Uri.parse(song.getSongFullPath())).resize(200 , 200).into(songImageView);
            songNameTextView.setText(song.getName());
            artistTextView.setText( song.getArtist());
        }
    }

}
