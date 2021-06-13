package com.example.thymematters;

public class User {

    private int id;
    private String FName, LName, DeliveryAddress, Contact_No, Email;

    public User(int id, String FName, String LName, String DeliveryAddress, String Contact_No, String Email) {
        this.id = id;
        this.FName = FName;
        this.LName = LName;
        this.DeliveryAddress = DeliveryAddress;
        this.Email = Email;
        this.Contact_No = Contact_No;
    }

    public int getId() {
        return id;
    }

    public String getFName() {
        return FName;
    }

    public String getEmail() {
        return Email;
    }

    public String getContact_No() {
        return Contact_No;
    }

    public String getLName() {
        return LName;
    }

    public String getDeliveryAddress() {
        return DeliveryAddress;
    }

}


