package com.example.mishranv3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MatesMusicAdapter extends FirebaseRecyclerAdapter<Music, MatesMusicAdapter.MatesHolder> {

    public MatesMusicAdapter(FirebaseRecyclerOptions<Music> options){
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MatesHolder holder, int position, @NonNull Music model) {

    }

    @NonNull
    @Override
    public MatesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_layout_remove, parent,false);
        return new MatesHolder(view);
    }

    public class MatesHolder extends RecyclerView.ViewHolder {

        TextView Name,Artist,Duration;
        ConstraintLayout constraintLayout;
        ImageView playButton, pauseButton, remove;

        public MatesHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.songName);
            Artist = itemView.findViewById(R.id.artistName);
            Duration = itemView.findViewById(R.id.duration);
            constraintLayout = itemView.findViewById(R.id.constraintlayout);
            playButton = itemView.findViewById(R.id.play);
            pauseButton = itemView.findViewById(R.id.pause);
            remove = itemView.findViewById(R.id.remove);
        }
    }
}
