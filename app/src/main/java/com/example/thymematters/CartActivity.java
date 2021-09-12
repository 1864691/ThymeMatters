package com.example.thymematters;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import okhttp3.HttpUrl;

public class CartActivity extends AppCompatActivity {

    Button pay;
    String CustID_FromIntent;
    LinearLayout refMainContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //Get reference to main container:
        refMainContainer = (LinearLayout)findViewById(R.id.ll_mainbox);


        //Initially we retrieve customers id:
        CustID_FromIntent = fetchCustID();


        //Test CartItem Layout: //WORKS:

        //ImageView x = new ImageView(CartActivity.this); x.setImageResource(R.drawable.meat4);
        //CartItemCustomView CART_ITEM = new CartItemCustomView(CartActivity.this,x);
        //LinearLayout tester = findViewById(R.id.ll_mainbox);
        //tester.addView(CART_ITEM);

        //Another Test: //WORKS:

        //ImageView delete_bin = new ImageView(this); delete_bin.setImageResource(R.drawable.ic_baseline_delete_24);
        //ImageView x = new ImageView(CartActivity.this);
        //PopulateImageViewFromURL.DownloadImageTask k = new PopulateImageViewFromURL.DownloadImageTask(x);
        //k.execute("https://thymematters.000webhostapp.com/MEAL_PICTURES/SOUP/ASIAN_CORN_SOUP.jpg");
        //CartItemCustomView CART_ITEM = new CartItemCustomView(CartActivity.this,x,"Asian Soup",
        //        "Glucose free","2","495",delete_bin);
        //refMainContainer.addView(CART_ITEM);

       // ImageView delete_bin2 = new ImageView(this); delete_bin2.setImageResource(R.drawable.ic_baseline_delete_24);
       // ImageView x2 = new ImageView(CartActivity.this);
        //PopulateImageViewFromURL.DownloadImageTask k2 = new PopulateImageViewFromURL.DownloadImageTask(x2);
        //k2.execute("https://thymematters.000webhostapp.com/MEAL_PICTURES/SOUP/ASIAN_CORN_SOUP.jpg");
       //CartItemCustomView CART_ITEM2 = new CartItemCustomView(CartActivity.this,x2,"Asian Soup",
       //         "Glucose free","2","495",delete_bin2);
       // refMainContainer.addView(CART_ITEM2);

       //Last Test
        //for(int i = 0 ; i < 20;  i++){
        //    TextView mkl = new TextView(this); mkl.setText("HELLO");refMainContainer.addView(mkl);
        //}


        //Load customer's cart items:
        //Send request to fetch all cart items from CustomerCurrentCartTable:
        //Send network request to 000webhost:
        //Define URL:
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://thymematters.000webhostapp.com/LOAD_MEALS_FOR_CUSTOMER/CUSTOMER_LOAD_MEALS.php").newBuilder();















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
                startActivity(new Intent(CartActivity.this, MainActivity.class));
                Toast.makeText(getApplicationContext(),"Logout Successful",Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case R.id.View_Account://create account page
                startActivity(new Intent(CartActivity.this, UserAccountDetails.class));
                finish();
                return true;

            case R.id.Order_History:
                startActivity(new Intent(CartActivity.this, CartActivity.class));
                finish();
                return true;

            case R.id.help:
                startActivity(new Intent(CartActivity.this, help_page.class));
                finish();
                return true;

            case R.id.favorites:
                startActivity(new Intent(CartActivity.this, favorites.class));
                finish();
                return true;
        }
        return false;
    }

    //Initially, this method is called upon opening this acity to rtrieve customers unique id from intent
    public String fetchCustID(){

        Intent getIntent = getIntent();
        String custID = getIntent.getStringExtra("CUST_ID");
        return custID;
    }
}