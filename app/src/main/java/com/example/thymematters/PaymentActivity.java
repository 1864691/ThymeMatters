package com.example.thymematters;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {


    Button btn_finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        btn_finish = findViewById(R.id.btn_finish);

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaymentActivity.this, Order_Confirmed.class));
                finish();
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
                startActivity(new Intent(PaymentActivity.this, MainActivity.class));
                Toast.makeText(getApplicationContext(),"Logout Successful",Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case R.id.View_Account://create account page
                startActivity(new Intent(PaymentActivity.this, UserAccountDetails.class));
                //finish();
                return true;

            case R.id.Order_History:
                startActivity(new Intent(PaymentActivity.this, CartActivity.class));
                //finish();
                return true;

            case R.id.help:
                startActivity(new Intent(PaymentActivity.this, help_page.class));
                //finish();
                return true;

            case R.id.favorites:
                startActivity(new Intent(PaymentActivity.this, favorites.class));
                //finish();
                return true;
        }
        return false;
    }
}