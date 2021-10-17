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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

public class CartActivity extends AppCompatActivity {

    Button pay;
    String CustID_FromIntent;
    LinearLayout refMainContainer;
    int TOTALPRICE = 0;
    int NUMITEMS = 0;


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
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://thymematters.000webhostapp.com/CUSTOMER_CART/LOAD_CART_ITEMS.php").newBuilder();
        //If you want to add query parameters:
        urlBuilder.addQueryParameter("cust_id",CustID_FromIntent);
        //urlBuilder.addQueryParameter("password",Password);

        String url = urlBuilder.build().toString();
        //Check if network is available: https://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
        boolean networkAvailable = isNetworkAvailable();
        if(!networkAvailable){ StyleableToast.makeText(CartActivity.this, "No Internet Connection", Toast.LENGTH_LONG, R.style.noInternet).show(); return;}

        //Send Request

        //Initialise progree bar: https://stackoverflow.com/questions/15083226/waiting-progress-bar-in-android
        //Progress Bar Functions: https://www.journaldev.com/9652/android-progressdialog-example
        final ProgressDialog progressDialog = ProgressDialog.show(this, "Loading Cart Items", "Please wait...");

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

                    CartActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (myResponse.equals("no_cart_items")){
                                progressDialog.dismiss();
                                Toast.makeText(CartActivity.this,"Your cart is empty",Toast.LENGTH_LONG).show();
                                //Set TEXTVIEWS FOR NUMITEMS AND TOTALPRICE:
                                TextView noItems = (TextView)findViewById(R.id.txtNum_items); noItems.setText("0");
                                TextView totalPrice = (TextView)findViewById(R.id.txtTotalPrice); totalPrice.setText("R 0");


                            }
                            else{
                                //Customer has cart items

                                try {
                                    JSON_cart_items_output(myResponse);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
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
                startActivity(new Intent(CartActivity.this, MainActivity.class));
                Toast.makeText(getApplicationContext(),"Logout Successful",Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case R.id.View_Account://create account page
                Intent user_acc = new Intent(CartActivity.this,UserAccountDetails.class);

                user_acc.putExtra("CUST_ID",CustID_FromIntent);

                startActivity(user_acc); finish();
                return true;

            case R.id.Order_History:
                Intent order_history = new Intent(CartActivity.this,CustomerViewOrderHistory.class);
                order_history.putExtra("CUST_ID",CustID_FromIntent);
                finish();
                startActivity(order_history);
                return true;

            case R.id.help:
                startActivity(new Intent(CartActivity.this, help_page.class));
                finish();
                return true;

            case R.id.favorites:
                // Need to pass customer unique id to favourites activity
                Intent fav = new Intent(CartActivity.this,favorites.class);
                //Pass data to customer home screen:
                fav.putExtra("CUST_ID",CustID_FromIntent);
                finish();
                startActivity(fav);

            case R.id.cart:
                Intent goToCart = new Intent(CartActivity.this,CartActivity.class);
                goToCart.putExtra("CUST_ID",CustID_FromIntent);
                finish();
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

    public void JSON_cart_items_output(String json_string) throws JSONException {
        //int TOTALPRICE = 0;
        //int NUMITEMS = 0;
        final ProgressDialog dialog = new ProgressDialog(CartActivity.this);
        dialog.setTitle("Loading Cart Items");
        dialog.setMessage("Please wait...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
        LinearLayout MAIN_LAYOUT = (LinearLayout)findViewById(R.id.ll_mainbox) ;
        JSONArray myJSONArray = new JSONArray(json_string);
        for(int i = 0 ; i < myJSONArray.length();i++) {
            JSONObject myJSONObject = myJSONArray.getJSONObject(i);
            String MEAL_NAME = myJSONObject.getString("MEAL_NAME");
            String ADDITIONAL_NOTES = myJSONObject.getString("ADDITIONAL_NOTES");
            String SERVING_SIZE = myJSONObject.getString("SERVING_SIZE");
            String PRICE = myJSONObject.getString("ITEM_TOTAL_COST");
            String CUST_CURR_CART_ITEM_ID = myJSONObject.getString("CUST_CURR_CART_ITEM_ID");
            ImageView PIC_OF_FOOD_ITEM = new ImageView(CartActivity.this);
            PopulateImageViewFromURL.DownloadImageTask k = new PopulateImageViewFromURL.DownloadImageTask(PIC_OF_FOOD_ITEM);
            k.execute(myJSONArray.getJSONObject(i).getString("MEAL_PICTURE_LINK"));

            //Sets num of items at top of page
            NUMITEMS = NUMITEMS + 1;

            //Sets Price at top of page:
            TOTALPRICE = TOTALPRICE + Integer.parseInt(PRICE);


            //Create delete bin image view:
            ImageView bin_delete_from_cart = new ImageView(this);
            bin_delete_from_cart.setImageResource(R.drawable.ic_baseline_delete_24);

            //Before putting delete bin into custom layout, define an on click for customer to remove item from cart
            bin_delete_from_cart.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Your code.

                    new AlertDialog.Builder(CartActivity.this,R.style.AlertDialogTheme)
                            .setTitle("Remove Meal from Cart")
                            .setMessage("Are you sure you would like to remove "+'"'+MEAL_NAME+'"'+" from your cart?")
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    //Do nothing

                                }
                            })

                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface arg0, int arg1) {
                                    //Send request to delete this cart item and then after deletion load new instance of this activity

                                    HttpUrl.Builder urlBuilder = HttpUrl.parse("https://thymematters.000webhostapp.com/CUSTOMER_CART/DELETE_ITEM_FROM_CUSTOMER_CURRENT_CART.php").newBuilder();
                                    urlBuilder.addQueryParameter("cust_curr_cart_item_id", CUST_CURR_CART_ITEM_ID);

                                    String url = urlBuilder.build().toString();

                                    //Sending request:
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

                                                CartActivity.this.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        //Successfuly deleted cart item
                                                        StyleableToast.makeText(CartActivity.this, "Removed from cart", Toast.LENGTH_LONG, R.style.success).show();
                                                        //Load new instance of current activity and end this one:
                                                        Intent goToCart = new Intent(CartActivity.this,CartActivity.class);
                                                        goToCart.putExtra("CUST_ID",CustID_FromIntent);
                                                        startActivity(goToCart); finish();


                                                    }
                                                });
                                            }

                                        }
                                    });
                                }
                            }).create().show();

















                }
            });



            //Make Custom layout:
            CartItemCustomView cart_item_instance = new CartItemCustomView(this,PIC_OF_FOOD_ITEM,MEAL_NAME,ADDITIONAL_NOTES,SERVING_SIZE,PRICE,bin_delete_from_cart);
            MAIN_LAYOUT.addView(cart_item_instance);

        }

        //Set TEXTVIEWS FOR NUMITEMS AND TOTALPRICE:
        TextView noItems = (TextView)findViewById(R.id.txtNum_items); noItems.setText(Integer.toString(NUMITEMS));
        TextView totalPrice = (TextView)findViewById(R.id.txtTotalPrice); totalPrice.setText("R "+ Integer.toString(TOTALPRICE));


        long delayInMillis = 4000;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, delayInMillis);
    }

    //The following method is for the customer to checkout (ie. To place their order)
    public void doCHECKOUT(View v){

        //If cart is empty, customer cannot checkout
        if(NUMITEMS==0){
            StyleableToast.makeText(CartActivity.this, "Your cart is empty", Toast.LENGTH_LONG, R.style.adminAwaitingCertification).show(); return;
        }

        //If internet is off, customer cannot checkout.
        boolean networkAvailable = isNetworkAvailable();
        if(!networkAvailable){ StyleableToast.makeText(CartActivity.this, "No Internet Connection", Toast.LENGTH_LONG, R.style.noInternet).show(); return;}

        //If both above ifs pass, it means the customer has something in their cart, they have internet connection AND they wish to checkout:
        //Intent parameters to be passed to checkout activity:
            //Customer_ID from intent was already declared in oncreate.
            //Order_Total_Price already declared - it is called TOTALPRICE

        Intent check_out = new Intent(CartActivity.this,Checkout.class);
        check_out.putExtra("CUST_ID",CustID_FromIntent);
        check_out.putExtra("TOTALPRICE",Integer.toString(TOTALPRICE));
        startActivity(check_out);
        finish();



    }

}

