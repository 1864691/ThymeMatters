package com.example.thymematters;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class View_Reports extends AppCompatActivity {

    Button sales;
    Button meals;
    Button customer;
    Button report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports);

        sales = findViewById(R.id.salesReportbtn);
        meals = findViewById(R.id.mealsReportBtn);
        customer = findViewById(R.id.customerReportbtn);
        report = findViewById(R.id.reportbtn);

        sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(View_Reports.this, Sales_Report.class));
                finish();
            }
        });

        meals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(View_Reports.this, Meals_Report.class));
                finish();
            }
        });

        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(View_Reports.this, Customer_Report.class));
                finish();
            }
        });

//        report.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(View_Reports.this, Sales_Report.class));
//                finish();
//            }
//        });
    }
}