package com.example.thymematters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class SoupActivity extends AppCompatActivity {

    ImageView Soup1;
    ImageView Soup2;
    ImageView Soup3;
    ImageView Soup4;
    ImageView Soup5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soup);

        Soup1 = findViewById(R.id.soup1);
        Soup2 = findViewById(R.id.soup2);
        Soup3 = findViewById(R.id.soup3);
        Soup4 = findViewById(R.id.soup4);
        Soup5 = findViewById(R.id.soup5);

        Soup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SoupActivity.this, PlaceOrderActivity.class));
                finish();
            }
        });
        Soup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SoupActivity.this, PlaceOrderActivity.class));
                finish();
            }
        });
        Soup3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SoupActivity.this, PlaceOrderActivity.class));
                finish();
            }
        });
        Soup4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SoupActivity.this, PlaceOrderActivity.class));
                finish();
            }
        });
        Soup5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SoupActivity.this, PlaceOrderActivity.class));
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
                startActivity(new Intent(SoupActivity.this, MainActivity.class));
                Toast.makeText(getApplicationContext(),"Logout Successful",Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case R.id.View_Account://create account page
                startActivity(new Intent(SoupActivity.this, UserAccountDetails.class));
                finish();
                return true;

            case R.id.Order_History:
                startActivity(new Intent(SoupActivity.this, CartActivity.class));
                finish();
                return true;

            case R.id.help:
                startActivity(new Intent(SoupActivity.this, help_page.class));
                finish();
                return true;

            case R.id.favorites:
                startActivity(new Intent(SoupActivity.this, favorites.class));
                finish();
                return true;
        }
        return false;
    }
}