package com.example.wasla.Models.Request;

public class LoginRequest {

/*    public LoginRequest(String userNameOrEmailAddress, String password, boolean rememberClient) {
        this.userNameOrEmailAddress = userNameOrEmailAddress;
        this.password = password;
        this.rememberClient = rememberClient;
    }

    public String userNameOrEmailAddress;
    public String password;
    public boolean rememberClient;*/

    public String userNameOrEmailAddress;
    public String password;
    public boolean rememberClient;

    public LoginRequest(String userNameOrEmailAddress, String password) {
        this.userNameOrEmailAddress = userNameOrEmailAddress;
        this.password = password;
        this.rememberClient = true;
    }
}
