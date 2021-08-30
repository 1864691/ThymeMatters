package com.example.thymematters;

public class Admin {


    private final int Admin_id;
    private final String Admin_FName;
    private final String Admin_LName;
    private final String Admin_Contact_Number;
    private final String Admin_Email_Address;

    public Admin(int Admin_id, String Admin_FName, String Admin_LName, String Admin_Contact_Number, String Admin_Email_Address) {
        this.Admin_id = Admin_id;
        this.Admin_FName = Admin_FName;
        this.Admin_LName = Admin_LName;
        this.Admin_Email_Address = Admin_Email_Address;
        this.Admin_Contact_Number = Admin_Contact_Number;
    }

    public int getAdminId() {
        return Admin_id;
    }

    public String getAdmin_FName() {
        return Admin_FName;
    }

    public String getAdmin_Email_Address() {
        return Admin_Email_Address;
    }

    public String getAdmin_Contact_Number() {
        return Admin_Contact_Number;
    }

    public String getAdmin_LName() {
        return Admin_LName;
    }



}
