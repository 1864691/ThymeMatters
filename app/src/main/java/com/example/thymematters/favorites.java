package com.example.thymematters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

public class favorites extends AppCompatActivity {

    String CustID_FromIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        //Initially retrieve customer unique id from intent:
        CustID_FromIntent = fetchCustID();
        //Toast.makeText(this,CustID_FromIntent, Toast.LENGTH_LONG).show();


        //Send request to fetch all favourite meals from favourites table:
        //Send network request to 000webhost:
        //Define URL:
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://thymematters.000webhostapp.com/CUSTOMER_FAVOURITES/LIST_CUSTOMER_FAVOURITES.php").newBuilder();

        //If you want to add query parameters:
        urlBuilder.addQueryParameter("cust_id",CustID_FromIntent);

        String url = urlBuilder.build().toString();
        //Check if network is available: https://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
        boolean networkAvailable = isNetworkAvailable();
        if(!networkAvailable){ StyleableToast.makeText(favorites.this, "No Internet Connection", Toast.LENGTH_LONG, R.style.noInternet).show(); return;}

        //Send Request

        //Initialise progree bar: https://stackoverflow.com/questions/15083226/waiting-progress-bar-in-android
        //Progress Bar Functions: https://www.journaldev.com/9652/android-progressdialog-example
        final ProgressDialog progressDialog = ProgressDialog.show(this, "Loading Favourite Meals", "Please wait...");

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

                    favorites.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Process Response Here:
                            try{
                                if(myResponse.equals("no_favourites_items")){
                                    Toast.makeText(favorites.this,"You have not Added any meal as a Favourite",Toast.LENGTH_LONG).show();
                                }
                                else{
                                    JSON_favourites_output(myResponse);
                                }

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
                startActivity(new Intent(favorites.this, MainActivity.class));
                Toast.makeText(getApplicationContext(),"Logout Successful",Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case R.id.View_Account://create account page
                startActivity(new Intent(favorites.this, UserAccountDetails.class));
                //finish();
                return true;

            case R.id.Order_History:
                startActivity(new Intent(favorites.this, CartActivity.class));
                //finish();
                return true;

            case R.id.help:
                startActivity(new Intent(favorites.this, help_page.class));
                //finish();
                return true;

            case R.id.favorites:
                // Need to pass customer unique id to favourites activity
                Intent fav = new Intent(favorites.this,favorites.class);
                //Pass data to customer home screen:
                fav.putExtra("CUST_ID",CustID_FromIntent);
                //finish();
                startActivity(fav);

            case R.id.cart:
                Intent goToCart = new Intent(favorites.this,CartActivity.class);
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

    public void JSON_favourites_output(String json_string) throws JSONException {
        final ProgressDialog dialog = new ProgressDialog(favorites.this);
        dialog.setTitle("Loading Favourite Meals");
        dialog.setMessage("Please wait...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
        LinearLayout MAIN_LAYOUT = (LinearLayout)findViewById(R.id.ll_mainbox) ;

        JSONArray myJSONArray = new JSONArray(json_string);
        for(int i = 0 ; i < myJSONArray.length();i++){
            JSONObject myJSONObject = myJSONArray.getJSONObject(i);
            String MEAL_ID = myJSONObject.getString("MEAL_ID");
            String MEAL_NAME = myJSONObject.getString("MEAL_NAME");
            String CATEGORY_NAME = myJSONObject.getString("CATEGORY_NAME");
            String FAVOURITE_ID = myJSONObject.getString("FAVOURITE_ID");
            ImageView PIC_OF_FOOD_ITEM = new ImageView(favorites.this);
            PopulateImageViewFromURL.DownloadImageTask k = new PopulateImageViewFromURL.DownloadImageTask(PIC_OF_FOOD_ITEM);
            k.execute(myJSONArray.getJSONObject(i).getString("MEAL_PICTURE_LINK"));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,800);
            PIC_OF_FOOD_ITEM.setLayoutParams(params);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(PIC_OF_FOOD_ITEM.getLayoutParams());
            lp.setMargins(0, dpToPx(40,this), 0, 0);
            PIC_OF_FOOD_ITEM.setLayoutParams(lp);

            //And create onClick for each item:
            PIC_OF_FOOD_ITEM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Send customer to Add_Item_To_Cart.java in order to select serving size, give additional notes.
                    //NB: The customer unique id and meal unique id must be sent to this activity with intents
                    Intent goToAddItemToCart = new Intent(favorites.this,Add_Item_To_Cart.class);
                    //Pass data to Add Item to Cart page:
                    goToAddItemToCart.putExtra("CUST_ID",CustID_FromIntent);
                    goToAddItemToCart.putExtra("MEAL_ID",MEAL_ID);
                    goToAddItemToCart.putExtra("MEAL_NAME",MEAL_NAME);

                    if(CATEGORY_NAME.equals("Soup")){goToAddItemToCart.putExtra("IS_SOUP","YES");}
                    else{goToAddItemToCart.putExtra("IS_SOUP","NO");}
                    startActivity(goToAddItemToCart);



                }
            });


            //Add a button for each meal giving option to emove it from the customer's favourites
            Button removeFavButton = new Button(this);
            removeFavButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Delete selected meal from favourites
                    deleteFromFavourites(FAVOURITE_ID,MEAL_NAME);

                }
            });
            removeFavButton.setText("Remove "+MEAL_NAME+" from favourites");
            removeFavButton.setBackgroundResource(R.color.cart_item_background);
            RelativeLayout.LayoutParams button_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            removeFavButton.setLayoutParams(button_params);
            RelativeLayout.LayoutParams l_p = new RelativeLayout.LayoutParams(PIC_OF_FOOD_ITEM.getLayoutParams());
            l_p.setMargins(0, dpToPx(40,this), 0, 0);
            PIC_OF_FOOD_ITEM.setLayoutParams(l_p);

            removeFavButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_delete_24, 0, 0, 0);




            MAIN_LAYOUT.addView(PIC_OF_FOOD_ITEM);
            MAIN_LAYOUT.addView(removeFavButton);


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

    public void deleteFromFavourites(String favID, String mealName){
        //Define URL:
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://thymematters.000webhostapp.com/CUSTOMER_FAVOURITES/DELETE_FAVOURITE_ENTRY.php").newBuilder();

        //If you want to add query parameters:
        urlBuilder.addQueryParameter("fav_id",favID);

        String url = urlBuilder.build().toString();
        //Check if network is available: https://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
        boolean networkAvailable = isNetworkAvailable();
        if(!networkAvailable){ StyleableToast.makeText(favorites.this, "No Internet Connection", Toast.LENGTH_LONG, R.style.noInternet).show(); return;}

        //Send Request

        //Initialise progree bar: https://stackoverflow.com/questions/15083226/waiting-progress-bar-in-android
        //Progress Bar Functions: https://www.journaldev.com/9652/android-progressdialog-example
        final ProgressDialog progressDialog = ProgressDialog.show(this, "Removing "+mealName+" from Favourites", "Please wait...");

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

                    favorites.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {



                            if(myResponse.equals("Success")){
                                progressDialog.dismiss();
                                StyleableToast.makeText(favorites.this, "Successfully removed item from favourites", Toast.LENGTH_LONG, R.style.success).show();
                                //Load new instance of current activity and end this one:
                                Intent reload = new Intent(favorites.this,favorites.class);
                                reload.putExtra("CUST_ID",CustID_FromIntent);
                                startActivity(reload); finish();
                            }
                            else{
                                progressDialog.dismiss();
                                Toast.makeText(favorites.this,"Something went wrong",Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }

            }
        });
    }
}