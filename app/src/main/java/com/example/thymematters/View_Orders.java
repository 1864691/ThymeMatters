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

//import com.muddzdev.styleabletoast.StyleableToast;

public class View_Orders extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);
        load();
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
                startActivity(new Intent(View_Orders.this, MainActivity.class));
                Toast.makeText(getApplicationContext(),"Logout Successful",Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case R.id.return_to_home://create account page
                startActivity(new Intent(View_Orders.this, AdminHome.class));
                finish();
                return true;

            case R.id.View_Admin_Account:
                startActivity(new Intent(View_Orders.this, AdminAccountDetails.class));
                finish();
                return true;

        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(View_Orders.this, AdminHome.class);
        finish();
        startActivity(i);
    }

    public void load(){
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://thymematters.000webhostapp.com/LoadPaymentOrders.php").newBuilder();

        String url = urlBuilder.build().toString();
        boolean networkAvailable = isNetworkAvailable();
        if(!networkAvailable){ StyleableToast.makeText(View_Orders.this, "No Internet Connection", Toast.LENGTH_LONG, R.style.noInternet).show(); return;}

        //Send Request
        final ProgressDialog progressDialog = ProgressDialog.show(this, "Fetching Orders", "Please wait...");

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

                    View_Orders.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            //Process Response Here:
                            try{
                                JSON_orders_output(myResponse);
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void JSON_orders_output (String json_string) throws JSONException {
       /* final ProgressDialog dialog = new ProgressDialog(View_Orders.this);
        dialog.setTitle("Fetching Orders");
        dialog.setMessage("Please wait...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();*/
        LinearLayout main_layout = (LinearLayout)findViewById(R.id.ll_mainbox) ;



        //This method creates list items for all orders:
        JSONArray myJSONArray = new JSONArray(json_string);
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
                    //Payment status change confirmation activity
                    Intent goConfirmPaymentUpdate = new Intent(View_Orders.this,Update_Payment_Status.class);
                    //Pass data to Add Item to Cart page:
                    goConfirmPaymentUpdate.putExtra("ORDER_ID",ORDER_ID);
                    goConfirmPaymentUpdate.putExtra("ORDER_COST",ORDER_COST);
                    goConfirmPaymentUpdate.putExtra("PAYMENT_METHOD",PAYMENT_METHOD);
                    goConfirmPaymentUpdate.putExtra("PAYMENT_STATUS",payment_status_for_next_activity);
                    goConfirmPaymentUpdate.putExtra("DELIVERY_STATUS",DELIVERY_STATUS);
                    goConfirmPaymentUpdate.putExtra("PLACEMENT_DATE",PLACEMENT_DATE);
                    goConfirmPaymentUpdate.putExtra("DELIVERY_DATE",DELIVERY_DATE);
                    goConfirmPaymentUpdate.putExtra("DELIVERY_ADDRESS",DELIVERY_ADDRESS);
                    startActivity(goConfirmPaymentUpdate);
                }
            });

            CustOrderHistoryLayout order = new CustOrderHistoryLayout(this,arrow,ORDER_ID,ORDER_COST,PAYMENT_METHOD,
                    PAYMENT_STATUS,DELIVERY_STATUS,PLACEMENT_DATE,DELIVERY_DATE,DELIVERY_ADDRESS);

           main_layout.addView(order);

        }

        long delayInMillis = 4000;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //dialog.dismiss();
            }
        }, delayInMillis);

    }
}