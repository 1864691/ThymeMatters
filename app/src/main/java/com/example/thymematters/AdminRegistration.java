package com.example.thymematters;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.muddzdev.styleabletoast.StyleableToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AdminRegistration extends AppCompatActivity {

    EditText Admin_FName, Admin_LName, Admin_Email, Admin_Phone, Admin_Password, Admin_ConfirmPassword;
    CheckBox Show_Admin_Password;
    Button btn_Admin_Register, btn_Admin_Return;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registration);

        //Get references to register button and return to login button:
        btn_Admin_Register = (Button) findViewById(R.id.btn_Admin_Register);
        btn_Admin_Return = (Button) findViewById(R.id.btn_Admin_Return);

        //Get references to all fields
        Admin_FName = (EditText) findViewById(R.id.et_Admin_FName);
        Admin_LName = (EditText) findViewById(R.id.et_Admin_LName);
        Admin_Email = (EditText) findViewById(R.id.et_Admin_EmailAddress);
        Admin_Phone = (EditText) findViewById(R.id.et_Admin_Phone);
        Admin_Password = (EditText) findViewById(R.id.et_Admin_Password);
        Admin_ConfirmPassword = (EditText) findViewById(R.id.et_Admin_ConfirmPassword);
        Show_Admin_Password = (CheckBox) findViewById(R.id.checkBoxAdminPwd);


        //Set show password checkbox to make passwords visible if clicked and hidden if clicked again:
        Show_Admin_Password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Show Password
                    Admin_Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    Admin_ConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // Hide Password
                    Admin_Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    Admin_ConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });





    }


    public void doRequestRegistration(View v){
        final String ADMIN_FNAME = Admin_FName.getText().toString();
        final String ADMIN_LNAME = Admin_LName.getText().toString();
        final String ADMIN_EMAIL = Admin_Email.getText().toString();
        final String ADMIN_PHONE = Admin_Phone.getText().toString();
        final String ADMIN_PASSWORD = Admin_Password.getText().toString();
        final String ADMIN_CONFIRM_PASSWORD = Admin_ConfirmPassword.getText().toString();

        //first we will do the validations

        if (TextUtils.isEmpty(ADMIN_FNAME)) {
            Admin_FName.setError("Please enter first name");
            Admin_FName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(ADMIN_LNAME)) {
            Admin_LName.setError("Please enter your last name");
            Admin_LName.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(ADMIN_EMAIL).matches()) {
            Admin_Email.setError("Enter a valid email");
            Admin_Email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(ADMIN_PHONE)) {
            Admin_Phone.setError("Please enter your last name");
            Admin_Phone.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(ADMIN_PASSWORD)) {
            Admin_Password.setError("Enter a password");
            Admin_Password.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(ADMIN_CONFIRM_PASSWORD)) {
            Admin_ConfirmPassword.setError("Please confirm your password");
            Admin_ConfirmPassword.requestFocus();
            return;
        }

        if(!TextUtils.equals(ADMIN_PASSWORD, ADMIN_CONFIRM_PASSWORD)){
            //Passwords dont match:
            Admin_Password.setError("Passwords do not match");
            Admin_Password.requestFocus();
            return;
        }

        //if it passes all the validations
        //Send network request to 000webhost for insertion of new administrator.
        //Define URL:
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://thymematters.000webhostapp.com/REGISTER/ADMIN_REGISTER.php").newBuilder();

        //If you want to add query parameters:
        urlBuilder.addQueryParameter("first_name", ADMIN_FNAME);
        urlBuilder.addQueryParameter("last_name", ADMIN_LNAME);

        urlBuilder.addQueryParameter("email", ADMIN_EMAIL);
        urlBuilder.addQueryParameter("admin_contact_no", ADMIN_PHONE);
        urlBuilder.addQueryParameter("admin_password", ADMIN_PASSWORD);

        String url = urlBuilder.build().toString();

        //Check if network is available: https://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
        boolean networkAvailable = isNetworkAvailable();
        if(!networkAvailable){ StyleableToast.makeText(AdminRegistration.this, "No Internet Connection", Toast.LENGTH_LONG, R.style.noInternet).show(); return;}

        //Send Request

        //Initialise progree bar: https://stackoverflow.com/questions/15083226/waiting-progress-bar-in-android
        //Progress Bar Functions: https://www.journaldev.com/9652/android-progressdialog-example
        final ProgressDialog progressDialog = ProgressDialog.show(this, "Requesting Admin Registration", "Please wait...");


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

                    AdminRegistration.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            //Process Response Here:

                            //If reponse is "Email not available", toast to say not available:
                            if(myResponse.equals("Email not available")){
                                progressDialog.dismiss();
                                StyleableToast.makeText(AdminRegistration.this, "This Email is Already in Use", Toast.LENGTH_LONG, R.style.invalidLogin).show();
                                Admin_Email.setError("This Email is already in use");
                                Admin_Email.requestFocus();
                            }

                            //Else the response will say "Admin Registration Request Sent", user is added
                            else{
                                progressDialog.dismiss();
                                StyleableToast.makeText(AdminRegistration.this, "Admin Registration Request Successful", Toast.LENGTH_LONG, R.style.success).show();
                            }



                        }
                    });
                }

            }
        });

    }
    public void doReturn_to_AdminLogin(View v){
        Intent goToAdminLoginScreen = new Intent(AdminRegistration.this,AdminLogin.class);
        finish();
        startActivity(goToAdminLoginScreen);

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}