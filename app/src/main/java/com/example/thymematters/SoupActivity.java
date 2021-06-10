package com.example.thymematters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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
}