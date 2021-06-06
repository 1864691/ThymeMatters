package com.example.thymematters;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
    }
}