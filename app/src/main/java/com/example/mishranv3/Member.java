package com.example.mishranv3;

public class Member {

    String username;
    String password;
    String email;
//    String id;

    public void Data(){

    }

    public Member(String username, String password, String email) {
        this.username = username;
        this.password = password;

        this.email = email;
//        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

//    public String getId() {
//        return id;
//    }
}
