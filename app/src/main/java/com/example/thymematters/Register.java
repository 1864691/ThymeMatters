package com.example.thymematters;

import android.content.Intent;
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


import androidx.appcompat.app.AppCompatActivity;

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
                }
                else {
                    // Hide Password
                    Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        //progressBar = findViewById(R.id.progressbar);
        //progressBar.setVisibility(View.GONE);

        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fName = FName.getText().toString().trim();
                String lName = LName.getText().toString().trim();
                String deliveryAddress = Delivery_Address.getText().toString().trim();
                String email = Email.getText().toString().trim();
                String phone = Phone.getText().toString().trim();
                String password = Password.getText().toString().trim();
                String confirmation = ConfirmPassword.getText().toString().trim();

                if (TextUtils.isEmpty(fName) || TextUtils.isEmpty(lName) || TextUtils.isEmpty(deliveryAddress) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmation)) {
                    Toast.makeText(getBaseContext(), "All fields must be filled out", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(fName)) {
                    FName.setError("Input First Name");

                } else if (TextUtils.isEmpty(lName)) {
                    LName.setError("Input Surname");

                } else if (TextUtils.isEmpty(deliveryAddress)) {
                    Delivery_Address.setError("Input Delivery Address");

                } else if (TextUtils.isEmpty(email)) {
                    Email.setError("Input Email Address");

                } else if (TextUtils.isEmpty(phone)) {
                    Phone.setError("Input Phone Number");

                } else if (TextUtils.isEmpty(password)) {
                    Password.setError("Input Password");

                } else if (password.length() < 6) {
                    Toast.makeText(Register.this, "Password must be at least 6 characters ", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(confirmation)) {
                    ConfirmPassword.setError("Input Password");

                } else if (!password.equals(confirmation)) {
                    ConfirmPassword.setError("Passwords do not match");
                }

                else{
                    //still need to implement method
                    startActivity(new Intent(Register.this, HomeActivity.class));
                    finish();
                    /*Register(FName, LName, Delivery_Address, email, phone, password, ConfirmPassword);
                    progressBar.setVisibility(View.VISIBLE);*/
                }
            }

        });
    }
}

