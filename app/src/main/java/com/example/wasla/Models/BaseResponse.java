package com.example.wasla.Models;

public class BaseResponse {

    public Error error;

    public class Error{
        public int code;
        public String message;
        public String details;
        public Object validationErrors;
    }

}
