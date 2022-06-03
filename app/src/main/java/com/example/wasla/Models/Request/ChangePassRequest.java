package com.example.wasla.Models.Request;

public class ChangePassRequest {

    String phoneCode;
    String password;

    public ChangePassRequest(String phoneCode, String password) {
        this.phoneCode = phoneCode;
        this.password = password;
    }
}
