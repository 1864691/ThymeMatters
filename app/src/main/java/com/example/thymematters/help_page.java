package com.example.thymematters;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class help_page extends AppCompatActivity {



    Button change_delivery, change_other, change_date, change_personal_details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_page);









        change_delivery = findViewById(R.id.change_delivery);
        change_date = findViewById(R.id.change_date);
        change_personal_details = findViewById(R.id.change_personal_details);
        change_other = findViewById(R.id.change_other);

        change_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(help_page.this, help_delivery_address.class));
                finish();
            }
        });

        change_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(help_page.this, help_delivery_date.class));
                finish();
            }
        });

        change_personal_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(help_page.this, help_change_account_details.class));
                finish();
            }
        });

        change_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(help_page.this, help_other.class));
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
                startActivity(new Intent(help_page.this, MainActivity.class));
                Toast.makeText(getApplicationContext(),"Logout Successful",Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case R.id.View_Account://create account page
                startActivity(new Intent(help_page.this, UserAccountDetails.class));
                finish();
                return true;

            case R.id.Order_History:
                startActivity(new Intent(help_page.this, CartActivity.class));
                finish();
                return true;

            case R.id.help:
                startActivity(new Intent(help_page.this, help_page.class));
                finish();
                return true;

            case R.id.favorites:
                startActivity(new Intent(help_page.this, favorites.class));
                finish();
                return true;
        }
        return false;
    }


}