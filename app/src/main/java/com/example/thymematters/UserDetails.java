package com.example.thymematters;

// this is the cart and the order table
//so each cart item gets these values associated with them them they get grouped on the app using user_id and order_id
//
public class UserDetails {
    private final int User_id;
    private final int Order_id;
    private final String Meal_Category;
    private final String Meal_Name; //displayed on cart item
    private final String Serving_Size; //displayed on cart item
    private final String Delivery_Date; //displayed on cart item
    private final String Placement_Date;//make code to get this in background
    private final String Price; //displayed on cart item
    private final String Payment_Method; //added on checkout
    private final String Additional_Notes; // on selecting meal
    private final String Paid_Status; //on checkout
    private final String Delivered_status; //admin handled
    private final String Delivery_Address; //displayed on cart item
    private final int Quantity; //displayed on cart item

    public UserDetails(int User_id,int Order_id, String Meal_Category, String Meal_Name, String Serving_Size, String Delivery_Date, String Placement_Date, String Delivery_Address, String Price, String Payment_Method, String Additional_Notes, String Paid_Status, String Delivered_Status, int Quantity) {
        this.User_id = User_id;
        this.Order_id = Order_id;
        this.Meal_Category = Meal_Category;
        this.Meal_Name = Meal_Name;
        this.Serving_Size = Serving_Size;
        this.Delivery_Date = Delivery_Date;
        this.Placement_Date = Placement_Date;
        this.Price = Price;
        this.Payment_Method = Payment_Method;
        this.Additional_Notes = Additional_Notes;
        this.Paid_Status = Paid_Status;
        this.Delivered_status = Delivered_Status;
        this.Delivery_Address = Delivery_Address;
        this.Quantity = Quantity;
    }

    public int getUserId() {
        return User_id;
    }

    public int getOrderId() {
        return Order_id;
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

    public String getPlacement_Date_Date(){

        return Placement_Date;
    }

    public String getDelivery_Address(){

        return Delivery_Address;
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

    public int getQuantity() {

        return Quantity;
    }
}


