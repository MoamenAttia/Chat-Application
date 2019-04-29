package com.moamen.whatsapp.Model.Message;


public class Message {
    private String from, text;
    private long createdAt;


    public Message(String from, String text, long createdAt) {
        this.from = from;
        this.text = text;
        this.createdAt = createdAt;
    }

    public Message(String from, String text) {
        this.from = from;
        this.text = text;
    }

    public Message() {
        // for document snapshot
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setText(String text) {
        this.text = text;
    }


    public String getFrom() {
        return from;
    }

    public String getText() {
        return text;
    }
}
