package com.example.thymematters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.muddzdev.styleabletoast.StyleableToast;

public class HomeActivity extends AppCompatActivity {

    TextView Soup;
    TextView Fish;
    TextView Meat;
    TextView Vegetarian;
    TextView Dessert;
    String CustID_FromIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Initially retrieve customer unique id from intent:
        CustID_FromIntent = fetchCustID();
        Toast.makeText(this,CustID_FromIntent, Toast.LENGTH_LONG).show();





        Soup = findViewById(R.id.soup);
        Fish = findViewById(R.id.fish);
        Meat = findViewById(R.id.meat);
        Vegetarian = findViewById(R.id.vegetarian);
        Dessert = findViewById(R.id.dessert);

        Fish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fish = new Intent(HomeActivity.this,FishActivity.class);
                fish.putExtra("CUST_ID",CustID_FromIntent);
                startActivity(fish);

            }
        });

        Meat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent meat = new Intent(HomeActivity.this,MeatActivity.class);
                meat.putExtra("CUST_ID",CustID_FromIntent);
                startActivity(meat);

            }
        });

        Soup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent soup = new Intent(HomeActivity.this,SoupActivity.class);
                soup.putExtra("CUST_ID",CustID_FromIntent);
                startActivity(soup);

            }
        });

        Vegetarian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vegetarian = new Intent(HomeActivity.this,VegetarianActivity.class);
                vegetarian.putExtra("CUST_ID",CustID_FromIntent);
                startActivity(vegetarian);

            }
        });

        Dessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dessert = new Intent(HomeActivity.this,DessertActivity.class);
                dessert.putExtra("CUST_ID",CustID_FromIntent);
                startActivity(dessert);
            }
        });
    }


    //For the customer menu at top right
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    //For the customer menu at top right
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                //onLogoutClick();
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                Toast.makeText(getApplicationContext(),"Logout Successful",Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case R.id.View_Account://create account page
                startActivity(new Intent(HomeActivity.this, UserAccountDetails.class));
                finish();
                return true;

            case R.id.Order_History:
                startActivity(new Intent(HomeActivity.this, CartActivity.class));
                finish();
                return true;

            case R.id.help:
                startActivity(new Intent(HomeActivity.this, help_page.class));
                finish();
                return true;

            case R.id.favorites:
                startActivity(new Intent(HomeActivity.this, favorites.class));
                finish();
                return true;

            case R.id.cart:
                Intent goToCart = new Intent(HomeActivity.this,CartActivity.class);
                goToCart.putExtra("CUST_ID",CustID_FromIntent);
                startActivity(goToCart);


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

    //Override back button click
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this,R.style.AlertDialogTheme)
                .setTitle("Logout?")
                .setMessage("Are you sure you would like to log out?")
                .setNegativeButton("NO",null)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        HomeActivity.super.onBackPressed();
                        Intent backToLogin = new Intent(HomeActivity.this,MainActivity.class);
                        startActivity(backToLogin); finish();
                    }
                }).create().show();
    }
}