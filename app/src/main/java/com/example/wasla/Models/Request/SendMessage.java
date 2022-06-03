package com.example.wasla.Models.Request;

import com.google.gson.annotations.SerializedName;

public class SendMessage {

    public int userId;

    @SerializedName("tilte")
    public String title;

    public String body;

    public SendMessage(int userId, String title, String body) {
        this.userId = userId;
        this.title = title;
        this.body = body;
    }
}
