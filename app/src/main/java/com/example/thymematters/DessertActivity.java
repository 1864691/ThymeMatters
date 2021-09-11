package com.example.thymematters;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.muddzdev.styleabletoast.StyleableToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DessertActivity extends AppCompatActivity {

    String CustID_FromIntent;
    LinearLayout main_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dessert);

        //Initially retrieve customer unique id from intent:
        CustID_FromIntent = fetchCustID();
        Toast.makeText(this,CustID_FromIntent, Toast.LENGTH_LONG).show();

        //Send request to fetch all soup meals from meals table:
        //Send network request to 000webhost:
        //Define URL:
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://thymematters.000webhostapp.com/LOAD_MEALS_FOR_CUSTOMER/CUSTOMER_LOAD_MEALS.php").newBuilder();

        //If you want to add query parameters:
        urlBuilder.addQueryParameter("meal_category_name","Something Sweet");
        //urlBuilder.addQueryParameter("password",Password);

        String url = urlBuilder.build().toString();
        //Check if network is available: https://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
        boolean networkAvailable = isNetworkAvailable();
        if(!networkAvailable){ StyleableToast.makeText(DessertActivity.this, "No Internet Connection", Toast.LENGTH_LONG, R.style.noInternet).show(); return;}

        //Send Request

        //Initialise progree bar: https://stackoverflow.com/questions/15083226/waiting-progress-bar-in-android
        //Progress Bar Functions: https://www.journaldev.com/9652/android-progressdialog-example
        final ProgressDialog progressDialog = ProgressDialog.show(this, "Fetching Desserts", "Please wait...");

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

                    DessertActivity.this.runOnUiThread(new Runnable() {
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
                startActivity(new Intent(DessertActivity.this, MainActivity.class));
                Toast.makeText(getApplicationContext(),"Logout Successful",Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case R.id.View_Account://create account page
                startActivity(new Intent(DessertActivity.this, UserAccountDetails.class));
                finish();
                return true;

            case R.id.Order_History:
                startActivity(new Intent(DessertActivity.this, CartActivity.class));
                finish();
                return true;

            case R.id.help:
                startActivity(new Intent(DessertActivity.this, help_page.class));
                finish();
                return true;

            case R.id.favorites:
                startActivity(new Intent(DessertActivity.this, favorites.class));
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void JSON_meals_output (String json_string) throws JSONException {
        final ProgressDialog dialog = new ProgressDialog(DessertActivity.this);
        dialog.setTitle("Fetching Desserts");
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
            ImageView PIC_OF_FOOD_ITEM = new ImageView(DessertActivity.this);
            PopulateImageViewFromURL.DownloadImageTask k = new PopulateImageViewFromURL.DownloadImageTask(PIC_OF_FOOD_ITEM);
            k.execute(myJSONArray.getJSONObject(i).getString("MEAL_PICTURE_LINK"));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,800);
            //ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            PIC_OF_FOOD_ITEM.setLayoutParams(params);


            //And create onClick for each item:
            PIC_OF_FOOD_ITEM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(DessertActivity.this,"You clicked on "+MEAL_NAME,Toast.LENGTH_LONG).show();
                }
            });

            MAIN_LAYOUT.addView(PIC_OF_FOOD_ITEM);
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
}