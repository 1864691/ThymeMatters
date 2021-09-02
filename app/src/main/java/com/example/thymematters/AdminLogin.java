package com.example.thymematters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.util.HashMap;

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

        //user clicks on login button
        btn_Admin_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminLogin();
            }
        });
        //user not registered clicks on register text view
        tAdmin_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminLogin.this, AdminRegistration.class));
                finish();
            }
        });
    }

    //method to login admin
    private void adminLogin() {
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

        //if everything is fine
        //user gets logged in and data gets saved
        //depricated means theres a better code existing and i must use that
        class adminLogin extends AsyncTask<Void, Void, String> {
            //Step 1:
            // UI progress bar pops up while data is being fetched and validated
            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
            }
            /// step 2:
            ///this all gets done in background ?
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressBar.setVisibility(View.GONE);


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("admin");

                        //creating a new user object
                        Admin admin = new Admin(
                                userJson.getInt("admin_id"),
                                userJson.getString("admin_fname"),
                                userJson.getString("admin_lname"),
                                userJson.getString("admin_email"),
                                userJson.getString("admin_contact_no")
                        );

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).adminLogin(admin);

                        //starting the main activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), AdminHome.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid email address or password", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            /// in back ground of step 2: requesting the infor from the database
            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("admin_email", Admin_Email);
                params.put("admin_password", Admin_Password);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_LOGIN_ADMIN, params);
            }
        }

        adminLogin Al = new adminLogin();
        Al.execute();
    }
}