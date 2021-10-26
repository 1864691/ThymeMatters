package com.example.thymematters;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
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

public class Checkout extends AppCompatActivity {

    String TOTALPRICE_FromIntent;
    String CustID_FromIntent;
    String Customer_Registered_Address;

    //For the dates
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    Button date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        //Receive cust id and total price of order from intent
        TOTALPRICE_FromIntent = fetchTOTALPRICE();
        CustID_FromIntent = fetchCustID();

        //Upon loading this activity, set price to price from intent:
        TextView priceAtTop = (TextView)findViewById(R.id.txtActualPrice);
        priceAtTop.setText("R "+TOTALPRICE_FromIntent);

        //Set edit text for delivery address to uneditable initially:
        EditText deliverAddress = (EditText)findViewById(R.id.et_Address);
        deliverAddress.setEnabled(false);

        //Fetch Customer's Home Address and update Customer_Registered_Address to be that:
        //Send request to fetch del address from customer table:
        //Send network request to 000webhost:
        //Define URL:
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://thymematters.000webhostapp.com/ORDER/FETCH_CUSTOMER_HOME_ADDRESS.php").newBuilder();

        //If you want to add query parameters:
        urlBuilder.addQueryParameter("cust_id",CustID_FromIntent);
        //urlBuilder.addQueryParameter("password",Password);

        String url = urlBuilder.build().toString();
        //Check if network is available: https://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
        boolean networkAvailable = isNetworkAvailable();
        if(!networkAvailable){ StyleableToast.makeText(Checkout.this, "No Internet Connection", Toast.LENGTH_LONG, R.style.noInternet).show(); return;}

        //Send Request

        final ProgressDialog progressDialog = ProgressDialog.show(this, "Loading", "Please wait...");

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

