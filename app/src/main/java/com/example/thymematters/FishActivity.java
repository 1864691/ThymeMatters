package com.example.thymematters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class FishActivity extends AppCompatActivity {

    ImageView Fish1;
    ImageView Fish2;
    ImageView Fish3;
    ImageView Fish4;
    ImageView Fish5;
    ImageView Fish6;
    ImageView Fish7;
    ImageView Fish8;
    ImageView Fish9;
    ImageView Fish10;
    ImageView Fish11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish);

        Fish1 = findViewById(R.id.fish1);
        Fish2 = findViewById(R.id.fish2);
        Fish3 = findViewById(R.id.fish3);
        Fish4 = findViewById(R.id.fish4);
        Fish5 = findViewById(R.id.fish5);
        Fish6 = findViewById(R.id.fish6);
        Fish7 = findViewById(R.id.fish7);
        Fish8 = findViewById(R.id.fish8);
        Fish9 = findViewById(R.id.fish9);
        Fish10 = findViewById(R.id.fish10);
        Fish11 = findViewById(R.id.fish11);

        Fish1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FishActivity.this, CartActivity.class));
                finish();
            }
        });


    }
}