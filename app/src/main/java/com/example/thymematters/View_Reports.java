package com.example.thymematters;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class View_Reports extends AppCompatActivity {

    Button sales;
    Button meals;
    Button customer;
    Button report;
    Button paymentMethod;
    Button analytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports);

        sales = findViewById(R.id.salesReportbtn);
        meals = findViewById(R.id.mealsReportBtn);
        customer = findViewById(R.id.customerReportbtn);
        report = findViewById(R.id.reportbtn);
        paymentMethod = findViewById(R.id.paymentMethodReportbtn);
        analytics = findViewById(R.id.reportbtn);

        sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(View_Reports.this, Sales_Report.class));
                //finish();
            }
        });

        meals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(View_Reports.this, Meals_Report.class));
                //finish();
            }
        });

        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(View_Reports.this, Customer_Report.class));
                //finish();
            }
        });

        paymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(View_Reports.this, PaymentMethodReport.class));
                //finish();
            }
        });

        analytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(View_Reports.this, Analytics.class));

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                //onLogoutClick();
                startActivity(new Intent(View_Reports.this, MainActivity.class));
                Toast.makeText(getApplicationContext(),"Logout Successful",Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case R.id.view_orders://create account page
                startActivity(new Intent(View_Reports.this, View_Orders.class));
                finish();
                return true;

            case R.id.View_Admin_Account:
                startActivity(new Intent(View_Reports.this, AdminAccountDetails.class));
                finish();
                return true;

        }
        return false;
    }
}