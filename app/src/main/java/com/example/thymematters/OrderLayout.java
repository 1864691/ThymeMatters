package com.example.thymematters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OrderLayout extends LinearLayout {

    TextView tOrder_ID;
    TextView tDatePlaced;
    TextView tPayment_Status;
    TextView tDelivery_Status;


    //Labels:
    TextView lblOrderID;
    TextView lblDatePlaced;
    TextView lblPaymentStatus;
    TextView lblDeliveryStatus;


    public OrderLayout(Context p, String Order_ID, String Payment_Status, String Delivery_Status, String Date_Placed) {
        super(p);
        setOrientation(LinearLayout.VERTICAL);

        //one field
        LinearLayout field = new LinearLayout(p);
        field.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        field.setLayoutParams(lp);
        lp.setMargins(dpToPx(15,p),0,0,0);

        lblOrderID = new TextView(p);
        lblOrderID.setText("Order Number: ");
        lblOrderID.setTextSize(15);
        lblOrderID.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);

        tOrder_ID = new TextView(p);
        tOrder_ID.setText(Order_ID);
        tOrder_ID.setTextSize(15);

        field.addView(lblOrderID);
        field.addView(tOrder_ID);

        addView(field);

        LinearLayout field2 = new LinearLayout(p);
        field2.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams lp2 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        field2.setLayoutParams(lp2);
        lp2.setMargins(dpToPx(15,p),0,0,0);

        lblDatePlaced = new TextView(p);
        lblDatePlaced.setText("Date Placed: ");
        lblDatePlaced.setTextSize(15);
        lblDatePlaced.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);

        tDatePlaced = new TextView(p);
        tDatePlaced.setText(Date_Placed);
        tDatePlaced.setTextSize(15);

        field2.addView(lblDatePlaced);
        field2.addView(tDatePlaced);

        addView(field2);

        LinearLayout field3 = new LinearLayout(p);
        field3.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams lp3 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        field3.setLayoutParams(lp3);
        lp3.setMargins(dpToPx(15,p),0,0,0);

        lblPaymentStatus = new TextView(p);
        lblPaymentStatus.setText("Payment Status: ");
        lblPaymentStatus.setTextSize(15);
        lblPaymentStatus.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);

        tPayment_Status = new TextView(p);
        tPayment_Status.setTextSize(15);
        if (Payment_Status.equals("0")) {
            tPayment_Status.setText("Awaiting Payment");
        }else{
            if(Payment_Status.equals("1")){
                tPayment_Status.setText("Paid");
            }else{
                tPayment_Status.setText(Payment_Status);
            }
        }

        field3.addView(lblPaymentStatus);
        field3.addView(tPayment_Status);

        addView(field3);

        LinearLayout field4 = new LinearLayout(p);
        field4.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams lp4 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        field4.setLayoutParams(lp4);
        lp4.setMargins(dpToPx(15,p),0,0,0);

        lblDeliveryStatus = new TextView(p);
        lblDeliveryStatus.setText("Delivery Status: ");
        lblDeliveryStatus.setTextSize(15);
        lblDeliveryStatus.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);

        tDelivery_Status = new TextView(p);
        tDelivery_Status.setText(Delivery_Status);
        tDelivery_Status.setTextSize(15);

        field4.addView(lblDeliveryStatus);
        field4.addView(tDelivery_Status);

        addView(field4);

    }

    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }


}
