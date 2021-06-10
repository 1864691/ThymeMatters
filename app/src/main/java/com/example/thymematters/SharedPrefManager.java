package com.example.thymematters;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
//this class if for methods that are used in multiple classes
public class SharedPrefManager {

    //the constants
    private static final String SHARED_PREF_NAME = "sharedpreference";
    private static final String KEY_ID = "keyid";
    private static final String KEY_FNAME = "keyfname";
    private static final String KEY_LNAME = "keylname";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_PHONE = "keyphone";
    private static final String KEY_ADDRESS = "keyaddress";

    private static final String KEY_MEAL_CATEGORY = "keydate";
    private static final String KEY_MEAL_NAME = "keytotal";
    private static final String KEY_SERVING_SIZE = "keypositive";
    private static final String KEY_DELIVERY_DATE = "keydate";
    private static final String KEY_PAYMENT_METHOD = "keytotal";
    private static final String KEY_PRICE = "keypositive";
    private static final String KEY_ADDITIONAL_NOTES = "keydate";
    private static final String KEY_PAID_STATUS = "keytotal";
    private static final String KEY_DELIVERY_STATUS = "keypositive";

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

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.apply();
    }

    //this method will store the user order details in shared preferences
    public void userPlaceOrder(UserDetails userdetails) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, userdetails.getId());
        editor.putString(KEY_MEAL_CATEGORY, userdetails.getMeal_Category());
        editor.putString(KEY_MEAL_NAME, userdetails.getMeal_Name());
        editor.putString(KEY_SERVING_SIZE, userdetails.getServing_Size());
        editor.putString(KEY_DELIVERY_DATE, userdetails.getDelivery_Date());
        editor.putString(KEY_MEAL_CATEGORY, userdetails.getMeal_Category());
        editor.putString(KEY_MEAL_NAME, userdetails.getMeal_Name());
        editor.putString(KEY_SERVING_SIZE, userdetails.getServing_Size());
        editor.putString(KEY_DELIVERY_DATE, userdetails.getDelivery_Date());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, null) != null;
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
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_FNAME, null),
                sharedPreferences.getString(KEY_LNAME, null),
                sharedPreferences.getString(KEY_ADDRESS, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_PHONE, null)
        );
    }

    //this method will give the checked in user
    public UserDetails getUserDetails() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new UserDetails(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_MEAL_CATEGORY,null),
                sharedPreferences.getString(KEY_MEAL_NAME, null),
                sharedPreferences.getString(KEY_SERVING_SIZE, null),
                sharedPreferences.getString(KEY_DELIVERY_DATE, null),
                sharedPreferences.getString(KEY_PRICE, null),
                sharedPreferences.getString(KEY_PAYMENT_METHOD, null),
                sharedPreferences.getString(KEY_ADDITIONAL_NOTES, null),
                sharedPreferences.getString(KEY_PAID_STATUS, null),
                sharedPreferences.getString(KEY_DELIVERY_STATUS, null)
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
