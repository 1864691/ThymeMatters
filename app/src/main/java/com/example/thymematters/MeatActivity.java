package com.example.thymematters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MeatActivity extends AppCompatActivity {

    ImageView Meat1;
    ImageView Meat2;
    ImageView Meat3;
    ImageView Meat4;
    ImageView Meat5;
    ImageView Meat6;
    ImageView Meat7;
    ImageView Meat8;
    ImageView Meat9;
    ImageView Meat10;
    ImageView Meat11;
    ImageView Meat12;
    String CustID_FromIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meat);

        //Initially retrieve customer unique id from intent:
        CustID_FromIntent = fetchCustID();
        Toast.makeText(this,CustID_FromIntent, Toast.LENGTH_LONG).show();

        Meat1 = findViewById(R.id.meat1);
        Meat2= findViewById(R.id.meat2);
        Meat3 = findViewById(R.id.meat3);
        Meat4 = findViewById(R.id.meat4);
        Meat5 = findViewById(R.id.meat5);
        Meat6 = findViewById(R.id.meat6);
        Meat7 = findViewById(R.id.meat7);
        Meat8 = findViewById(R.id.meat8);
        Meat9 = findViewById(R.id.meat9);
        Meat10 = findViewById(R.id.meat10);
        Meat11 = findViewById(R.id.meat11);
        Meat12 = findViewById(R.id.meat12);

        Meat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MeatActivity.this, PlaceOrderActivity.class));
                finish();
            }
        });
        Meat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MeatActivity.this, PlaceOrderActivity.class));
                finish();
            }
        });
        Meat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MeatActivity.this, PlaceOrderActivity.class));
                finish();
            }
        });
        Meat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MeatActivity.this, PlaceOrderActivity.class));
                finish();
            }
        });
        Meat5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MeatActivity.this, PlaceOrderActivity.class));
                finish();
            }
        });
        Meat6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MeatActivity.this, PlaceOrderActivity.class));
                finish();
            }
        });
        Meat7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MeatActivity.this, PlaceOrderActivity.class));
                finish();
            }
        });
        Meat8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MeatActivity.this, PlaceOrderActivity.class));
                finish();
            }
        });
        Meat9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MeatActivity.this, PlaceOrderActivity.class));
                finish();
            }
        });
        Meat10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MeatActivity.this, PlaceOrderActivity.class));
                finish();
            }
        });
        Meat11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MeatActivity.this, PlaceOrderActivity.class));
                finish();
            }
        });
        Meat12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MeatActivity.this, PlaceOrderActivity.class));
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
                startActivity(new Intent(MeatActivity.this, MainActivity.class));
                Toast.makeText(getApplicationContext(),"Logout Successful",Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case R.id.View_Account://create account page
                startActivity(new Intent(MeatActivity.this, UserAccountDetails.class));
                finish();
                return true;

            case R.id.Order_History:
                startActivity(new Intent(MeatActivity.this, CartActivity.class));
                finish();
                return true;

            case R.id.help:
                startActivity(new Intent(MeatActivity.this, help_page.class));
                finish();
                return true;

            case R.id.favorites:
                startActivity(new Intent(MeatActivity.this, favorites.class));
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