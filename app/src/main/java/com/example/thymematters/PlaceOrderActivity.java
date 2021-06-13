package com.example.thymematters;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import java.util.Calendar;

public class PlaceOrderActivity extends AppCompatActivity {

    Spinner quantity;
    Spinner category;
    Spinner meal;
    Button pay;
    Button date;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        quantity = findViewById(R.id.spin_quantity);
        String[] quantities = new String[]{"2", "4", "6"};
        ArrayAdapter<String> quantity_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, quantities);
        quantity.setAdapter(quantity_adapter);

        category = findViewById(R.id.spin_category);
        String[] categories = new String[]{"Soup", "Fish", "Meat", "Vegetarian", "Dessert"};
        ArrayAdapter<String> category_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        category.setAdapter(category_adapter);

        meal = findViewById(R.id.spin_meal);
        String[] meals = new String[]{"Soup", "Fish", "Meat", "Vegetarian", "Dessert"};
        ArrayAdapter<String> meal_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, meals);
        category.setAdapter(meal_adapter);

        pay = findViewById(R.id.btn_pay);
        date = findViewById(R.id.date);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlaceOrderActivity.this, CartActivity.class));
                finish();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(PlaceOrderActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        date.setText(year + "-" + (month + 1) + "-" + day);
                    }
                }, year, month, dayOfMonth);
                //this ensures only future dates can be selected
                //datePickerDialog.getDatePicker().setMinDate();;
                datePickerDialog.show();
            }
        });

    }
}