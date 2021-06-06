package com.example.thymematters;

public class User {

    private int id;
    private String FName, LName, DeliveryAddress, Contact_No, email;

    public User(int id, String username, String email, String gender) {
        this.id = id;
        this.FName = FName;
        this.LName = LName;
        this.DeliveryAddress = DeliveryAddress;
        this.email = email;
        this.Contact_No = Contact_No;
    }

    public int getId() {
        return id;
    }

    public String getFName() {
        return FName;
    }

    public String getEmail() {
        return email;
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


