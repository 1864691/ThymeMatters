package com.example.thymematters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Order_Confirmed extends AppCompatActivity {

    TextView track_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmed);

//        track_order.findViewById(R.id.track_order);

      /*  track_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Order_Confirmed.this, Order_Confirmed.class));
                finish();
            }
        });*/
    }
}