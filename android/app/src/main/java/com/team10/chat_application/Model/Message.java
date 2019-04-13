package com.team10.chat_application.Model;

public class Message {
    private String username;
    private String text;
    private int createdAt;

    public Message(String username, String text, int createdAt) {
        this.username = username;
        this.text = text;
        this.createdAt = createdAt;
    }

    public int getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
