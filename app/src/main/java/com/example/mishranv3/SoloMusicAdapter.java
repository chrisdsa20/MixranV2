package com.example.mishranv3;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class SoloMusicAdapter extends FirebaseRecyclerAdapter<Music, SoloMusicAdapter.SoloHolder> {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String currentUser = mAuth.getCurrentUser().getUid();
    MediaPlayer mediaPlayer;
    private boolean flag = true;

    private static final String TAG="MusicAdapter";

    public SoloMusicAdapter(@NonNull FirebaseRecyclerOptions<Music> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final SoloHolder holder, int position, @NonNull final Music model) {
        holder.Name.setText(model.getName());
        holder.Artist.setText(model.getArtist());
        holder.Duration.setText(model.getDuration());
        holder.playButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                String song = model.getSong();
                Uri newSong = Uri.parse(song);
                Toast.makeText(v.getContext(), "Buffering....", Toast.LENGTH_SHORT).show();
                if(flag){
                    mediaPlayer = MediaPlayer.create(v.getContext(),newSong);
                    flag = false;
                }if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    Toast.makeText(v.getContext(), "Song is Paused...",Toast.LENGTH_SHORT).show();
                    holder.playButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                }
                else{
                    mediaPlayer.start();
                    Toast.makeText(v.getContext(), "Song is playing", Toast.LENGTH_SHORT).show();
                    holder.playButton.setImageResource(R.drawable.ic_pause_black_24dp);
                }
            }
        });

        holder.stopButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(!flag){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    flag = true;
                    holder.playButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                }
            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final DatabaseReference db = FirebaseDatabase.getInstance().getReference().
                        child("UserMusic").child(currentUser).child(model.getName());
                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        db.removeValue();
                        Toast.makeText(v.getContext(), "Song is removed....", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });

    }


    @NonNull
    @Override
    public SoloHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_layout_remove, parent,false);
        return new SoloHolder(view);
    }

    class SoloHolder extends RecyclerView.ViewHolder{


        TextView Name,Artist,Duration;
        ConstraintLayout constraintLayout;
        ImageView playButton, stopButton, remove;

        public SoloHolder(@NonNull View itemView) {

            super(itemView);
            Name = itemView.findViewById(R.id.songName);
            Artist = itemView.findViewById(R.id.artistName);
            Duration = itemView.findViewById(R.id.duration);
            constraintLayout = itemView.findViewById(R.id.constraintlayout);
            playButton = itemView.findViewById(R.id.play);
            stopButton = itemView.findViewById(R.id.stop);
            remove = itemView.findViewById(R.id.remove);



        }
    }
}