package com.example.thymematters;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    EditText fname;
    EditText lname;
    EditText del_add;
    EditText email;
    EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_details);

        //Initiate edittexts:
        fname = findViewById(R.id.edit_text_FNAME);
        lname = findViewById(R.id.edit_text_LNAME);
        del_add = findViewById(R.id.edit_text_DELIVERY_ADDRESS);
        email = findViewById(R.id.edit_text_EMAIL_ADDRESS);
        phone = findViewById(R.id.edit_text_PHONE);


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
                Intent user_acc = new Intent(UserAccountDetails.this,UserAccountDetails.class);

                user_acc.putExtra("CUST_ID",CustID_FromIntent);

                startActivity(user_acc); finish();
                return true;

            case R.id.Order_History:
                Intent orderHist = new Intent(UserAccountDetails.this,CustomerViewOrderHistory.class);

                orderHist.putExtra("CUST_ID",CustID_FromIntent);

                startActivity(orderHist); finish();
                return true;

            case R.id.help:
                startActivity(new Intent(UserAccountDetails.this, help_page.class));
                finish();
                return true;

            case R.id.favorites:
                Intent fav = new Intent(UserAccountDetails.this,favorites.class);

                fav.putExtra("CUST_ID",CustID_FromIntent);

                startActivity(fav); finish();
                return true;

            case R.id.cart:
                Intent cart = new Intent(UserAccountDetails.this,CartActivity.class);

                cart.putExtra("CUST_ID",CustID_FromIntent);

                startActivity(cart); finish();
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

    public void update(View v){
        String FNAME = fname.getText().toString();
        String LNAME = lname.getText().toString();
        String EMAIL = email.getText().toString();
        String DELIVERY_ADDRESS = del_add.getText().toString();
        String PHONE = phone.getText().toString();

        //Validations
        if (TextUtils.isEmpty(FNAME)) {
            fname.setError("Please enter first name");
            fname.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(LNAME)) {
            lname.setError("Please enter your last name");
            lname.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(EMAIL).matches()) {
            email.setError("Enter a valid email");
            email.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(DELIVERY_ADDRESS)) {
            del_add.setError("Please enter your delivery address");
            del_add.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(PHONE)) {
            phone.setError("Please enter your contact number");
            phone.requestFocus();
            return;
        }

        //If all passed, do an update, ensuring the desired email is available:
        //Define URL:
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://thymematters.000webhostapp.com/UPDATE_CUSTOMER_DETAILS/tmp_update_cust_details.php").newBuilder();
        urlBuilder.addQueryParameter("cust_id",CustID_FromIntent);
        urlBuilder.addQueryParameter("fname",FNAME);
        urlBuilder.addQueryParameter("lname",LNAME);
        urlBuilder.addQueryParameter("del_add",DELIVERY_ADDRESS);
        urlBuilder.addQueryParameter("email",EMAIL);
        urlBuilder.addQueryParameter("phone",PHONE);

        String url = urlBuilder.build().toString();
        //Check if network is available: https://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
        boolean networkAvailable = isNetworkAvailable();
        if(!networkAvailable){ StyleableToast.makeText(UserAccountDetails.this, "No Internet Connection", Toast.LENGTH_LONG, R.style.noInternet).show(); return;}

        //Send Request
        final ProgressDialog progressDialog = ProgressDialog.show(this, "Updating Details", "Please wait...");
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
                            //Toast.makeText(UserAccountDetails.this,myResponse,Toast.LENGTH_LONG).show();

                            if(myResponse.equals("update_success")){
                                StyleableToast.makeText(UserAccountDetails.this, "Details Updated", Toast.LENGTH_LONG, R.style.success).show();
                                new AlertDialog.Builder(UserAccountDetails.this,R.style.AlertDialogTheme)
                                        .setTitle("Notice")
                                        .setMessage("Your personal details have been successfully updated")
                                        //.setNegativeButton("NO",null)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface arg0, int arg1) {

                                                Intent home_screen = new Intent(UserAccountDetails.this,HomeActivity.class);

                                                home_screen.putExtra("CUST_ID",CustID_FromIntent);

                                                startActivity(home_screen); finish();
                                            }
                                        }).create().show();
                            }
                            else if(myResponse.equals("email_not_available")){
                                StyleableToast.makeText(UserAccountDetails.this, "Email not available", Toast.LENGTH_LONG, R.style.invalidLogin).show();
                                email.setError("This email is already in use");
                                email.requestFocus();
                            }
                            else{
                                StyleableToast.makeText(UserAccountDetails.this, "SOMETHING WENT WRONG", Toast.LENGTH_LONG, R.style.invalidLogin).show();
                            }

                        }
                    });
                }

            }
        });
    }

    public void cancel(View v){
        Intent home_screen = new Intent(UserAccountDetails.this,HomeActivity.class);

        home_screen.putExtra("CUST_ID",CustID_FromIntent);

        startActivity(home_screen); finish();
    }
}