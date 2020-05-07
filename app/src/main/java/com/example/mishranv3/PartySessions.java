package com.example.mishranv3;

public class PartySessions {
    String id, permission;

    public PartySessions(String id, String permission) {
        this.id = id;
        this.permission = permission;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
