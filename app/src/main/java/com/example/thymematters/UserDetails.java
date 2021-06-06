package com.example.thymematters;


public class UserDetails {
    private int id;
    private String Meal, NumOfPeople, AdditionalRequests, PaymentMethod, Status, Delivered;

    public UserDetails(int id, String Meal, String NumOfPeople, String AdditionalRequests, String PaymentMethod, String Status, String Delivered) {
        this.id=id;
        this.Meal = Meal;
        this.NumOfPeople = NumOfPeople;
        this.AdditionalRequests = AdditionalRequests;
        this.PaymentMethod = PaymentMethod;
        this.Status = Status;
        this.Delivered = Delivered;
    }

    public int getId() {
        return id;
    }

    public String getMeal(){

        return Meal;
    }

    public String getNumOfPeople(){

        return NumOfPeople;
    }

    public String getAdditionalRequests(){

        return AdditionalRequests;
    }

    public String getPaymentMethod(){

        return PaymentMethod;
    }

    public String getStatus() {

        return Status;
    }

    public String getDelivered() {

        return Delivered;
    }
}


