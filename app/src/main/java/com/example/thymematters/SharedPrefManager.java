package com.example.thymematters;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
//this class if for methods that are used in multiple classes
public class SharedPrefManager {

    //the constants
    private static final String SHARED_PREF_NAME = "sharedpreference";
    private static final String KEY_USER_ID = "customer_id";
    private static final String KEY_ORDER_ID = "order_id";
    private static final String KEY_FNAME = "first_name";
    private static final String KEY_LNAME = "last_name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_ADDRESS = "del_address";

    private static final String KEY_ADMIN_ID = "admin_id";
    private static final String KEY_ADMIN_FNAME = "admin_fname";
    private static final String KEY_ADMIN_LNAME = "admin_lname";
    private static final String KEY_ADMIN_EMAIL = "admin_email";
    private static final String KEY_ADMIN_PHONE = "admin_phone";

    private static final String KEY_MEAL_CATEGORY = "category";
    private static final String KEY_MEAL_NAME = "meal_name";
    private static final String KEY_SERVING_SIZE = "serving_size";
    private static final String KEY_DELIVERY_DATE = "delivery_date";
    private static final String KEY_PLACEMENT_DATE = "placement_date";
    private static final String KEY_DELIVERY_ADDRESS = "delivery_address";
    private static final String KEY_PAYMENT_METHOD = "payment";
    private static final String KEY_PRICE = "meal_price";
    private static final String KEY_QUANTITY = "item_quantity";
    private static final String KEY_ADDITIONAL_NOTES = "notes";
    private static final String KEY_PAID_STATUS = "payment_status";
    private static final String KEY_ORDER_STATUS = "order_status";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the admin login
    //this method will store the admin data in shared preferences
    public void adminLogin(Admin admin) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ADMIN_ID, admin.getAdminId());
        editor.putString(KEY_ADMIN_EMAIL, admin.getAdmin_Email_Address());
        editor.apply();
    }

    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID, user.getUserId());
        editor.putString(KEY_FNAME, user.getFName());
        editor.putString(KEY_LNAME, user.getLName());
        editor.putString(KEY_ADDRESS, user.getAddress());
        editor.putString(KEY_EMAIL, user.getEmail_Address());
        editor.putString(KEY_PHONE, user.getContact_Number());
        editor.apply();
    }

    //this method will store the user order details in shared preferences
    public void userPlaceOrder(UserDetails userdetails) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID, userdetails.getUserId());
        editor.putInt(KEY_ORDER_ID, userdetails.getOrderId());
        editor.putString(KEY_MEAL_CATEGORY, userdetails.getMeal_Category());
        editor.putString(KEY_MEAL_NAME, userdetails.getMeal_Name());
        editor.putString(KEY_SERVING_SIZE, userdetails.getServing_Size());
        editor.putString(KEY_DELIVERY_DATE, userdetails.getDelivery_Date());
        editor.putString(KEY_MEAL_CATEGORY, userdetails.getMeal_Category());
        editor.putString(KEY_MEAL_NAME, userdetails.getMeal_Name());
        editor.putString(KEY_SERVING_SIZE, userdetails.getServing_Size());
        editor.putString(KEY_DELIVERY_DATE, userdetails.getDelivery_Date());
        editor.putString(KEY_PLACEMENT_DATE, userdetails.getDelivery_Date());
        editor.putString(KEY_DELIVERY_ADDRESS, userdetails.getDelivery_Address());
        editor.putInt(KEY_QUANTITY, userdetails.getQuantity());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, null) != null;
    }

    public boolean isAdminLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ADMIN_EMAIL, null) != null;
    }

    //FIX THIS FOR THYME MATTERS TO CHECK IF USER HAS PLACED AN ORDER
    /*public boolean isCheckedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_LOCATION, null) != null;
    }*/

    //this method will give the logged in user
    //FOR USER ACCOUNT DETAILS
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_USER_ID, -1),
                sharedPreferences.getString(KEY_FNAME, null),
                sharedPreferences.getString(KEY_LNAME, null),
                sharedPreferences.getString(KEY_ADDRESS, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_PHONE, null)
        );
    }

    public Admin getAdmin() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Admin(
                sharedPreferences.getInt(KEY_ADMIN_ID, -1),
                sharedPreferences.getString(KEY_ADMIN_FNAME, null),
                sharedPreferences.getString(KEY_ADMIN_LNAME, null),
                sharedPreferences.getString(KEY_ADMIN_EMAIL, null),
                sharedPreferences.getString(KEY_ADMIN_PHONE, null)
        );
    }

    //this method will give the checked in user
    public UserDetails getUserDetails() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new UserDetails(
                sharedPreferences.getInt(KEY_USER_ID, -1),
                sharedPreferences.getInt(KEY_ORDER_ID, 0), //not sure why 0
                sharedPreferences.getString(KEY_MEAL_CATEGORY,null),
                sharedPreferences.getString(KEY_MEAL_NAME, null),
                sharedPreferences.getString(KEY_SERVING_SIZE, null),
                sharedPreferences.getString(KEY_PLACEMENT_DATE, null),
                sharedPreferences.getString(KEY_DELIVERY_DATE, null),
                sharedPreferences.getString(KEY_PRICE, null),
                sharedPreferences.getString(KEY_PAYMENT_METHOD, null),
                sharedPreferences.getString(KEY_ADDITIONAL_NOTES, null),
                sharedPreferences.getString(KEY_PAID_STATUS, null),
                sharedPreferences.getString(KEY_ORDER_STATUS, null),
                sharedPreferences.getString(KEY_DELIVERY_ADDRESS, null),
                sharedPreferences.getInt(KEY_QUANTITY, 1)//not sure why 1
        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
