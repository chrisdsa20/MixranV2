package com.example.mishranv3;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.renderscript.Sampler;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

class SearchAdapter extends FirebaseRecyclerAdapter<Music, SearchAdapter.SearchViewHolder> {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String currentUser = mAuth.getCurrentUser().getUid();
    DatabaseReference userdb = FirebaseDatabase.getInstance().getReference("MatesSession").child(currentUser);
    DatabaseReference db;
    MediaPlayer mediaPlayer;
    private boolean flag = true;

    private static final String TAG="SearchAdapter";
    public SearchAdapter(@NonNull FirebaseRecyclerOptions<Music> options) {
        super(options);

        userdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String value = dataSnapshot.child("id").getValue().toString();
                    Log.i(TAG, "onDataChange: "+ value);
                    db= FirebaseDatabase.getInstance().getReference("MatesSessionID").child(value).child("Songs");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onBindViewHolder(@NonNull final SearchViewHolder holder, int position, @NonNull final Music model) {
        holder.Name.setText(model.getName());
        holder.Artist.setText(model.getArtist());
        holder.Duration.setText(model.getDuration());
        holder.playButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                String song = model.getSong();
                Uri newSong = Uri.parse(song);
                if(flag){
                    mediaPlayer = MediaPlayer.create(v.getContext(),newSong);
                    flag = false;
                    Toast.makeText(v.getContext(), "Buffering....", Toast.LENGTH_SHORT).show();
                }if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
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
        holder.stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
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
                    db.child(name).setValue(music);
                    Toast.makeText(v.getContext(), "Song added to playlist", Toast.LENGTH_SHORT).show();



            }
        });


    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_layout, parent,false);
        return new SearchAdapter.SearchViewHolder(view);
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        TextView Name,Artist,Duration;
        ConstraintLayout constraintLayout;
        ImageView playButton, stopButton, add;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.clientsongName);
            Artist = itemView.findViewById(R.id.artistName);
            Duration = itemView.findViewById(R.id.duration);
            constraintLayout = itemView.findViewById(R.id.constraintlayout);
            playButton = itemView.findViewById(R.id.play);
            stopButton = itemView.findViewById(R.id.stop);
            add = itemView.findViewById(R.id.add);
        }
    }


}
