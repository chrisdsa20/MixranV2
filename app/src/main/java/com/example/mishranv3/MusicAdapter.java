package com.example.mishranv3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MusicAdapter extends FirebaseRecyclerAdapter<Music, MusicAdapter.MusicViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MusicAdapter(@NonNull FirebaseRecyclerOptions<Music> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MusicViewHolder holder, int position, @NonNull Music model) {
        holder.Name.setText(model.getName());
        holder.Artist.setText(model.getArtist());
        holder.Duration.setText(model.getDuration());


    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_layout, parent,false);
        return new MusicViewHolder(view);
    }

    class MusicViewHolder extends RecyclerView.ViewHolder{

        TextView Name,Artist,Duration;


        public MusicViewHolder(@NonNull View itemView) {

            super(itemView);
            Name = itemView.findViewById(R.id.songName);
            Artist = itemView.findViewById(R.id.artistName);
            Duration = itemView.findViewById(R.id.duration);



        }
    }
}
