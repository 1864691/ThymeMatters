package com.example.thymematters;

public class User {

    private final int User_id;
    private final String FName;
    private final String LName;
    private final String Delivery_Address;
    private final String Contact_Number;
    private final String Email_Address;

    public User( int User_id, String FName, String LName, String Delivery_Address, String Contact_Number, String Email_Address) {
        this.User_id = User_id;
        this.FName = FName;
        this.LName = LName;
        this.Delivery_Address = Delivery_Address;
        this.Email_Address = Email_Address;
        this.Contact_Number = Contact_Number;
    }

    public int getUserId() {
        return User_id;
    }

    public String getFName() {
        return FName;
    }

    public String getEmail_Address() {
        return Email_Address;
    }

    public String getContact_Number() {
        return Contact_Number;
    }

    public String getLName() {
        return LName;
    }

    public String getAddress() {
        return Delivery_Address;
    }

}


