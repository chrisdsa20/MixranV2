package com.example.mishranv3;

public class Genre {

    String FavGenre1;
    String FavGenre2;
    String FavGenre3;

    public String getFavGenre1() {
        return FavGenre1;
    }

    public String getFavGenre2() {
        return FavGenre2;
    }

    public String getFavGenre3() {
        return FavGenre3;
    }

    public Genre(String favGenre1, String favGenre2, String favGenre3) {
        FavGenre1 = favGenre1;
        FavGenre2 = favGenre2;
        FavGenre3 = favGenre3;

    }
}
