package com.example.thymematters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

import com.muddzdev.styleabletoast.StyleableToast;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//import com.techyourchance.threadposter.UiThreadPoster;

public class MainActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button btn_Login;
    TextView tRegister;
    TextView tAdminLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.TV_email);
        password = (EditText) findViewById(R.id.TV_password);
        btn_Login = findViewById(R.id.btn_Login);
        tRegister = findViewById(R.id.tRegister);
        tAdminLogin = findViewById(R.id.tAdminLogin);



        //user not registered clicks on register text view
        tRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Register.class));
                finish();
            }
        });

        tAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AdminLogin.class));
                finish();
            }
        });
    }


    //User clicks login button -> This method is called:
    public void doLogin(View v){

        //first getting the values
        final String Email = email.getText().toString();
        final String Password = password.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(Email)) {
            email.setError("Please enter your email");
            email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(Password)) {
            password.setError("Please enter your password");
            password.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            email.setError("Enter a valid email");
            email.requestFocus();
            return;
        }

        //if everything is fine
        //user gets logged in and data gets saved
        //Send network request to 000webhost for login of customer:
        //Define URL:
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://thymematters.000webhostapp.com/LOGIN/CUSTOMER_LOGIN.php").newBuilder();

        //If you want to add query parameters:
        urlBuilder.addQueryParameter("email",Email);
        urlBuilder.addQueryParameter("password",Password);

        String url = urlBuilder.build().toString();

        //Check if network is available: https://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
        boolean networkAvailable = isNetworkAvailable();
        if(!networkAvailable){ StyleableToast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_LONG, R.style.noInternet).show(); return;}

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

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            //Process Response Here:

                            //If reponse is "Invalid Login", toast to say invalid login:
                            if(myResponse.equals("Invalid Login")){
                                progressDialog.dismiss();
                                StyleableToast.makeText(MainActivity.this, "Invalid Login", Toast.LENGTH_LONG, R.style.invalidLogin).show();

                            }

                            //Else the login is succesful and the user's unique id is outputted, user is logged in
                            else{
                                progressDialog.dismiss();
                                StyleableToast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG, R.style.success).show();

                                //Then navigate customer to home activity with his/her unique id passed to the new activity with an intent:
                                Intent customerHome = new Intent(MainActivity.this,HomeActivity.class);
                                //Pass data to customer home screen:
                                customerHome.putExtra("CUST_ID",myResponse);
                                startActivity(customerHome);
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
