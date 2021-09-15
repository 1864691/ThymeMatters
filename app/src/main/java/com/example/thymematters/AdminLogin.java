package com.example.thymematters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//import com.muddzdev.styleabletoast.StyleableToast;

public class AdminLogin extends AppCompatActivity {

    EditText admin_email;
    EditText admin_password;
    Button btn_Admin_Login;
    TextView tAdmin_Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        admin_email = (EditText) findViewById(R.id.TV_Admin_email);
        admin_password = (EditText) findViewById(R.id.TV_Admin_password);
        btn_Admin_Login = findViewById(R.id.btn_Admin_Login);
        tAdmin_Register = findViewById(R.id.tAdmin_Register);


        //user not registered clicks on register text view
        tAdmin_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminLogin.this, AdminRegistration.class));
                finish();
            }
        });

        btn_Admin_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminLogin.this, AdminHome.class));
                finish();
            }
        });
    }


    //Admin clicks login button -> This method is called:
    public void doAdminLogin(View v){

        //first getting the values
        final String Admin_Email = admin_email.getText().toString();
        final String Admin_Password = admin_password.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(Admin_Email)) {
            admin_email.setError("Please enter your email");
            admin_email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(Admin_Password)) {
            admin_password.setError("Please enter your password");
            admin_password.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Admin_Email).matches()) {
            admin_email.setError("Enter a valid email");
            admin_email.requestFocus();
            return;
        }

        //if everything is fine
        //user gets logged in and data gets saved
        //Send network request to 000webhost for login of admin:
        //Define URL:
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://thymematters.000webhostapp.com/LOGIN/ADMIN_LOGIN.php").newBuilder();

        //If you want to add query parameters:
        urlBuilder.addQueryParameter("email",Admin_Email);
        urlBuilder.addQueryParameter("password",Admin_Password);

        String url = urlBuilder.build().toString();

        //Check if network is available: https://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
        boolean networkAvailable = isNetworkAvailable();
        //if(!networkAvailable){ StyleableToast.makeText(AdminLogin.this, "No Internet Connection", Toast.LENGTH_LONG, R.style.noInternet).show(); return;}

        //Send Request

        //Initialise progree bar: https://stackoverflow.com/questions/15083226/waiting-progress-bar-in-android
        //Progress Bar Functions: https://www.journaldev.com/9652/android-progressdialog-example
        final ProgressDialog progressDialog = ProgressDialog.show(this, "Logging in", "Please wait...");

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

                    AdminLogin.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            //Process Response Here:

                            //If reponse is "Invalid Login", toast to say invalid login:
                            if(myResponse.equals("Invalid Login")){
                                progressDialog.dismiss();
                                //StyleableToast.makeText(AdminLogin.this, "Invalid Login", Toast.LENGTH_LONG, R.style.invalidLogin).show();

                            }
                            else if(myResponse.equals("Not certified but is an admin")){
                                progressDialog.dismiss();
                                //StyleableToast.makeText(AdminLogin.this, "Not yet certified as Administrator", Toast.LENGTH_LONG, R.style.adminAwaitingCertification).show();
                            }

                            //Else the login is succesful and the user's unique id is outputted, user is logged in
                            else{
                                progressDialog.dismiss();
                                //StyleableToast.makeText(AdminLogin.this, "Login Successful", Toast.LENGTH_LONG, R.style.success).show();

                                //Then navigate admin to admin home activity with his/her unique id passed to the new activity with an intent:
                                Intent adminHome = new Intent(AdminLogin.this,AdminHome.class);
                                //Pass data to customer home screen:
                                adminHome.putExtra("ADMIN_ID",myResponse);
                                startActivity(adminHome);
                                finish();
                            }



                        }
                    });
                }

            }
        });

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}