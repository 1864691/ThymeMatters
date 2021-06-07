package com.example.thymematters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vegetarian);

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
                startActivity(new Intent(VegetarianActivity.this, CartActivity.class));
                finish();
            }
        });
        Veg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VegetarianActivity.this, CartActivity.class));
                finish();
            }
        });
        Veg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VegetarianActivity.this, CartActivity.class));
                finish();
            }
        });
        Veg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VegetarianActivity.this, CartActivity.class));
                finish();
            }
        });
        Veg5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VegetarianActivity.this, CartActivity.class));
                finish();
            }
        });
        Veg6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VegetarianActivity.this, CartActivity.class));
                finish();
            }
        });
        Veg7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VegetarianActivity.this, CartActivity.class));
                finish();
            }
        });
        Veg8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VegetarianActivity.this, CartActivity.class));
                finish();
            }
        });
        Veg9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VegetarianActivity.this, CartActivity.class));
                finish();
            }
        });
        Veg10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VegetarianActivity.this, CartActivity.class));
                finish();
            }
        });
        Veg11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VegetarianActivity.this, CartActivity.class));
                finish();
            }
        });
        Veg12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VegetarianActivity.this, CartActivity.class));
                finish();
            }
        });
    }
}