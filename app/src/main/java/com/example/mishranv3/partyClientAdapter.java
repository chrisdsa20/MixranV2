package com.example.mishranv3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class partyClientAdapter extends FirebaseRecyclerAdapter<Music, partyClientAdapter.clientViewHolder> {

    public partyClientAdapter(@NonNull FirebaseRecyclerOptions<Music> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull clientViewHolder holder, int position, @NonNull Music model) {
        holder.Name.setText(model.getName());
        holder.Artist.setText(model.getArtist());
        holder.Duration.setText(model.getDuration());

    }

    @NonNull
    @Override
    public clientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clientplaylist, parent,false);
        return new clientViewHolder(view);
    }

    public class clientViewHolder extends RecyclerView.ViewHolder {
        TextView Name,Artist,Duration;
        ConstraintLayout constraintLayout;
        public clientViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.clientsongName);
            Artist = itemView.findViewById(R.id.artistName);
            Duration = itemView.findViewById(R.id.duration);
            constraintLayout = itemView.findViewById(R.id.constraintlayout);
        }
    }
}
