package com.example.wasla.Models.Response;

public class AvailableModel {

    public class Result{
        public String imei;
        public String lat;
        public String lng;
        public String speed;
        public String active;

        @Override
        public String toString() {
            return "Result{" +
                    "imei='" + imei + '\'' +
                    ", lat='" + lat + '\'' +
                    ", lng='" + lng + '\'' +
                    ", speed='" + speed + '\'' +
                    ", active='" + active + '\'' +
                    '}';
        }
    }

        public Result result;

    @Override
    public String toString() {
        return "AvailableModel{" +
                "result=" + result +
                '}';
    }
}