                    Checkout.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Customer_Registered_Address = myResponse;
                            progressDialog.dismiss();

                        }
                    });
                }

            }
        });

        //This blcok of code is to initialise the date picker dialog
        date = (Button)findViewById(R.id.date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get rid of error if there is one
                date.setError(null);
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Checkout.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        date.setText(year + "-" + (month + 1) + "-" + day);
                    }
                }, year, month, dayOfMonth);
                //this ensures only future dates can be selected
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());


                datePickerDialog.show();
            }
        });







    }

    //Method to see if COD or EFT selected:
    public String CODorEFT(){
        String result;
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radGroupPaymentMethod);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton PMRadButt = (RadioButton) findViewById(selectedId);
        if(selectedId==-1){
            result = "Nothing selected";
        }
        else{

            result = PMRadButt.getText().toString();
        }
        return result;
    }

    //Method to see if HOME address or Other address is selected:
    public String HOMEorOTHER(){
        String result;
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radGroupDeliveryAddress);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton DARadButt = (RadioButton) findViewById(selectedId);
        if(selectedId==-1){
            result = "Nothing selected";
        }
        else{

            result = DARadButt.getText().toString();
        }
        return result;
    }

    //Method to receive CUST_ID from intents
    public String fetchCustID(){

        Intent getIntent = getIntent();
        String custID = getIntent.getStringExtra("CUST_ID");
        return custID;
    }

    //Method to receive TOTALPRICE from intents
    public String fetchTOTALPRICE(){

        Intent getIntent = getIntent();
        String totPrice = getIntent.getStringExtra("TOTALPRICE");
        return totPrice;
    }

    //Top right menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //This is the main method for this activity
    public void doPlaceOrder(View v){

        /*TEST

        String x;
        x = CODorEFT();
        Toast.makeText(this,"Payment Method: "+x,Toast.LENGTH_LONG).show();
        String y;
        y= HOMEorOTHER();
        Toast.makeText(this,"Address: "+y,Toast.LENGTH_LONG).show();
        String z;
        z = date.getText().toString();
        Toast.makeText(this,"Date: "+z,Toast.LENGTH_LONG).show();

        */

        //Place order start:

        //Error handling:
        if(!isNetworkAvailable()){ StyleableToast.makeText(Checkout.this, "No Internet Connection", Toast.LENGTH_LONG, R.style.noInternet).show(); return;}

        if(date.getText().toString().equals("SELECT DELIVERY DATE")){
            StyleableToast.makeText(Checkout.this, "Please select a delivery date", Toast.LENGTH_LONG, R.style.invalidLogin).show();
            date.setError("Please select a date for delivery");
            date.requestFocus();
            return;
        }

        String payment_method = CODorEFT();
        String del_Address = HOMEorOTHER();

        if(payment_method.equals("Nothing selected")){
            StyleableToast.makeText(Checkout.this, "Please select a payment method", Toast.LENGTH_LONG, R.style.invalidLogin).show();
            TextView lblPM = (TextView)findViewById(R.id.txtPaymentMethodLabel);
            lblPM.setError("Please select a payment method");
            lblPM.requestFocus();
            return;
        }

        if(del_Address.equals("Nothing selected")){
            StyleableToast.makeText(Checkout.this, "Please indicate the delivery address for this order", Toast.LENGTH_LONG, R.style.invalidLogin).show();
            TextView lblDA = (TextView)findViewById(R.id.txtDeliveryAddressLabel);
            lblDA.setError("Please indicate the delivery address for this order");
            lblDA.requestFocus();
            return;
        }

        //If customer selects other address, we must check that he has supplied an other address:
        EditText alternativeAddress = (EditText) findViewById(R.id.et_Address);
        if(del_Address.equals("Other") && alternativeAddress.getText().toString().equals("")){


            alternativeAddress.setError("Please indicate an alternative address for this order");
            alternativeAddress.requestFocus();
            return;
        }

        //If this point has been reached without the function returning, then all error checks have passed and we can place the order:
        //We define the insert parameters:
        String CUSTOMER_ID_TO_INSERT = CustID_FromIntent;
        String PAYMENT_METHOD_TO_INSERT;
        if(payment_method.equals("Cash on delivery")){PAYMENT_METHOD_TO_INSERT = "COD";}
        else{PAYMENT_METHOD_TO_INSERT = "EFT";}
        String ORDER_TOTAL_TO_INSERT = TOTALPRICE_FromIntent;
        String ORDER_DELIVERY_DATE_TO_INSERT = date.getText().toString();
        String ORDER_DELIVERY_ADDRESS_TO_INSERT;
        if(del_Address.equals("Home")){ORDER_DELIVERY_ADDRESS_TO_INSERT= Customer_Registered_Address;}
        else{ORDER_DELIVERY_ADDRESS_TO_INSERT = alternativeAddress.getText().toString();}

        //ABOVE WORKS:

        //Now we send request to insert order:
        //Send network request to 000webhost for insertion of new order.
        //Define URL:
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://thymematters.000webhostapp.com/ORDER/PLACE_ORDER.php").newBuilder();

        //If you want to add query parameters:
        urlBuilder.addQueryParameter("cust_id", CUSTOMER_ID_TO_INSERT);
        urlBuilder.addQueryParameter("payment_method", PAYMENT_METHOD_TO_INSERT);
        urlBuilder.addQueryParameter("order_total_cost", ORDER_TOTAL_TO_INSERT);
        urlBuilder.addQueryParameter("order_delivery_date", ORDER_DELIVERY_DATE_TO_INSERT);
        urlBuilder.addQueryParameter("order_delivery_address", ORDER_DELIVERY_ADDRESS_TO_INSERT);

        String url = urlBuilder.build().toString();

        //Check if network is available: https://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
        boolean networkAvailable = isNetworkAvailable();
        if(!networkAvailable){ StyleableToast.makeText(Checkout.this, "No Internet Connection", Toast.LENGTH_LONG, R.style.noInternet).show(); return;}

        //Send Request

        //Initialise progree bar: https://stackoverflow.com/questions/15083226/waiting-progress-bar-in-android
        //Progress Bar Functions: https://www.journaldev.com/9652/android-progressdialog-example
        final ProgressDialog progressDialog = ProgressDialog.show(this, "Placing Order", "Please wait...");


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

                    Checkout.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String order_no = myResponse;
                            progressDialog.dismiss();
                            fetchCustCurrentCartItems(order_no);

                        }
                    });
                }

            }
        });




    }

    //Top right menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                //onLogoutClick();
                startActivity(new Intent(Checkout.this, MainActivity.class));
                Toast.makeText(getApplicationContext(),"Logout Successful",Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case R.id.View_Account://create account page
                startActivity(new Intent(Checkout.this, UserAccountDetails.class));
                //finish();
                return true;

            case R.id.Order_History:
                startActivity(new Intent(Checkout.this, CartActivity.class));
                //finish();
                return true;

            case R.id.help:
                startActivity(new Intent(Checkout.this, help_page.class));
                //finish();
                return true;

            case R.id.favorites:
                startActivity(new Intent(Checkout.this, favorites.class));
                //finish();
                return true;
        }
        return false;
    }

    public void doOtherRadClicked(View v){
        EditText other_address = (EditText)findViewById(R.id.et_Address);
        other_address.setEnabled(true);
        other_address.setHint("Enter other address");
        other_address.setText("");

        //Get rid of error of no del address method selected if there is an error:
        TextView x = (TextView)findViewById(R.id.txtDeliveryAddressLabel); x.setError(null);

    }

    public void doHomeRadClicked(View v){
        EditText other_address = (EditText)findViewById(R.id.et_Address);
        other_address.setEnabled(false);
        other_address.setHint("");

        //Then set et_address to customer's home address.
        other_address.setText(Customer_Registered_Address);

        //If theres currently an error on et_address field, get rid of it
        EditText x = (EditText)findViewById(R.id.et_Address); x.setError(null);
    }

    public void doCashRadClicked(View v){
        //Get rid of error of no payment method selected if there is an error:
        TextView x = (TextView)findViewById(R.id.txtPaymentMethodLabel); x.setError(null);
    }

    public void doEftRadClicked(View v){
        //Get rid of error of no payment method selected if there is an error:
        TextView x = (TextView)findViewById(R.id.txtPaymentMethodLabel); x.setError(null);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void fetchCustCurrentCartItems(String order_no){

        //Now we send request to fetch current cart items (then for each of these items we will insert it into cart item table:
        //Send network request to 000webhost for fetching current cart items.
        //Define URL:
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://thymematters.000webhostapp.com/ORDER/FETCH_CURRENT_CART_ITEMS.php").newBuilder();

        //If you want to add query parameters:
        urlBuilder.addQueryParameter("cust_id", CustID_FromIntent);

        String url = urlBuilder.build().toString();

        //Send Request

        //Initialise progree bar: https://stackoverflow.com/questions/15083226/waiting-progress-bar-in-android
        //Progress Bar Functions: https://www.journaldev.com/9652/android-progressdialog-example
        final ProgressDialog progressDialog = ProgressDialog.show(this, "Processing Order", "Please wait...");
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

                    Checkout.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String ORDER_NO = order_no;
                            String json_current_cart_items = myResponse;
                            progressDialog.dismiss();
                            try {
                                copyCurrentCartItemsIntoCartItemTable(ORDER_NO, json_current_cart_items);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    });
                }

            }
        });

    }

    public void copyCurrentCartItemsIntoCartItemTable(String ORDER_NO, String JSON_CURRENT_CART_ITEMS) throws JSONException{
        final ProgressDialog dialog = new ProgressDialog(Checkout.this);
        dialog.setTitle("Awaiting Confirmation");
        dialog.setMessage("Please wait...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        JSONArray myJSONArray = new JSONArray(JSON_CURRENT_CART_ITEMS);
        for(int i = 0 ; i < myJSONArray.length();i++){
            JSONObject myJSONObject = myJSONArray.getJSONObject(i);
            String ITEM_TOTAL_COST = myJSONObject.getString("ITEM_TOTAL_COST");
            String SERVING_SIZE = myJSONObject.getString("SERVING_SIZE");
            String MEAL_ID = myJSONObject.getString("MEAL_ID");
            String ADDITIONAL_NOTES = myJSONObject.getString("ADDITIONAL_NOTES");

            //Define URL:
            HttpUrl.Builder urlBuilder = HttpUrl.parse("https://thymematters.000webhostapp.com/ORDER/INSERT_CURRENT_CART_ITEM_INTO_CART_ITEM_TABLE.php").newBuilder();
            //If you want to add query parameters:
            urlBuilder.addQueryParameter("order_id", ORDER_NO);
            urlBuilder.addQueryParameter("item_total_cost", ITEM_TOTAL_COST);
            urlBuilder.addQueryParameter("serving_size", SERVING_SIZE);
            urlBuilder.addQueryParameter("additional_notes", ADDITIONAL_NOTES);
            urlBuilder.addQueryParameter("meal_id", MEAL_ID);

            String url = urlBuilder.build().toString();
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

                        Checkout.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Working
                                //Now we finally delete all current cart items for this customer
                                deleteCustomersCurrentCartItems();
                            }
                        });
                    }

                }
            });

        }
        long delayInMillis = 3000;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, delayInMillis);
    }

    public void deleteCustomersCurrentCartItems(){

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://thymematters.000webhostapp.com/ORDER/DELETE_CURRENT_CART_ITEMS.php").newBuilder();

        //If you want to add query parameters:
        urlBuilder.addQueryParameter("cust_id", CustID_FromIntent);
        String url = urlBuilder.build().toString();

        final ProgressDialog progressDialog = ProgressDialog.show(this, "Almost there", "Please wait...");
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

                    Checkout.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            progressDialog.dismiss();
                            new AlertDialog.Builder(Checkout.this, R.style.AlertDialogTheme)
                                    .setTitle("Order Placed Successfully")
                                    .setMessage("Your order has been placed.\nOrder status can be tracked under order histories.")
                                    .setCancelable(false)
                                    //.setNegativeButton("NO",null)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface arg0, int arg1) {

                                            Intent backHome = new Intent(Checkout.this,HomeActivity.class);
                                            backHome.putExtra("CUST_ID",CustID_FromIntent);
                                            startActivity(backHome); finish();


                                        }
                                    }).create().show();
                        }
                    });
                }

            }
        });

    }


}