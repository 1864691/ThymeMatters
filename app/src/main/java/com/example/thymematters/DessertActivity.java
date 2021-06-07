package com.example.thymematters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class DessertActivity extends AppCompatActivity {

    ImageView Dessert1;
    ImageView Dessert2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dessert);

        Dessert1 = findViewById(R.id.dessert1);
        Dessert2 = findViewById(R.id.dessert2);

        Dessert1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DessertActivity.this, CartActivity.class));
                finish();
            }
        });
        Dessert2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DessertActivity.this, CartActivity.class));
                finish();
            }
        });
    }
}