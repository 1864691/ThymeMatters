package com.example.thymematters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Edit_Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu admin_menu){
        getMenuInflater().inflate(R.menu.admin_menu, admin_menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                //onLogoutClick();
                startActivity(new Intent(Edit_Menu.this, MainActivity.class));
                Toast.makeText(getApplicationContext(),"Logout Successful",Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case R.id.view_orders://create account page
                startActivity(new Intent(Edit_Menu.this, View_Orders.class));
                finish();
                return true;

            case R.id.View_Admin_Account:
                startActivity(new Intent(Edit_Menu.this, AdminAccountDetails.class));
                finish();
                return true;

        }
        return false;
    }
}