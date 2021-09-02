package com.example.thymematters;

import android.content.Intent;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    EditText FName, LName, Delivery_Address, Email, Phone, Password, ConfirmPassword;
    CheckBox Show_Password;
    Button btn_Register, btn_return;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_Register = (Button) findViewById(R.id.btn_Register);
        btn_return = (Button) findViewById(R.id.btn_return);

        FName = (EditText) findViewById(R.id.et_FName);
        LName = (EditText) findViewById(R.id.et_LName);
        Delivery_Address = (EditText) findViewById(R.id.et_DeliveryAddress);
        Email = (EditText) findViewById(R.id.et_EmailAddress);
        Phone = (EditText) findViewById(R.id.et_Phone);
        Password = (EditText) findViewById(R.id.et_Password);
        ConfirmPassword = (EditText) findViewById(R.id.et_ConfirmPassword);
        Show_Password = (CheckBox) findViewById(R.id.checkBoxPwd);

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

        findViewById(R.id.btn_Register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on button register
                //for now for prototype just move to main page
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                //here we will register the user to server
                //registerUser();
            }
        });

        findViewById(R.id.btn_Register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open register screen
                finish();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });

        findViewById(R.id.btn_return).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open register screen
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

    }

    private void registerUser() {
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

        //if it passes all the validations

        class RegisterUser extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("FName", FNAME);
                params.put("LName", LNAME);
                params.put("Email", EMAIL);
                params.put("Phone", PHONE);
                params.put("Delivery_Address", DELIVERY_ADDRESS);
                params.put("Password", PASSWORD);
                params.put("ConfirmPassword", CONFIRM_PASSWORD);


                //returning the response
                return requestHandler.sendPostRequest(URLs.URL_REGISTER, params);
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
                                userJson.getInt("id"),
                                userJson.getString("fname"),
                                userJson.getString("lname"),
                                userJson.getString("delivery_address"),
                                userJson.getString("email"),
                                userJson.getString("contact_no")

                        );

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                        //starting the main menu activity (when register button is clicked)
                        finish();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                    } else {
                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        //executing the async task
        RegisterUser ru = new RegisterUser();
        ru.execute();
    }

}


