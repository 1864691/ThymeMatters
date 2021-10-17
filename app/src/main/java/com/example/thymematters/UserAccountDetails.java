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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class UserAccountDetails extends AppCompatActivity {

    String CustID_FromIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_details);

        //Initially retrieve customer unique id from intent:
        CustID_FromIntent = fetchCustID();
        Toast.makeText(this,CustID_FromIntent, Toast.LENGTH_LONG).show();

        //load edit texts with current info
        loadEditTexts();
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
                startActivity(new Intent(UserAccountDetails.this, MainActivity.class));
                Toast.makeText(getApplicationContext(),"Logout Successful",Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case R.id.View_Account://create account page
                startActivity(new Intent(UserAccountDetails.this, UserAccountDetails.class));
                finish();
                return true;

            case R.id.Order_History:
                startActivity(new Intent(UserAccountDetails.this, CartActivity.class));
                finish();
                return true;

            case R.id.help:
                startActivity(new Intent(UserAccountDetails.this, help_page.class));
                finish();
                return true;

            case R.id.favorites:
                startActivity(new Intent(UserAccountDetails.this, favorites.class));
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

    public void loadEditTexts(){
        //Define URL:
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://thymematters.000webhostapp.com/UPDATE_CUSTOMER_DETAILS/tmp_get_cust_details.php").newBuilder();
        urlBuilder.addQueryParameter("cust_id",CustID_FromIntent);

        String url = urlBuilder.build().toString();
        //Check if network is available: https://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
        boolean networkAvailable = isNetworkAvailable();
        if(!networkAvailable){ StyleableToast.makeText(UserAccountDetails.this, "No Internet Connection", Toast.LENGTH_LONG, R.style.noInternet).show(); return;}

        //Send Request
        final ProgressDialog progressDialog = ProgressDialog.show(this, "Loading Personal Information", "Please wait...");
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

                    UserAccountDetails.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            try {
                                setEditTextValues(myResponse);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(UserAccountDetails.this,"Exception", Toast.LENGTH_LONG).show();
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

    public void setEditTextValues(String jsonString) throws JSONException {
        //JSONArray myJSONArray = new JSONArray(jsonString);
        JSONObject obj = new JSONObject(jsonString);


            String CUSTOMER_FNAME = obj.getString("CUSTOMER_FNAME");

            String CUSTOMER_LNAME = obj.getString("CUSTOMER_LNAME");
            String CUSTOMER_DELIVERY_ADDRESS = obj.getString("CUSTOMER_DELIVERY_ADDRESS");
            String CUSTOMER_EMAIL_ADDRESS = obj.getString("CUSTOMER_EMAIL_ADDRESS");
            String CUSTOMER_CONTACT_NO = obj.getString("CUSTOMER_CONTACT_NO");

            //SET EDIT TEXTS:
            EditText custfname = findViewById(R.id.edit_text_FNAME); custfname.setText(CUSTOMER_FNAME);
            EditText custLname = (findViewById(R.id.edit_text_LNAME)); custLname.setText(CUSTOMER_LNAME);
            EditText DELADDRESS = (findViewById(R.id.edit_text_DELIVERY_ADDRESS)); DELADDRESS.setText(CUSTOMER_DELIVERY_ADDRESS);
            EditText EMAIL = (findViewById(R.id.edit_text_EMAIL_ADDRESS)); EMAIL.setText(CUSTOMER_EMAIL_ADDRESS);
            EditText CONTACT = (findViewById(R.id.edit_text_PHONE)); CONTACT.setText(CUSTOMER_CONTACT_NO);






    }

    public void update(){

    }
}