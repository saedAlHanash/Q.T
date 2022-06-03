package com.example.wasla.Models.Response;

import java.util.ArrayList;

public class RateResponse {


    public Result result;

    public class RatesInfo {
        public int userId = 0;
        public double ratedUserId = 0.0;
        public double rateing = 0.0;
        public String notes = "";
        public String userName = "";
        public String ratedUserName = "";
    }

    public class Result {
        public double rate;
        public ArrayList<RatesInfo> ratesInfo;
    }

}
