package com.example.wasla.Models.Response;

public class LoginResponse {

    public Result result;

    public class Result {

        public String accessToken;
        public String encryptedAccessToken;
        public int expireInSeconds;
        public int userId;
        public String userTrip;

    }


}
