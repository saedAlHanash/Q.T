package com.example.wasla.Models.Response;

import com.example.wasla.Models.Request.DriverLocation;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Trips {

    @SerializedName("result")
    public ArrayList<Result> result;

    public class Result {
        public int id;
        public int clientId;
        public int driverId;
        public String startDate;
        public String endDate;
        public String currentLocation_name;
        @SerializedName("distnation_name")
        public String destinationName;
        public String duration;
        public DriverLocation currentLocation;
        @SerializedName("distnation")
        public DriverLocation destination;
        public boolean isAccepted;
        public String note;
        public double distance;
        public String tripTimeDate;
        public double tripFare;
        public String client_PhoneNumber;
        @SerializedName("isDilverd")
        public boolean isDelved;
        public boolean isCanceled;
        @SerializedName("cancelReasone")
        public String cancelReason;
        public String driverName;
        @SerializedName("clietName")
        public String clientName;
        public String creationTime;
        public boolean isConfirmed;
        public double clientRate;
        public double driverRate;
        public boolean isClientRated;
        public boolean isDriverRated;
        public int carCategoryId;
        public int couponsId;
        public boolean isStarted;
        public int tripType;
    }

}




/*
         id : int

         clientId   : int

         driverId   : int

         startDate  : String

         endDate    : String

         currentLocation_name   : String

         destinationName    : String

         duration   : String

         currentLocation    : DriverLocation

         destination    : DriverLocation

         isAccepted : boolean

         note   : String

         distance   : double

         tripTimeDate   : String

         tripFare   : double

         client_PhoneNumber : String

         isDelved   : boolean

         isCanceled : boolean

         cancelReason   : String

         driverName : String

         clientName : String

         creationTime   : String

         isConfirmed    : boolean

         clientRate : double

         driverRate : double

         isClientRated  : boolean

         isDriverRated  : boolean

         carCategoryId  : int

         couponsId  : int

         isStarted  : boolean

         tripType   : int

 */
