package com.example.thymematters;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class CartActivity extends AppCompatActivity {

    Button pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        pay = findViewById(R.id.btn_pay);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this, PaymentActivity.class));
                finish();
            }
        });

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
                startActivity(new Intent(CartActivity.this, MainActivity.class));
                Toast.makeText(getApplicationContext(),"Logout Successful",Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case R.id.View_Account://create account page
                startActivity(new Intent(CartActivity.this, UserAccountDetails.class));
                finish();
                return true;

            case R.id.Order_History:
                startActivity(new Intent(CartActivity.this, CartActivity.class));
                finish();
                return true;

            case R.id.help:
                startActivity(new Intent(CartActivity.this, help_page.class));
                finish();
                return true;

            case R.id.favorites:
                startActivity(new Intent(CartActivity.this, favorites.class));
                finish();
                return true;
        }
        return false;
    }
}