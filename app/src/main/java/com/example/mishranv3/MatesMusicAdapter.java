package com.example.mishranv3;

import android.media.MediaPlayer;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class MatesMusicAdapter extends FirebaseRecyclerAdapter<Music, MatesMusicAdapter.MatesHolder> {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String currentUser = mAuth.getCurrentUser().getUid();
    DatabaseReference userdb = FirebaseDatabase.getInstance().getReference("PartySession").child(currentUser);
    DatabaseReference db;

    private static final String TAG="MatesMusicHolder";
    public MatesMusicAdapter(FirebaseRecyclerOptions<Music> options){
        super(options);
        userdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String value = dataSnapshot.child("id").getValue().toString();
                    Log.i(TAG, "onDataChange: "+ value);
                    db= FirebaseDatabase.getInstance().getReference("PartyID").child(value).child("Songs");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



            }

    @Override
    protected void onBindViewHolder(@NonNull MatesHolder holder, int position, @NonNull final Music model) {
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
                holder.remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        userdb.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    String value = dataSnapshot.child("id").getValue().toString();
                                    Log.i(TAG, "onDataChange: "+ value);
                                    db= FirebaseDatabase.getInstance().getReference("MatesSessionID").child(value).child("Songs")
                                            .child(model.Name);
                                    db.removeValue();
                                    Toast.makeText(v.getContext(),"Song Removed",Toast.LENGTH_SHORT).show();
                                }
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
    public MatesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_layout_remove, parent,false);
        return new MatesHolder(view);
    }

    class MatesHolder extends RecyclerView.ViewHolder {

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
