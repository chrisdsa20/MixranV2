package com.example.mishranv3;
//Music class so that the application is able to receive the songs from the database
public class Music {

    String Artist;
    String Duration;
    String Genre;
    String Name;
    String Song;


    public Music(String artist, String duration, String genre, String name, String song) {
        Artist = artist;
        Duration = duration;
        Genre = genre;
        Name = name;
        Song = song;
    }

    public Music() {
    }

    public String getArtist() {
        return Artist;
    }

    public void setArtist(String artist) {
        Artist = artist;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSong() {
        return Song;
    }

    public void setSong(String song) {
        Song = song;
    }
}
