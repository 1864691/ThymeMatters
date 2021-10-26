package com.example.thymematters;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class help_other extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_other);
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
                startActivity(new Intent(help_other.this, MainActivity.class));
                Toast.makeText(getApplicationContext(),"Logout Successful",Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case R.id.View_Account://create account page
                startActivity(new Intent(help_other.this, UserAccountDetails.class));
                //finish();
                return true;

            case R.id.Order_History:
                startActivity(new Intent(help_other.this, CartActivity.class));
                //finish();
                return true;

            case R.id.help:
                startActivity(new Intent(help_other.this, help_page.class));
                //finish();
                return true;

            case R.id.favorites:
                startActivity(new Intent(help_other.this, favorites.class));
                //finish();
                return true;
        }
        return false;
    }
}