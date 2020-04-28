package com.example.mishranv3;

import android.media.MediaPlayer;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class SoloAddAdapter extends FirebaseRecyclerAdapter<Music, SoloAddAdapter.SoloAddViewHolder > {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String currentUser = mAuth.getCurrentUser().getUid();
    DatabaseReference db = FirebaseDatabase.getInstance().getReference("UserMusic");
    private static final String TAG="SoloAddAdapter";
    public SoloAddAdapter(@NonNull FirebaseRecyclerOptions<Music> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull SoloAddViewHolder holder, int position, @NonNull final Music model) {
        final MediaPlayer mediaPlayer = new MediaPlayer();
        holder.Name.setText(model.getName());
        holder.Artist.setText(model.getArtist());
        holder.Duration.setText(model.getDuration());
        holder.playButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(model.getSong());
                    mediaPlayer.prepare();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            Log.e(TAG, "onPrepared: Buffering ");
                        }

                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                    Log.i(TAG, "onClick: Music Playing");
                }
                else{
                    mediaPlayer.pause();
                    Log.i(TAG, "onClick: Music Paused");
                }
            }
        });
        holder.pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = model.getName();
                String artist = model.getArtist();
                String duration = model.getDuration();
                String song = model.getSong();
                String genre = model.getGenre();

                Music music = new Music(artist,duration,genre,name,song);
                db.child(currentUser).child(name).setValue(music);
                Toast.makeText(v.getContext(), "Song added to playlist", Toast.LENGTH_SHORT).show();



            }
        });

    }

    @NonNull
    @Override
    public SoloAddViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_layout, parent,false);
        return new SoloAddViewHolder(view);
    }

    public class SoloAddViewHolder extends RecyclerView.ViewHolder {
        TextView Name,Artist,Duration;
        ConstraintLayout constraintLayout;
        ImageView playButton, pauseButton, add;

        public SoloAddViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.songName);
            Artist = itemView.findViewById(R.id.artistName);
            Duration = itemView.findViewById(R.id.duration);
            constraintLayout = itemView.findViewById(R.id.constraintlayout);
            playButton = itemView.findViewById(R.id.play);
            pauseButton = itemView.findViewById(R.id.pause);
            add = itemView.findViewById(R.id.add);
        }
    }

}
