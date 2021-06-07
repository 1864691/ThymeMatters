package com.example.thymematters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meat);

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
                startActivity(new Intent(MeatActivity.this, CartActivity.class));
                finish();
            }
        });
        Meat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MeatActivity.this, CartActivity.class));
                finish();
            }
        });
        Meat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MeatActivity.this, CartActivity.class));
                finish();
            }
        });
        Meat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MeatActivity.this, CartActivity.class));
                finish();
            }
        });
        Meat5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MeatActivity.this, CartActivity.class));
                finish();
            }
        });
        Meat6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MeatActivity.this, CartActivity.class));
                finish();
            }
        });
        Meat7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MeatActivity.this, CartActivity.class));
                finish();
            }
        });
        Meat8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MeatActivity.this, CartActivity.class));
                finish();
            }
        });
        Meat9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MeatActivity.this, CartActivity.class));
                finish();
            }
        });
        Meat10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MeatActivity.this, CartActivity.class));
                finish();
            }
        });
        Meat11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MeatActivity.this, CartActivity.class));
                finish();
            }
        });
        Meat12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MeatActivity.this, CartActivity.class));
                finish();
            }
        });
    }
}