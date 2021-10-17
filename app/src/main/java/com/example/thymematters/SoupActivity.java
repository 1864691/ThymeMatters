package com.example.thymematters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import io.github.muddz.styleabletoast.StyleableToast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SoupActivity extends AppCompatActivity {


    String CustID_FromIntent;
    LinearLayout main_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soup);

        //Reference to main container
        LinearLayout main_container = (LinearLayout)findViewById(R.id.ll_mainbox);



        //Initially retrieve customer unique id from intent:
        CustID_FromIntent = fetchCustID();
        Toast.makeText(this,CustID_FromIntent, Toast.LENGTH_LONG).show();

        //Send request to fetch all soup meals from meals table:
        //Send network request to 000webhost:
        //Define URL:
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://thymematters.000webhostapp.com/LOAD_MEALS_FOR_CUSTOMER/CUSTOMER_LOAD_MEALS.php").newBuilder();

        //If you want to add query parameters:
        urlBuilder.addQueryParameter("meal_category_name","Soup");
        //urlBuilder.addQueryParameter("password",Password);

        String url = urlBuilder.build().toString();
        //Check if network is available: https://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
        boolean networkAvailable = isNetworkAvailable();
        if(!networkAvailable){ StyleableToast.makeText(SoupActivity.this, "No Internet Connection", Toast.LENGTH_LONG, R.style.noInternet).show(); return;}

        //Send Request

        //Initialise progree bar: https://stackoverflow.com/questions/15083226/waiting-progress-bar-in-android
        //Progress Bar Functions: https://www.journaldev.com/9652/android-progressdialog-example
        final ProgressDialog progressDialog = ProgressDialog.show(this, "Fetching Soup Meals", "Please wait...");

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

                    SoupActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            //Process Response Here:
                            try{
                                JSON_meals_output(myResponse);
                            }
                            catch(JSONException e){
                                e.printStackTrace();
                            }

                            progressDialog.dismiss();

                        }
                    });
                }

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
                startActivity(new Intent(SoupActivity.this, MainActivity.class));
                Toast.makeText(getApplicationContext(),"Logout Successful",Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case R.id.View_Account://create account page
                Intent user_acc = new Intent(SoupActivity.this,UserAccountDetails.class);

                user_acc.putExtra("CUST_ID",CustID_FromIntent);

                startActivity(user_acc); finish();
                return true;

            case R.id.Order_History:
                Intent order_history = new Intent(SoupActivity.this,CustomerViewOrderHistory.class);
                order_history.putExtra("CUST_ID",CustID_FromIntent);
                finish();
                startActivity(order_history);
                return true;

            case R.id.help:
                startActivity(new Intent(SoupActivity.this, help_page.class));
                finish();
                return true;

            case R.id.favorites:
                // Need to pass customer unique id to favourites activity
                Intent fav = new Intent(SoupActivity.this,favorites.class);
                //Pass data to customer home screen:
                fav.putExtra("CUST_ID",CustID_FromIntent);
                startActivity(fav);

                return true;

            case R.id.cart:
                Intent goToCart = new Intent(SoupActivity.this,CartActivity.class);
                goToCart.putExtra("CUST_ID",CustID_FromIntent);
                startActivity(goToCart);


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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void JSON_meals_output (String json_string) throws JSONException {
        final ProgressDialog dialog = new ProgressDialog(SoupActivity.this);
        dialog.setTitle("Fetching Soup Meals");
        dialog.setMessage("Please wait...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
        LinearLayout MAIN_LAYOUT = (LinearLayout)findViewById(R.id.ll_mainbox) ;
        //This method creates imageviews for all the meals along with onclicks for each to order them:
        JSONArray myJSONArray = new JSONArray(json_string);
        for(int i = 0 ; i < myJSONArray.length();i++){
            JSONObject myJSONObject = myJSONArray.getJSONObject(i);
            String MEAL_ID = myJSONObject.getString("MEAL_ID");
            String MEAL_NAME = myJSONObject.getString("MEAL_NAME");
            ImageView PIC_OF_FOOD_ITEM = new ImageView(SoupActivity.this);
            PopulateImageViewFromURL.DownloadImageTask k = new PopulateImageViewFromURL.DownloadImageTask(PIC_OF_FOOD_ITEM);
            k.execute(myJSONArray.getJSONObject(i).getString("MEAL_PICTURE_LINK"));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,800);
            //ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            PIC_OF_FOOD_ITEM.setLayoutParams(params);


            //And create onClick for each item:
            PIC_OF_FOOD_ITEM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Send customer to Add_Item_To_Cart.java in order to select serving size, give additional notes.
                    //NB: The customer unique id and meal unique id must be sent to this activity with intents
                    Intent goToAddItemToCart = new Intent(SoupActivity.this,Add_Item_To_Cart.class);
                    //Pass data to Add Item to Cart page:
                    goToAddItemToCart.putExtra("CUST_ID",CustID_FromIntent);
                    goToAddItemToCart.putExtra("MEAL_ID",MEAL_ID);
                    goToAddItemToCart.putExtra("MEAL_NAME",MEAL_NAME);
                    goToAddItemToCart.putExtra("IS_SOUP","YES");
                    startActivity(goToAddItemToCart);



                }
            });

            Button favButton = new Button(this);
            favButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Add this particular meal to favourites for this particular customer
                    addMealToFavorites(CustID_FromIntent,MEAL_ID, MEAL_NAME);
                }
            });
            favButton.setText("Add "+MEAL_NAME+" to favourites");
            favButton.setBackgroundResource(R.color.cart_item_background);
            RelativeLayout.LayoutParams button_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            favButton.setLayoutParams(button_params);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(PIC_OF_FOOD_ITEM.getLayoutParams());
            lp.setMargins(0, dpToPx(40,this), 0, 0);
            PIC_OF_FOOD_ITEM.setLayoutParams(lp);


            favButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_favorite_24, 0, 0, 0);


            MAIN_LAYOUT.addView(PIC_OF_FOOD_ITEM);
            MAIN_LAYOUT.addView(favButton);
        }

        long delayInMillis = 4000;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, delayInMillis);


    }

    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public void addMealToFavorites(String Customer_ID, String Meal_ID, String meal_name){

        //Now we send request to insert favourite:
        //Send network request to 000webhost for insertion of favourite entry.
        //Define URL:
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://thymematters.000webhostapp.com/CUSTOMER_FAVOURITES/ADD_MEAL_AS_FAVOURITE.php").newBuilder();

        //If you want to add query parameters:
        urlBuilder.addQueryParameter("cust_id", Customer_ID);
        urlBuilder.addQueryParameter("meal_id", Meal_ID);

        String url = urlBuilder.build().toString();

        //Check if network is available: https://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
        boolean networkAvailable = isNetworkAvailable();
        if(!networkAvailable){ StyleableToast.makeText(SoupActivity.this, "No Internet Connection", Toast.LENGTH_LONG, R.style.noInternet).show(); return;}

        //Send Request

        //Initialise progree bar: https://stackoverflow.com/questions/15083226/waiting-progress-bar-in-android
        //Progress Bar Functions: https://www.journaldev.com/9652/android-progressdialog-example
        final ProgressDialog progressDialog = ProgressDialog.show(this, "Adding "+meal_name+ " to favourites", "Please wait...");

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

                    SoupActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            progressDialog.dismiss();

                            if(myResponse.equals("inserted")){
                                StyleableToast.makeText(SoupActivity.this, "Added to Favourites", Toast.LENGTH_LONG, R.style.favourite).show();
                            }
                            else if(myResponse.equals("Already favourited")){
                                StyleableToast.makeText(SoupActivity.this, "You have already added this item as a Favourite", Toast.LENGTH_LONG, R.style.invalidLogin).show();
                            }
                            else{
                                StyleableToast.makeText(SoupActivity.this, "Something went wrong", Toast.LENGTH_LONG, R.style.invalidLogin).show();
                            }


                        }
                    });
                }

            }
        });

    }
}