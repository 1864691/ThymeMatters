package com.example.thymematters;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AdminRegistration extends AppCompatActivity {

    EditText Admin_FName, Admin_LName, Admin_Email, Admin_Phone, Admin_Password, Admin_ConfirmPassword;
    CheckBox Show_Admin_Password;
    Button btn_Admin_Register, btn_Admin_Return;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registration);

        btn_Admin_Register = (Button) findViewById(R.id.btn_Admin_Register);
        btn_Admin_Return = (Button) findViewById(R.id.btn_Admin_Return);

        Admin_FName = (EditText) findViewById(R.id.et_Admin_FName);
        Admin_LName = (EditText) findViewById(R.id.et_Admin_LName);
        Admin_Email = (EditText) findViewById(R.id.et_Admin_EmailAddress);
        Admin_Phone = (EditText) findViewById(R.id.et_Admin_Phone);
        Admin_Password = (EditText) findViewById(R.id.et_Admin_Password);
        Admin_ConfirmPassword = (EditText) findViewById(R.id.et_Admin_ConfirmPassword);
        Show_Admin_Password = (CheckBox) findViewById(R.id.checkBoxAdminPwd);

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

        findViewById(R.id.btn_Admin_Return).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open register screen
                finish();
                startActivity(new Intent(getApplicationContext(), AdminLogin.class));
            }
        });

        findViewById(R.id.btn_Admin_Register).setOnClickListener(new View.OnClickListener() {
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

        findViewById(R.id.btn_Admin_Register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open register screen
                finish();
                startActivity(new Intent(getApplicationContext(), AdminHome.class));
            }
        });

    }

    private void registerUser() {
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

        //if it passes all the validations
//edit this to be for admin
        // php file not made for this yet
        class RegisterAdmin extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("Admin_FName", ADMIN_FNAME);
                params.put("Admin_LName", ADMIN_LNAME);
                params.put("Admin_Email", ADMIN_EMAIL);
                params.put("Admin_Phone", ADMIN_PHONE);
                params.put("Admin_Password", ADMIN_PASSWORD);
                params.put("Admin_ConfirmPassword", ADMIN_CONFIRM_PASSWORD);


                //returning the response
                return requestHandler.sendPostRequest(URLs.URL_REGISTER_ADMIN, params);
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
                        ///create a admin class
                        SharedPrefManager.getInstance(getApplicationContext()).adminLogin(admin);

                        //starting the main menu activity (when register button is clicked)
                        finish();
                        startActivity(new Intent(getApplicationContext(), AdminHome.class));

                    } else {
                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        //executing the async task
        RegisterAdmin ra = new RegisterAdmin();
        ra.execute();
    }
}