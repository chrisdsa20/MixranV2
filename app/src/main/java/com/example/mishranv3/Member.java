package com.example.mishranv3;

public class Member {

    private String username;
    private String password;
    private String email;
//    String id;

    public void Data(){

    }

    public Member(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;

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

}
