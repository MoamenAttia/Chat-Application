package com.moamen.whatsapp.Model;

public class RoomUser {
    private String username;
    private String room;
    private int id;

    public RoomUser(String username, String room, int id) {
        this.username = username;
        this.room = room;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
