package com.example.wasla.Models.Request;

import com.example.wasla.Models.Response.UserInfo;

public class UpdateUserInfo {

    public UpdateUserInfo(String name, String surname, String emailAddress, String avatar) {
        this.name = name;
        this.surname = surname;
        this.emailAddress = emailAddress;
        this.avatar = avatar;
    }

    public UpdateUserInfo(UserInfo info, String avatar) {

        this.avatar = avatar != null ? avatar : "";

        this.name = info.result.fullName != null ? info.result.fullName : "";
        this.surname = " ";
        this.emailAddress = info.result.userName + "@gmil.com";
    }

    public String name;
    public String surname;
    public String emailAddress;
    public String avatar;

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
