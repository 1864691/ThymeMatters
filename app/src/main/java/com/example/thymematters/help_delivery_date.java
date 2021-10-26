package com.example.thymematters;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class help_delivery_date extends AppCompatActivity {

    Button new_date;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_delivery_date);

        TextView textView=findViewById(R.id.text);
        new_date = findViewById(R.id.new_date);

        new_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(help_delivery_date.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        new_date.setText(year + "-" + (month + 1) + "-" + day);
                    }
                }, year, month, dayOfMonth);
                //this ensures only future dates can be selected
                //datePickerDialog.getDatePicker().setMinDate();;
                datePickerDialog.show();
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
                startActivity(new Intent(help_delivery_date.this, MainActivity.class));
                Toast.makeText(getApplicationContext(),"Logout Successful",Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case R.id.View_Account://create account page
                startActivity(new Intent(help_delivery_date.this, UserAccountDetails.class));
                //finish();
                return true;

            case R.id.Order_History:
                startActivity(new Intent(help_delivery_date.this, CartActivity.class));
                //finish();
                return true;

            case R.id.help:
                startActivity(new Intent(help_delivery_date.this, help_page.class));
                //finish();
                return true;

            case R.id.favorites:
                startActivity(new Intent(help_delivery_date.this, favorites.class));
                //finish();
                return true;
        }
        return false;
    }
}