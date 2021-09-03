package com.example.thymematters;

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

import androidx.appcompat.app.AppCompatActivity;

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

        //user clicks on login button
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
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

    //method to login user
    private void userLogin() {
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

        //if everything is fine
        //user gets logged in and data gets saved
        //depricated means theres a better code existing and i must use that
        class UserLogin extends AsyncTask<Void, Void, String> {
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
                        JSONObject userJson = obj.getJSONObject("user");

                        //creating a new user object
                        User user = new User(
                                userJson.getInt("User_id"),
                                userJson.getString("FName"),
                                userJson.getString("LName"),
                                userJson.getString("DeliveryAddress"),
                                userJson.getString("Email_Address"),
                                userJson.getString("Contact_Number")
                        );

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                        //starting the main activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
                params.put("email", Email);
                params.put("password", Password);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_LOGIN, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }


}
