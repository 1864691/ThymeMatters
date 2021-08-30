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

public class HomeActivity extends AppCompatActivity {

    TextView Soup;
    TextView Fish;
    TextView Meat;
    TextView Vegetarian;
    TextView Dessert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Soup = findViewById(R.id.soup);
        Fish = findViewById(R.id.fish);
        Meat = findViewById(R.id.meat);
        Vegetarian = findViewById(R.id.vegetarian);
        Dessert = findViewById(R.id.dessert);

        Fish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, FishActivity.class));
                finish();
            }
        });

        Meat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, MeatActivity.class));
                finish();
            }
        });

        Soup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, SoupActivity.class));
                finish();
            }
        });

        Vegetarian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, VegetarianActivity.class));
                finish();
            }
        });

        Dessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, DessertActivity.class));
                finish();
            }
        });
    }// edit this for our sql database
    /*public void onLogoutClick(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:

                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(HomeActivity.this, LandingActivity.class));
                        Toast.makeText(getApplicationContext(),"Logout Successful",Toast.LENGTH_SHORT).show();
                        finish();

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }

            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setMessage("Are you sure you want to Logout ?")
                .setNegativeButton("No", dialogClickListener)
                .setPositiveButton("Yes", dialogClickListener)
                .show();

    }*/

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
        }
        return false;
    }
}