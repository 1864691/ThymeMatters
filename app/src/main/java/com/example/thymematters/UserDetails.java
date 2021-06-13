package com.example.thymematters;


public class UserDetails {
    private int id;
    private String Meal_Category, Meal_Name, Serving_Size,Delivery_Date, Price, Payment_Method, Additional_Notes, Paid_Status, Delivered_status;

    public UserDetails(int id, String Meal_Category, String Meal_Name, String Serving_Size, String Delivery_Date, String Price, String Payment_Method, String Additional_Notes, String Paid_Status, String Delivered_Status) {
        this.id=id;
        this.Meal_Category = Meal_Category;
        this.Meal_Name = Meal_Name;
        this.Serving_Size = Serving_Size;
        this.Delivery_Date = Delivery_Date;
        this.Price = Price;
        this.Payment_Method = Payment_Method;
        this.Additional_Notes = Additional_Notes;
        this.Paid_Status = Paid_Status;
        this.Delivered_status = Delivered_Status;
    }

    public int getId() {
        return id;
    }

    public String getMeal_Category(){

        return Meal_Category;
    }

    public String getMeal_Name(){

        return Meal_Name;
    }

    public String getServing_Size(){

        return Serving_Size;
    }

    public String getDelivery_Date(){

        return Delivery_Date;
    }

    public String getPrice(){

        return Price;
    }

    public String getPayment_Method(){

        return Payment_Method;
    }

    public String getAdditional_Notes(){

        return Additional_Notes;
    }

    public String getPaid_Status() {

        return Paid_Status;
    }

    public String getDelivered_status() {

        return Delivered_status;
    }
}


