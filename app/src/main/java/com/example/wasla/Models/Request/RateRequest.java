package com.example.wasla.Models.Request;

public class RateRequest {

        public int orderID;
        public int ratedUserId;
        public double rateing;
        public String notes;


    public RateRequest(int orderID, int ratedUserId, double rateing, String notes) {
        this.orderID = orderID;
        this.ratedUserId = ratedUserId;
        this.rateing = rateing;
        this.notes = notes;
    }

}
