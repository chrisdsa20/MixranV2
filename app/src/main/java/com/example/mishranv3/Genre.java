package com.example.mishranv3;

public class Genre {

    private String FavGenre1;
    private String FavGenre2;
    private String FavGenre3;
    private String username;

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
