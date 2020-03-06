package com.example.mishranv3;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MatesAdapter extends RecyclerView.Adapter<MatesAdapter.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.playlist_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 25;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView SongName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            SongName = itemView.findViewById(R.id.playlistName);
        }
    }

}
