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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.github.muddz.styleabletoast.StyleableToast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//import com.muddzdev.styleabletoast.StyleableToast;

public class CustomerViewOrderHistory extends AppCompatActivity {

    String CustID_FromIntent;
    TextView cust_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_order_history);

        //Fetch unique cust id:
        CustID_FromIntent = fetchCustID();
        //Toast.makeText(this,CustID_FromIntent, Toast.LENGTH_LONG).show();

        //Fetch cust email:
        //Define URL:
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://thymematters.000webhostapp.com/CUST_ORDER_HISTORY/GET_CUST_EMAIL.php").newBuilder();
        urlBuilder.addQueryParameter("cust_id",CustID_FromIntent);

        String url = urlBuilder.build().toString();
        //Check if network is available: https://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
        boolean networkAvailable = isNetworkAvailable();
        if(!networkAvailable){ StyleableToast.makeText(CustomerViewOrderHistory.this, "No Internet Connection", Toast.LENGTH_LONG, R.style.noInternet).show(); return;}

        //Send Request
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

                    CustomerViewOrderHistory.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            cust_email = (TextView)findViewById(R.id.txtCustEmail);
                            cust_email.setText(myResponse);

                        }
                    });
                }

            }
        });

        //Now we load this customer's orders:
        //Define URL:
        HttpUrl.Builder urlBuilder_2 = HttpUrl.parse("https://thymematters.000webhostapp.com/CUST_ORDER_HISTORY/LOAD_CUST_ORDERS.php").newBuilder();
        //If you want to add query parameters:
        urlBuilder_2.addQueryParameter("cust_id",CustID_FromIntent);

        String url_2 = urlBuilder_2.build().toString();

        final ProgressDialog progressDialog = ProgressDialog.show(this, "Loading Order History", "Please wait...");

        OkHttpClient client_2 = new OkHttpClient();
        Request request_2 = new Request.Builder()
                .url(url_2)
                .build();

        client_2.newCall(request_2).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse( Call call,  Response response) throws IOException {
                if (response.isSuccessful()){
                    final String myResponse = response.body().string();

                    CustomerViewOrderHistory.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            progressDialog.dismiss();
                            if(myResponse.equals("no_orders")){
                                StyleableToast.makeText(CustomerViewOrderHistory.this, "You have not logged any orders", Toast.LENGTH_LONG, R.style.invalidLogin).show();
                                return;
                            }



                            try {
                                JSON_Orders_Output(myResponse);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }

            }
        });

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

    public void JSON_Orders_Output(String JSON_orders) throws JSONException {
        LinearLayout MAIN_LAYOUT = (LinearLayout)findViewById(R.id.main_order_box) ;
        JSONArray myJSONArray = new JSONArray(JSON_orders);
        for(int i = 0 ; i < myJSONArray.length();i++){
            JSONObject myJSONObject = myJSONArray.getJSONObject(i);
            String ORDER_ID = myJSONObject.getString("ORDER_ID");
            String ORDER_COST = myJSONObject.getString("ORDER_TOTAL");
            String PAYMENT_METHOD = myJSONObject.getString("PAYMENT_METHOD");
            String PAYMENT_STATUS = myJSONObject.getString("ORDER_PAYMENT_STATUS");
            String DELIVERY_STATUS = myJSONObject.getString("ORDER_DELIVERY_STATUS");
            String PLACEMENT_DATE = myJSONObject.getString("ORDER_PLACEMENT_DATE");
            String DELIVERY_DATE = myJSONObject.getString("ORDER_DELIVERY_DATE");
            String DELIVERY_ADDRESS = myJSONObject.getString("ORDER_DELIVERY_ADDRESS");

            String payment_status_for_next_activity = PAYMENT_STATUS;

            //Payment status:
            if(PAYMENT_STATUS.equals("0")){
                PAYMENT_STATUS = "Awaiting Payment";
            }
            else{
                PAYMENT_STATUS = "Payment Received";
            }

            //Create ARROW image view:
            ImageView arrow = new ImageView(this);
            arrow.setImageResource(R.drawable.ic_order_details);

            arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Take user to activity where they can view meals that they ordered within this order.
                    Intent goToViewOrderContents = new Intent(CustomerViewOrderHistory.this,ViewOrderContents.class);
                    //Pass data to Add Item to Cart page:
                    goToViewOrderContents.putExtra("ORDER_ID",ORDER_ID);
                    goToViewOrderContents.putExtra("ORDER_COST",ORDER_COST);
                    goToViewOrderContents.putExtra("PAYMENT_METHOD",PAYMENT_METHOD);
                    goToViewOrderContents.putExtra("PAYMENT_STATUS",payment_status_for_next_activity);
                    goToViewOrderContents.putExtra("DELIVERY_STATUS",DELIVERY_STATUS);
                    goToViewOrderContents.putExtra("PLACEMENT_DATE",PLACEMENT_DATE);
                    goToViewOrderContents.putExtra("DELIVERY_DATE",DELIVERY_DATE);
                    goToViewOrderContents.putExtra("DELIVERY_ADDRESS",DELIVERY_ADDRESS);
                    startActivity(goToViewOrderContents);
                }
            });

            //Make Custom layout:
            CustOrderHistoryLayout order = new CustOrderHistoryLayout(this,arrow,ORDER_ID,ORDER_COST,PAYMENT_METHOD,
                    PAYMENT_STATUS,DELIVERY_STATUS,PLACEMENT_DATE,DELIVERY_DATE,DELIVERY_ADDRESS);
            MAIN_LAYOUT.addView(order);


        }
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
                startActivity(new Intent(CustomerViewOrderHistory.this, MainActivity.class));
                Toast.makeText(getApplicationContext(),"Logout Successful",Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case R.id.View_Account://create account page
                startActivity(new Intent(CustomerViewOrderHistory.this, UserAccountDetails.class));
                finish();
                return true;

            case R.id.Order_History:
                Intent order_history = new Intent(CustomerViewOrderHistory.this,CustomerViewOrderHistory.class);
                order_history.putExtra("CUST_ID",CustID_FromIntent);
                finish();
                startActivity(order_history);
                return true;

            case R.id.help:
                startActivity(new Intent(CustomerViewOrderHistory.this, help_page.class));
                finish();
                return true;

            case R.id.favorites:
                // Need to pass customer unique id to favourites activity
                Intent fav = new Intent(CustomerViewOrderHistory.this,favorites.class);
                //Pass data to customer home screen:
                fav.putExtra("CUST_ID",CustID_FromIntent);
                finish();
                startActivity(fav);

                return true;

            case R.id.cart:
                Intent goToCart = new Intent(CustomerViewOrderHistory.this,CartActivity.class);
                goToCart.putExtra("CUST_ID",CustID_FromIntent);
                finish();
                startActivity(goToCart);


                return true;
        }
        return false;
    }
}