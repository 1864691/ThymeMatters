package com.example.thymematters;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//import com.google.android.gms.analytics.Tracker;

public class AdminHome extends AppCompatActivity {

    Button view_orders;
    Button view_reports;
    Button edit_menu;
    Button manage_deliveries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        view_orders = findViewById(R.id.view_orders);
        view_reports = findViewById(R.id.view_reports);
        edit_menu = findViewById(R.id.edit_menu);
        manage_deliveries = findViewById(R.id.manage_deliveries);


        view_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this, View_Orders.class));
                //finish();
            }
        });

        view_reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this, View_Reports.class));
                //finish();
            }
        });

        edit_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this, Edit_Menu.class));
                //finish();
            }
        });

        manage_deliveries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHome.this, ManageDeliveries.class));
                //finish();
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

            case R.id.view_orders://view orders activity
                startActivity(new Intent(AdminHome.this, View_Orders.class));
                //finish();
                return true;

            case R.id.View_Admin_Account:
                startActivity(new Intent(AdminHome.this, AdminAccountDetails.class));
                 //finish();
                return true;

        }
        return false;
    }
}