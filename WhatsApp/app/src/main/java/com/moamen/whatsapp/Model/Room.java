package com.moamen.whatsapp.Model;

import com.moamen.whatsapp.Model.Message.Message;

import java.util.ArrayList;

public class Room {
    private String chatRoomID, password;
    private ArrayList<Message> chatList;

    public Room() {
        // For Document Snapshot.
    }

    public void setChatRoomID(String chatRoomID) {
        this.chatRoomID = chatRoomID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setChatList(ArrayList<Message> chatList) {
        this.chatList = chatList;
    }

    public String getChatRoomID() {
        return chatRoomID;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Message> getChatList() {
        return chatList;
    }

    public Room(String chatRoomID, String password, ArrayList<Message> chatList) {
        this.chatRoomID = chatRoomID;
        this.password = password;
        this.chatList = chatList;
    }
}
