package com.example.thymematters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Checkout extends AppCompatActivity {

    String TOTALPRICE_FromIntent;
    String CustID_FromIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        //Receive cust id and total price of order from intent
        TOTALPRICE_FromIntent = fetchTOTALPRICE();
        CustID_FromIntent = fetchCustID();
        Toast.makeText(Checkout.this,"Total Price: "+TOTALPRICE_FromIntent,Toast.LENGTH_LONG).show();
        Toast.makeText(Checkout.this,"Customer ID: "+CustID_FromIntent,Toast.LENGTH_LONG).show();
    }

    //Method to receive CUST_ID from intents
    public String fetchCustID(){

        Intent getIntent = getIntent();
        String custID = getIntent.getStringExtra("CUST_ID");
        return custID;
    }

    //Method to receive TOTALPRICE from intents
    public String fetchTOTALPRICE(){

        Intent getIntent = getIntent();
        String totPrice = getIntent.getStringExtra("TOTALPRICE");
        return totPrice;
    }

    //Top right menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //Top right menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                //onLogoutClick();
                startActivity(new Intent(Checkout.this, MainActivity.class));
                Toast.makeText(getApplicationContext(),"Logout Successful",Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case R.id.View_Account://create account page
                startActivity(new Intent(Checkout.this, UserAccountDetails.class));
                finish();
                return true;

            case R.id.Order_History:
                startActivity(new Intent(Checkout.this, CartActivity.class));
                finish();
                return true;

            case R.id.help:
                startActivity(new Intent(Checkout.this, help_page.class));
                finish();
                return true;

            case R.id.favorites:
                startActivity(new Intent(Checkout.this, favorites.class));
                finish();
                return true;
        }
        return false;
    }
}