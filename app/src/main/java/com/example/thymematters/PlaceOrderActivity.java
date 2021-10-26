package com.example.thymematters;

import static com.example.thymematters.URLs.URL_MEALS;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PlaceOrderActivity extends AppCompatActivity {

    Spinner quantity;
    Spinner category;
    Spinner spMeal;
    Button pay;
    Button date;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;

    //
    public static final String TAG_MEAL_NAME = "meal_name";

    public static final String JSON_ARRAY = "result";
    private JSONArray result;
    private ArrayList<String> MealList;
    //

    OkHttpClient client = new OkHttpClient();

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

       /* meal = findViewById(R.id.spin_meal);
        String[] meals = new String[]{"Creamy Mushroom Tuna Steaks", "Crumbed Sole", "Sesame Coated Tuna Poke Bowl", "Hake Curry in Coconut Milk", "Thai Style Fish Cakes"};
        ArrayAdapter<String> meal_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, meals);
        meal.setAdapter(meal_adapter);
*/
        ///
        //setting the meal spinner to the category list chosen by user in previous spinner


        HttpUrl.Builder urlBuilder = HttpUrl.parse(URL_MEALS).newBuilder();
        String url_method = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url_method)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                final String responseData = response.body().string();
                PlaceOrderActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getMeals(responseData);
                    }
                });
            }

            @Override
            public void onFailure(Call arg0, IOException arg1) {
                arg1.printStackTrace();

            }
        });

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

    //CODE TO GET MEALS ARRAY FROM SQL and populates spinner
    public ArrayList<String> getMeals(String json){
        JSONArray jsonArray = null;
        ArrayList<String> MealList = new ArrayList<String>();
        spMeal = (Spinner) findViewById(R.id.spin_meal);

        try {
            jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++){
                //specify the array to load the meal that is associated with the one clicked
                //set default to the one clicked previously
                MealList.add(jsonArray.getJSONObject(i).getString("MEAL_NAME"));
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(PlaceOrderActivity.this, android.R.layout.simple_expandable_list_item_1, MealList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spMeal.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return MealList;
    }


    //menu navigation initializer but not working
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
                startActivity(new Intent(PlaceOrderActivity.this, MainActivity.class));
                Toast.makeText(getApplicationContext(),"Logout Successful",Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case R.id.View_Account://create account page
                startActivity(new Intent(PlaceOrderActivity.this, UserAccountDetails.class));
                //finish();
                return true;

            case R.id.Order_History:
                startActivity(new Intent(PlaceOrderActivity.this, CartActivity.class));
                //finish();
                return true;

            case R.id.help:
                startActivity(new Intent(PlaceOrderActivity.this, help_page.class));
                //finish();
                return true;

            case R.id.favorites:
                startActivity(new Intent(PlaceOrderActivity.this, favorites.class));
                //finish();
                return true;
        }
        return false;
    }
}