package com.example.wasla.Models.Response;

import com.google.gson.annotations.SerializedName;

public class MyBalance {

  public   Result result;

    public class Result {

        public int id;
        public double valueBefore;
        public double valueAfter;
        public double bonus;
        public double lastPay;
        @SerializedName("trips_Count")
        public int tripsCount;
        public int userId;
    }
}
