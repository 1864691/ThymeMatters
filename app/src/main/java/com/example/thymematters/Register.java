package com.example.thymematters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import io.github.muddz.styleabletoast.StyleableToast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Register extends AppCompatActivity {

    EditText FName, LName, Delivery_Address, Email, Phone, Password, ConfirmPassword;
    CheckBox Show_Password;
    Button btn_Register, btn_return;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Get references to register button and return to login button:
        btn_Register = (Button) findViewById(R.id.btn_Register);
        btn_return = (Button) findViewById(R.id.btn_return);

        //Get references to all fields
        FName = (EditText) findViewById(R.id.et_FName);
        LName = (EditText) findViewById(R.id.et_LName);
        Delivery_Address = (EditText) findViewById(R.id.et_DeliveryAddress);
        Email = (EditText) findViewById(R.id.et_EmailAddress);
        Phone = (EditText) findViewById(R.id.et_Phone);
        Password = (EditText) findViewById(R.id.et_Password);
        ConfirmPassword = (EditText) findViewById(R.id.et_ConfirmPassword);
        Show_Password = (CheckBox) findViewById(R.id.checkBoxPwd);


        //Set show password checkbox to make passwords visible if clicked and hidden if clicked again:
        Show_Password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Show Password
                    Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // Hide Password
                    Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });





    }

    public void doClickRegister(View v){
        final String FNAME = FName.getText().toString();
        final String LNAME = LName.getText().toString();
        final String EMAIL = Email.getText().toString();
        final String DELIVERY_ADDRESS = Delivery_Address.getText().toString();
        final String PHONE = Phone.getText().toString();
        final String PASSWORD = Password.getText().toString();
        final String CONFIRM_PASSWORD = ConfirmPassword.getText().toString();

        //first we will do the validations

        if (TextUtils.isEmpty(FNAME)) {
            FName.setError("Please enter first name");
            FName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(LNAME)) {
            LName.setError("Please enter your last name");
            LName.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(EMAIL).matches()) {
            Email.setError("Enter a valid email");
            Email.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(DELIVERY_ADDRESS)) {
            Delivery_Address.setError("Please enter your address");
            Delivery_Address.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(PHONE)) {
            Phone.setError("Please enter your last name");
            Phone.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(PASSWORD)) {
            Password.setError("Enter a password");
            Password.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(CONFIRM_PASSWORD)) {
            ConfirmPassword.setError("Please confirm your password");
            ConfirmPassword.requestFocus();
            return;
        }
        if(!TextUtils.equals(CONFIRM_PASSWORD, PASSWORD)){
            //Passwords dont match:
            Password.setError("Passwords do not match");
            Password.requestFocus();
            return;
        }

        //if it passes all the validations
        //Send network request to 000webhost for insertion of new user (Customer)
        //Define URL:
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://thymematters.000webhostapp.com/REGISTER/CUSTOMER_REGISTER.php").newBuilder();

        //If you want to add query parameters:
        urlBuilder.addQueryParameter("first_name", FNAME);
        urlBuilder.addQueryParameter("last_name", LNAME);
        urlBuilder.addQueryParameter("del_address", DELIVERY_ADDRESS);
        urlBuilder.addQueryParameter("email", EMAIL);
        urlBuilder.addQueryParameter("cust_contact_no", PHONE);
        urlBuilder.addQueryParameter("cust_password", PASSWORD);

        String url = urlBuilder.build().toString();

        //Check if network is available: https://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
        boolean networkAvailable = isNetworkAvailable();
        if(!networkAvailable){ StyleableToast.makeText(Register.this, "No Internet Connection", Toast.LENGTH_LONG, R.style.noInternet).show(); return;}

        //Send Request

        //Initialise progree bar: https://stackoverflow.com/questions/15083226/waiting-progress-bar-in-android
        //Progress Bar Functions: https://www.journaldev.com/9652/android-progressdialog-example
        final ProgressDialog progressDialog = ProgressDialog.show(this, "Registering", "Please wait...");


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse( Call call,  Response response) throws IOException {
                if (response.isSuccessful()){
                    final String myResponse = response.body().string();

                    Register.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            //Process Response Here:

                            //If reponse is "Email not available", toast to say not available:
                            if(myResponse.equals("Email not available")){
                                progressDialog.dismiss();
                                StyleableToast.makeText(Register.this, "This Email is Already in Use", Toast.LENGTH_LONG, R.style.invalidLogin).show();
                                Email.setError("This Email is already in use");
                                Email.requestFocus();
                            }

                            //Else the response will say "Registration Success", user is added
                            else{
                                progressDialog.dismiss();
                                StyleableToast.makeText(Register.this, "Registration Successful", Toast.LENGTH_LONG, R.style.success).show();
                                new AlertDialog.Builder(Register.this,R.style.AlertDialogTheme)
                                        .setTitle("Registered")
                                        .setMessage("You have successfully registered as a new customer. You may now sign in.")
                                        .setCancelable(false)

                                        //.setNegativeButton("NO",null)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface arg0, int arg1) {
                                                //customer_home_screen.super.onBackPressed();
                                                Intent back_to_login = new Intent(Register.this,MainActivity.class);
                                                startActivity(back_to_login); finish();
                                            }
                                        }).create().show();
                            }



                        }
                    });
                }

            }
        });

    }
    public void doReturn_to_Login(View v){
        Intent backToLogin = new Intent(Register.this,MainActivity.class);
        finish();
        startActivity(backToLogin);

    }



    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}


