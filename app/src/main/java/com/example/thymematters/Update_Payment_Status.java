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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

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

//import com.muddzdev.styleabletoast.StyleableToast;

public class Update_Payment_Status extends AppCompatActivity {

    String ORDER_ID_FROM_INTENT;
    String ORDER_COST_FROM_INTENT;
    String PAYMENT_METHOD_FROM_INTENT;
    String PAYMENT_STATUS_FROM_INTENT;
    String DELIVERY_STATUS_FROM_INTENT;
    String PLACEMENT_DATE_FROM_INTENT;
    String DELIVERY_DATE_FROM_INTENT;
    String DELIVERY_ADDRESS_FROM_INTENT;

    LinearLayout refOrderContainer;

    Button Confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_payment_status);

        //Receive intents:
        ORDER_ID_FROM_INTENT = FETCH_ORDER_ID();
        ORDER_COST_FROM_INTENT = FETCH_ORDER_COST();
        PAYMENT_METHOD_FROM_INTENT = FETCH_PAYMENT_METHOD();
        PAYMENT_STATUS_FROM_INTENT = FETCH_PAYMENT_STATUS();
        DELIVERY_STATUS_FROM_INTENT = FETCH_DELIVERY_STATUS();
        PLACEMENT_DATE_FROM_INTENT = FETCH_PLACEMENT_DATE();
        DELIVERY_DATE_FROM_INTENT = FETCH_DELIVERY_DATE();
        DELIVERY_ADDRESS_FROM_INTENT = FETCH_DELIVERY_ADDRESS();

        //Now make order layout and add to order_detail_box
        refOrderContainer = (LinearLayout)findViewById(R.id.order_detail_box);
        ImageView arrow = new ImageView(this);
        CustOrderHistoryLayout order = new CustOrderHistoryLayout(this,arrow,ORDER_ID_FROM_INTENT,ORDER_COST_FROM_INTENT,PAYMENT_METHOD_FROM_INTENT,
                PAYMENT_STATUS_FROM_INTENT,DELIVERY_STATUS_FROM_INTENT,PLACEMENT_DATE_FROM_INTENT,DELIVERY_DATE_FROM_INTENT,
                DELIVERY_ADDRESS_FROM_INTENT);
        refOrderContainer.addView(order);

        Button btnUpdate = (Button) findViewById(R.id.Confirm);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //update
                //Define URL:
                HttpUrl.Builder urlBuilder = HttpUrl.parse("https://thymematters.000webhostapp.com/UPDATE_PAYMENT_STATUS.php").newBuilder();

                //If you want to add query parameters:
                urlBuilder.addQueryParameter("order_id",ORDER_ID_FROM_INTENT);

                String url = urlBuilder.build().toString();
                //Check if network is available: https://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
                boolean networkAvailable = isNetworkAvailable();
                if(!networkAvailable){ StyleableToast.makeText(Update_Payment_Status.this, "No Internet Connection", Toast.LENGTH_LONG, R.style.noInternet).show(); return;}


                //send request
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

                            Update_Payment_Status.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    //try {

                                        StyleableToast.makeText(Update_Payment_Status.this, "Payment Status Updated Successfully", Toast.LENGTH_LONG, R.style.success).show();
                                        //JSON_update(myResponse);

                                        Intent i = new Intent(Update_Payment_Status.this, View_Orders.class);
                                        finish();
                                        //overridePendingTransition(0, 0);
                                        startActivity(i);
                                        //overridePendingTransition(0, 0);

                                        //recreate();

                                   // } catch (JSONException e) {
                                       // e.printStackTrace();
                                   // }

                                }
                            });
                        }

                    }
                });
            }
        });

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void JSON_update(String response) throws JSONException{
        final ProgressDialog dialog = new ProgressDialog(Update_Payment_Status.this);
        dialog.setTitle("Updating Delivery Status");
        dialog.setMessage("Please wait...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        StyleableToast.makeText(Update_Payment_Status.this, response, Toast.LENGTH_LONG, R.style.noInternet).show();

        long delayInMillis = 2500;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, delayInMillis);
    }

    //Method to receive ORDER_ID
    public String FETCH_ORDER_ID(){

        Intent getIntent = getIntent();
        String ORDER_ID = getIntent.getStringExtra("ORDER_ID");
        return ORDER_ID;
    }

    //Method to receive ORDER_COST
    public String FETCH_ORDER_COST(){

        Intent getIntent = getIntent();
        String ORDER_COST = getIntent.getStringExtra("ORDER_COST");
        return ORDER_COST;
    }

    //Method to receive PAYMENT_METHOD
    public String FETCH_PAYMENT_METHOD(){

        Intent getIntent = getIntent();
        String PAYMENT_METHOD = getIntent.getStringExtra("PAYMENT_METHOD");


        return PAYMENT_METHOD;
    }

    //Method to receive PAYMENT_STATUS
    public String FETCH_PAYMENT_STATUS(){

        Intent getIntent = getIntent();
        String PAYMENT_STATUS = getIntent.getStringExtra("PAYMENT_STATUS");
        if(PAYMENT_STATUS.equals("0")){return "Awaiting Payment";}
        else{return "Payment Received";}


    }

    //Method to receive DELIVERY_STATUS
    public String FETCH_DELIVERY_STATUS(){

        Intent getIntent = getIntent();
        String DELIVERY_STATUS = getIntent.getStringExtra("DELIVERY_STATUS");
        return DELIVERY_STATUS;
    }

    //Method to receive PLACEMENT_DATE
    public String FETCH_PLACEMENT_DATE(){

        Intent getIntent = getIntent();
        String PLACEMENT_DATE = getIntent.getStringExtra("PLACEMENT_DATE");
        return PLACEMENT_DATE;
    }

    //Method to receive DELIVERY_DATE
    public String FETCH_DELIVERY_DATE(){

        Intent getIntent = getIntent();
        String DELIVERY_DATE = getIntent.getStringExtra("DELIVERY_DATE");
        return DELIVERY_DATE;
    }

    //Method to receive DELIVERY_ADDRESS
    public String FETCH_DELIVERY_ADDRESS(){

        Intent getIntent = getIntent();
        String DELIVERY_ADDRESS = getIntent.getStringExtra("DELIVERY_ADDRESS");
        return DELIVERY_ADDRESS;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu admin_menu){
        getMenuInflater().inflate(R.menu.admin_menu, admin_menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                //onLogoutClick();
                startActivity(new Intent(Update_Payment_Status.this, MainActivity.class));
                Toast.makeText(getApplicationContext(),"Logout Successful",Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case R.id.return_to_home://create account page
                startActivity(new Intent(Update_Payment_Status.this, AdminHome.class));
                finish();
                return true;

            case R.id.View_Admin_Account:
                startActivity(new Intent(Update_Payment_Status.this, AdminAccountDetails.class));
                finish();
                return true;

        }
        return false;
    }
}