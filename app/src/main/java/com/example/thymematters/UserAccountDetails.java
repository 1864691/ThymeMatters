package com.example.thymematters;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class UserAccountDetails extends AppCompatActivity {

    EditText FName, LName, Delivery_Address, Email, Phone, Password, ConfirmPassword;
    Button btn_UpdateDetails, btn_Cancel, btnChangePassword;


    String CustID_FromIntent;
    String Fname, Lname, DeliveryAddress, Email_Address, Contact_No;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_details);

        FName = (EditText) findViewById(R.id.edit_text_FNAME);
        LName = (EditText) findViewById(R.id.edit_text_LNAME);
        Delivery_Address = findViewById(R.id.edit_text_DELIVERY_ADDRESS);
        Email = findViewById(R.id.edit_text_EMAIL_ADDRESS);
        Phone = findViewById(R.id.edit_text_PHONE);

        btn_UpdateDetails= findViewById(R.id.btn_Update_Details);
        btn_Cancel = findViewById(R.id.btn_Cancel);
        btnChangePassword = findViewById(R.id.btn_ChangePassword);

        User user = SharedPrefManager.getInstance(this).getUser();

        FName.setText(String.valueOf(user.getFName()));
        LName.setText(String.valueOf(user.getLName()));
        Delivery_Address.setText(String.valueOf(user.getAddress()));
        Email.setText(String.valueOf(user.getEmail_Address()));
        Phone.setText(String.valueOf(user.getContact_Number()));

        //user not registered clicks on register text view
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserAccountDetails.this, HomeActivity.class));
                //finish();
            }
        });

        btn_UpdateDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateDetails();
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserAccountDetails.this, ChangeUserPassword.class));
                //finish();
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    //updating the details
    private void UpdateDetails(){
        final String FNAME = FName.getText().toString();
        final String LNAME = LName.getText().toString();
        final String EMAIL = Email.getText().toString();
        final String DELIVERY_ADDRESS = Delivery_Address.getText().toString();
        final String PHONE = Phone.getText().toString();

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

        //if it passes all the validations
        //Send network request to 000webhost for insertion of new user (Customer)
        //Define URL:
        class UpdateUser extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("first_name", FNAME);
                params.put("last_name", LNAME);
                params.put("del_address", DELIVERY_ADDRESS);
                params.put("email", EMAIL);
                params.put("cust_contact_no", PHONE);


                //returning the response
                return requestHandler.sendPostRequest(URLs.URL_UPDATE, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //displaying the progress bar while user registers on the server
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //hiding the progressbar after completion
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
                                userJson.getInt("customer_id"),
                                userJson.getString("first_name"),
                                userJson.getString("last_name"),
                                userJson.getString("del_address"),
                                userJson.getString("email"),
                                userJson.getString("cust_contact_no")
                        );

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                        //starting the profile activity (when register button is clicked)
                        finish();
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));

                    } else {
                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        //executing the async task
        UpdateUser ru = new UpdateUser();
        ru.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                //onLogoutClick();
                startActivity(new Intent(UserAccountDetails.this, MainActivity.class));
                Toast.makeText(getApplicationContext(),"Logout Successful",Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case R.id.View_Account://create account page
                startActivity(new Intent(UserAccountDetails.this, UserAccountDetails.class));
                //finish();
                return true;

            case R.id.Order_History:
                startActivity(new Intent(UserAccountDetails.this, CartActivity.class));
                //finish();
                return true;

            case R.id.help:
                startActivity(new Intent(UserAccountDetails.this, help_page.class));
                //finish();
                return true;

            case R.id.favorites:
                startActivity(new Intent(UserAccountDetails.this, favorites.class));
                //finish();
                return true;
        }
        return false;
    }
}