package com.example.thymematters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class DessertActivity extends AppCompatActivity {

    ImageView Brownies;
    ImageView LemonMeringue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dessert);

        Brownies = findViewById(R.id.Brownies);
        LemonMeringue = findViewById(R.id.LemonMeringue);

        Brownies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DessertActivity.this, PlaceOrderActivity.class));
                finish();
            }
        });
        LemonMeringue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DessertActivity.this, PlaceOrderActivity.class));
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
                startActivity(new Intent(DessertActivity.this, MainActivity.class));
                Toast.makeText(getApplicationContext(),"Logout Successful",Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case R.id.View_Account://create account page
                startActivity(new Intent(DessertActivity.this, UserAccountDetails.class));
                finish();
                return true;

            case R.id.Order_History:
                startActivity(new Intent(DessertActivity.this, CartActivity.class));
                finish();
                return true;

            case R.id.help:
                startActivity(new Intent(DessertActivity.this, help_page.class));
                finish();
                return true;

            case R.id.favorites:
                startActivity(new Intent(DessertActivity.this, favorites.class));
                finish();
                return true;
        }
        return false;
    }
}