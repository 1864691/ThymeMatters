package com.example.thymematters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class VegetarianActivity extends AppCompatActivity {

    ImageView Veg1;
    ImageView Veg2;
    ImageView Veg3;
    ImageView Veg4;
    ImageView Veg5;
    ImageView Veg6;
    ImageView Veg7;
    ImageView Veg8;
    ImageView Veg9;
    ImageView Veg10;
    ImageView Veg11;
    ImageView Veg12;
    String CustID_FromIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vegetarian);

        //Initially retrieve customer unique id from intent:
        CustID_FromIntent = fetchCustID();
        Toast.makeText(this,CustID_FromIntent, Toast.LENGTH_LONG).show();

        Veg1= findViewById(R.id.veg1);
        Veg2 = findViewById(R.id.veg2);
        Veg3 = findViewById(R.id.veg3);
        Veg4 = findViewById(R.id.veg4);
        Veg5 = findViewById(R.id.veg5);
        Veg6 = findViewById(R.id.veg6);
        Veg7 = findViewById(R.id.veg7);
        Veg8 = findViewById(R.id.veg8);
        Veg9 = findViewById(R.id.veg9);
        Veg10 = findViewById(R.id.veg10);
        Veg11 = findViewById(R.id.veg11);
        Veg12 = findViewById(R.id.veg12);

        Veg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VegetarianActivity.this, PlaceOrderActivity.class));
                finish();
            }
        });
        Veg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VegetarianActivity.this, PlaceOrderActivity.class));
                finish();
            }
        });
        Veg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VegetarianActivity.this, PlaceOrderActivity.class));
                finish();
            }
        });
        Veg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VegetarianActivity.this, PlaceOrderActivity.class));
                finish();
            }
        });
        Veg5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VegetarianActivity.this, PlaceOrderActivity.class));
                finish();
            }
        });
        Veg6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VegetarianActivity.this, PlaceOrderActivity.class));
                finish();
            }
        });
        Veg7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VegetarianActivity.this, PlaceOrderActivity.class));
                finish();
            }
        });
        Veg8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VegetarianActivity.this, PlaceOrderActivity.class));
                finish();
            }
        });
        Veg9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VegetarianActivity.this, PlaceOrderActivity.class));
                finish();
            }
        });
        Veg10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VegetarianActivity.this, PlaceOrderActivity.class));
                finish();
            }
        });
        Veg11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VegetarianActivity.this, PlaceOrderActivity.class));
                finish();
            }
        });
        Veg12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VegetarianActivity.this, PlaceOrderActivity.class));
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
                startActivity(new Intent(VegetarianActivity.this, MainActivity.class));
                Toast.makeText(getApplicationContext(),"Logout Successful",Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case R.id.View_Account://create account page
                startActivity(new Intent(VegetarianActivity.this, UserAccountDetails.class));
                finish();
                return true;

            case R.id.Order_History:
                startActivity(new Intent(VegetarianActivity.this, CartActivity.class));
                finish();
                return true;

            case R.id.help:
                startActivity(new Intent(VegetarianActivity.this, help_page.class));
                finish();
                return true;

            case R.id.favorites:
                startActivity(new Intent(VegetarianActivity.this, favorites.class));
                finish();
                return true;
        }
        return false;
    }

    //Initially, this method is called upon opening this acity to rtrieve customers unique id from intent
    public String fetchCustID(){

        Intent getIntent = getIntent();
        String custID = getIntent.getStringExtra("CUST_ID");
        return custID;
    }
}