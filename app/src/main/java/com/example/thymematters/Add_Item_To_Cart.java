package com.example.thymematters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import io.github.muddz.styleabletoast.StyleableToast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Add_Item_To_Cart extends AppCompatActivity {

    TextView MEAL_NAME;
    Spinner spinnerSERVING_SIZE;
    String cust_id;
    String meal_id;
    String meal_name;
    String isSoup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_to_cart);

        //Test that intents are being received:
        cust_id = fetchCustID();
        meal_id = fetchMealID();
        meal_name = fetchMealName();
        //All correct
        //Toast.makeText(this,"Customer ID: " +cust_id, Toast.LENGTH_LONG).show();
        //Toast.makeText(this,"Meal ID: " +meal_id, Toast.LENGTH_LONG).show();
        //Toast.makeText(this,"Meal Name: " +meal_name, Toast.LENGTH_LONG).show();

        //Check if customer is ordering soup, if they are the pricing is different
        isSoup = isSoup();


        //Inform user what they have selected:
        MEAL_NAME = (TextView)findViewById(R.id.txtMealName);
        MEAL_NAME.setText(meal_name);

        //Populate Spinner:
        spinnerSERVING_SIZE = findViewById(R.id.spinnerServingSize);


        //Change serving sizes and pricing if soup is chosen
        TextView pricing = (TextView)findViewById(R.id.NotePrices);
        String[] servingSizes;
        if(isSoup.equals("YES")){
            servingSizes = new String[]{"6-8 Servings"};
            //And change red textview with prices
            pricing.setText("Soup Price:\n* 6-8 Servings - R495");
        }
        else{
            servingSizes = new String[]{"2 Servings", "4 Servings", "6 Servings"};


        }
        ArrayAdapter<String> servings_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, servingSizes);
        spinnerSERVING_SIZE.setAdapter(servings_adapter);
    }

    //Intent Communication, fetch CUST_ID
    public String fetchCustID(){

        Intent getIntent = getIntent();
        String custID = getIntent.getStringExtra("CUST_ID");
        return custID;
    }

    //Intent Communication, fetch MEAL_ID
    public String fetchMealID() {
        Intent getIntent = getIntent();
        String mealID = getIntent.getStringExtra("MEAL_ID");
        return mealID;
    }

    //Intent Communication, fetch MEAL_NAME
    public String fetchMealName() {
        Intent getIntent = getIntent();
        String mealNAME = getIntent.getStringExtra("MEAL_NAME");
        return mealNAME;
    }

    //Intent Communication - check if soup because soup is different pricing
    public String isSoup() {
        Intent getIntent = getIntent();
        String isSoup = getIntent.getStringExtra("IS_SOUP");
        return isSoup;
    }

    //For the top right menu:
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
                startActivity(new Intent(Add_Item_To_Cart.this, MainActivity.class));
                Toast.makeText(getApplicationContext(),"Logout Successful",Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case R.id.View_Account://create account page
                startActivity(new Intent(Add_Item_To_Cart.this, UserAccountDetails.class));
                //finish();
                return true;

            case R.id.Order_History:
                startActivity(new Intent(Add_Item_To_Cart.this, CartActivity.class));
                //finish();
                return true;

            case R.id.help:
                startActivity(new Intent(Add_Item_To_Cart.this, help_page.class));
                //finish();
                return true;

            case R.id.favorites:
                startActivity(new Intent(Add_Item_To_Cart.this, favorites.class));
                //finish();
                return true;

            case R.id.cart:
                Intent goToCart = new Intent(Add_Item_To_Cart.this,CartActivity.class);
                goToCart.putExtra("CUST_ID",cust_id);
                startActivity(goToCart);


                return true;
        }
        return false;
    }

    public void doAddToCustomersCart(View v){

        //First get all values to insert:

        String CUSTOMER_UNIQUE_ID = cust_id;
        String MEAL_UNIQUE_ID = meal_id;
        String ITEM_TOTAL_COST;
        //Get selected item in spinner:
        String strSERVING_SIZE = spinnerSERVING_SIZE.getSelectedItem().toString();
        //Get additional notes:
        EditText refAddNotes = (EditText)findViewById(R.id.et_Additional_Notes);
        String ADDITIONAL_NOTES = refAddNotes.getText().toString();
        if(ADDITIONAL_NOTES.equals("")){ADDITIONAL_NOTES = "None"; }

        if(strSERVING_SIZE.equals("6-8 Servings")){
            strSERVING_SIZE = "8";
            ITEM_TOTAL_COST = "495";
        }
        else if(strSERVING_SIZE.equals("2 Servings")){
            strSERVING_SIZE = "2";
            ITEM_TOTAL_COST = "345";
        }
        else if(strSERVING_SIZE.equals("4 Servings")){
            strSERVING_SIZE = "4";
            ITEM_TOTAL_COST = "495";
        }
        else{
            strSERVING_SIZE = "6";
            ITEM_TOTAL_COST = "645";
        }








        //Send request to insert meal into customer current cart table:
        //Send network request to 000webhost:
        //Define URL:
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://thymematters.000webhostapp.com/CUSTOMER_ADD_MEAL_TO_CART/ADD_MEAL_TO_CART.php").newBuilder();

        //If you want to add query parameters:
        urlBuilder.addQueryParameter("CUSTOMER_UNIQUE_ID",cust_id);
        urlBuilder.addQueryParameter("MEAL_UNIQUE_ID",meal_id);
        urlBuilder.addQueryParameter("ITEM_TOTAL_COST",ITEM_TOTAL_COST);
        urlBuilder.addQueryParameter("ADDITIONAL_NOTES",ADDITIONAL_NOTES);
        urlBuilder.addQueryParameter("SERVING_SIZE",strSERVING_SIZE);
        //urlBuilder.addQueryParameter("password",Password);

        String url = urlBuilder.build().toString();
        //Check if network is available: https://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
        boolean networkAvailable = isNetworkAvailable();
        if(!networkAvailable){ StyleableToast.makeText(Add_Item_To_Cart.this, "No Internet Connection", Toast.LENGTH_LONG, R.style.noInternet).show(); return;}

        //Send Request

        //Initialise progree bar: https://stackoverflow.com/questions/15083226/waiting-progress-bar-in-android
        //Progress Bar Functions: https://www.journaldev.com/9652/android-progressdialog-example
        final ProgressDialog progressDialog = ProgressDialog.show(this, "Adding meal to cart", "Please wait...");

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse( Call call,  Response response) throws IOException {
                if (response.isSuccessful()){
                    final String myResponse = response.body().string();

                    Add_Item_To_Cart.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(myResponse.equals("cart_item_inserted")){
                                progressDialog.dismiss();
                                new AlertDialog.Builder(Add_Item_To_Cart.this,R.style.AlertDialogTheme)
                                        .setTitle("Meal Added")
                                        .setMessage("You have successfully added "+ meal_name + " to your cart.")
                                        .setCancelable(false)

                                        //.setNegativeButton("NO",null)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface arg0, int arg1) {
                                                //customer_home_screen.super.onBackPressed();
                                                Intent back_to_customer_home_screen = new Intent(Add_Item_To_Cart.this,HomeActivity.class);
                                                back_to_customer_home_screen.putExtra("CUST_ID",cust_id);
                                                startActivity(back_to_customer_home_screen); finish();
                                            }
                                        }).create().show();
                            }
                            else{
                                progressDialog.dismiss();
                                Toast.makeText(Add_Item_To_Cart.this,"Something went wrong", Toast.LENGTH_LONG).show();
                            }



                        }
                    });
                }

            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
