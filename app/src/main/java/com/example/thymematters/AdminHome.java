package com.example.thymematters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AdminHome extends AppCompatActivity {

    Button view_orders;
    Button view_reports;
    Button edit_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);


        view_orders = findViewById(R.id.view_orders);
        view_reports = findViewById(R.id.view_reports);
        edit_menu = findViewById(R.id.edit_menu);

        view_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this, View_Orders.class));
                finish();
            }
        });

        view_reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this, View_Reports.class));
                finish();
            }
        });

        edit_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this, Edit_Menu.class));
                finish();
            }
        });
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
                startActivity(new Intent(AdminHome.this, MainActivity.class));
                Toast.makeText(getApplicationContext(),"Logout Successful",Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case R.id.view_orders://create account page
                startActivity(new Intent(AdminHome.this, View_Orders.class));
                finish();
                return true;

            case R.id.View_Admin_Account:
                startActivity(new Intent(AdminHome.this, AdminAccountDetails.class));
                finish();
                return true;

        }
        return false;
    }
}