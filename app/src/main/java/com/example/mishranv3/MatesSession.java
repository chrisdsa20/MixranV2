package com.example.mishranv3;

public class MatesSession {

    String name,id,userID;

    public MatesSession(String name, String id, String userID) {
        this.name = name;
        this.id = id;
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
