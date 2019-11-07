package com.example.musicplayer;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.model.Album;
import com.example.musicplayer.model.Song;

import java.util.ArrayList;
import java.util.List;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>{


    private List<Album> albumList;
    private List<Album> filteredAlbumList;
    private Fragment fragment;
    private RecyclerView recyclerView;
    private Context mContext;

    public AlbumsAdapter(Context context, List<Album> albumList, Fragment fragment, RecyclerView recyclerView) {
        this.mContext = context;
        this.albumList = albumList;
        this.filteredAlbumList = albumList;
        this.fragment = fragment;
        this.recyclerView = recyclerView;
    }


    @NonNull
    @Override
    public AlbumsAdapter.AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Activity activity = (Activity) parent.getContext();
        View view = activity.getLayoutInflater().inflate(R.layout.albums_row, parent, false);
        return new AlbumsAdapter.AlbumViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AlbumsAdapter.AlbumViewHolder holder, int position) {
        Album album = filteredAlbumList.get(position);
        holder.bind(album);
    }


    @Override
    public int getItemCount() {
   /*     if (filteredAlbumList.size() > 0)
            recyclerView.setBackgroundColor(Color.WHITE);
        else
            recyclerView.setBackgroundResource(R.drawable.ic_custom_background);

    */

        return filteredAlbumList == null ? 0 : filteredAlbumList.size();
    }
/*

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    filteredAlbumList = albumList;
                } else {
                    List<Album> filteredList = new ArrayList<>();
                    for (Album row : albumList) {

                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    filteredAlbumList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredAlbumList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredAlbumList = (List<Album>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

 */


    public class AlbumViewHolder extends RecyclerView.ViewHolder {
        private TextView albumNameTextView;
        private TextView artistNameTextView;


        public AlbumViewHolder(@NonNull final View itemView) {
            super(itemView);
            albumNameTextView = itemView.findViewById(R.id.albumname_textview_album);
            artistNameTextView = itemView.findViewById(R.id.artist_textView_album);

        }


        public void bind(final Album album) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 /*   MainFragment mainFragment = (MainFragment) fragment.getActivity().getSupportFragmentManager().findFragmentById(R.id.main_container_layout);
                    mainFragment.setMediaPlayer(song);
                    mainFragment.onPlayButtonClicked();
                    Toast.makeText(mContext, song.getName(), Toast.LENGTH_SHORT).show();

                  */
                }
            });

            albumNameTextView.setText(album.getTitle());
            artistNameTextView.setText(album.getArtist());
        }
    }
}
