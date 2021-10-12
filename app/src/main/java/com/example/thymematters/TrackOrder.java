package com.example.thymematters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class TrackOrder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);
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
                startActivity(new Intent(TrackOrder.this, MainActivity.class));
                Toast.makeText(getApplicationContext(),"Logout Successful",Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case R.id.View_Account://create account page
                startActivity(new Intent(TrackOrder.this, CartActivity.class));
                finish();
                return true;

            case R.id.Order_History:
                startActivity(new Intent(TrackOrder.this, CartActivity.class));
                finish();
                return true;

            case R.id.help:
                startActivity(new Intent(TrackOrder.this, help_page.class));
                finish();
                return true;

            case R.id.favorites:
                startActivity(new Intent(TrackOrder.this, favorites.class));
                finish();
                return true;
        }
        return false;
    }
}