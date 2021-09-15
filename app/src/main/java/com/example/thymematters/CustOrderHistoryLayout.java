package com.example.thymematters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustOrderHistoryLayout extends LinearLayout {

    //Labels
    TextView lblOrderID;
    TextView lblPlacementDate;
    TextView lblDeliveryStatus;
    TextView lblPayment_Status;
    TextView lblPayment_Method;
    TextView lblOrderTotal;
    TextView lblOrderDeliveryDate;
    TextView lblOrderDeliveryAddress;

    //Actual values:
    TextView ACTUAL_ORDER_ID;
    TextView ACTUAL_PLACEMENT_DATE;
    TextView ACTUAL_DELIVERY_STATUS;
    TextView ACTUAL_PAYMENT_STATUS;
    TextView ACTUAL_PAYMENT_METHOD;
    TextView ACTUAL_ORDER_TOTAL;
    TextView ACTUAL_DELIVERY_DATE;
    TextView ACTUAL_DELIVERY_ADDRESS;


    public CustOrderHistoryLayout(Context context, ImageView arrow, String ORDER_ID, String ORDER_COST, String PAYMENT_METHOD,
                                  String PAYMENT_STATUS, String DELIVERY_STATUS,String PLACEMENT_DATE, String DELIVERY_DATE, String DELIVERY_ADDRESS) {
        super(context);

        //Outermost layout
        this.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams main_layout = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        main_layout.setMargins(0, dpToPx(7,context), 0, 0);
        this.setBackgroundResource(R.color.white);
        this.setLayoutParams(main_layout);


        //Add main left vertical
        LinearLayout main_left = new LinearLayout(context);
        main_left.setOrientation(LinearLayout.VERTICAL);
        LayoutParams main_left_params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        main_left_params.weight=1;
        main_left.setLayoutParams(main_left_params);
        main_left.setBackgroundResource(R.color.cart_item_background);

        //Add main right
        LinearLayout main_right = new LinearLayout(context);
        main_right.setOrientation(LinearLayout.VERTICAL);
        LayoutParams main_right_params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        main_right_params.gravity= Gravity.CENTER_HORIZONTAL;
        main_right_params.weight=3;
        main_right.setLayoutParams(main_right_params);
        main_right.setBackgroundResource(R.color.cart_item_background);

        //Populate main_right
        LayoutParams arrow_params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        arrow_params.weight = 1;
        arrow_params.gravity = Gravity.CENTER_HORIZONTAL;
        arrow.setLayoutParams(arrow_params);
        main_right.addView(arrow);

        //Populate the main left:

        //1. We do the order ID:
        //Create horizontal ll and add it to main left
        LinearLayout order_id_LL = new LinearLayout(context);
        order_id_LL.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams order_id_LL_params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        order_id_LL_params.setMargins(dpToPx(5,context),dpToPx(5,context),dpToPx(5,context),0);
        order_id_LL.setLayoutParams(order_id_LL_params);

        //Add label for order id
        lblOrderID = new TextView(context);
        lblOrderID.setText("Order ID: ");
        LayoutParams labelOrderID_params = new LayoutParams(dpToPx(20,context), ViewGroup.LayoutParams.WRAP_CONTENT);
        lblOrderID.setTextSize(17);
        lblOrderID.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        labelOrderID_params.weight = 1;
        lblOrderID.setLayoutParams(labelOrderID_params);
        order_id_LL.addView(lblOrderID);

        //Actual order_id:
        ACTUAL_ORDER_ID = new TextView(context); ACTUAL_ORDER_ID.setText(ORDER_ID);
        LayoutParams ACTUAL_ORDER_ID_params = new LayoutParams(dpToPx(20,context), ViewGroup.LayoutParams.WRAP_CONTENT);
        ACTUAL_ORDER_ID_params.weight = 1;
        ACTUAL_ORDER_ID.setTextSize(17);
        ACTUAL_ORDER_ID.setTypeface(Typeface.SANS_SERIF,Typeface.BOLD);
        ACTUAL_ORDER_ID.setLayoutParams(ACTUAL_ORDER_ID_params);
        order_id_LL.addView(ACTUAL_ORDER_ID);

        main_left.addView(order_id_LL);

        //2. We do the order total cost:
        //Create horizontal ll and add it to main left
        LinearLayout order_cost_LL = new LinearLayout(context);
        order_cost_LL.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams order_cost_LL_params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        order_cost_LL_params.setMargins(dpToPx(5,context),dpToPx(3,context),dpToPx(5,context),0);
        order_cost_LL.setLayoutParams(order_cost_LL_params);

        //Add label for total cost
        lblOrderTotal = new TextView(context);
        lblOrderTotal.setText("Order Cost: ");
        LayoutParams lblOrderTotal_params = new LayoutParams(dpToPx(20,context), ViewGroup.LayoutParams.WRAP_CONTENT);
        lblOrderTotal.setTextSize(13);
        lblOrderTotal.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        lblOrderTotal_params.weight = 1;
        lblOrderTotal.setLayoutParams(lblOrderTotal_params);
        order_cost_LL.addView(lblOrderTotal);

        //Actual total cost:
        ACTUAL_ORDER_TOTAL = new TextView(context); ACTUAL_ORDER_TOTAL.setText("R "+ORDER_COST);
        LayoutParams ACTUAL_ORDER_TOTAL_params = new LayoutParams(dpToPx(20,context), ViewGroup.LayoutParams.WRAP_CONTENT);
        ACTUAL_ORDER_TOTAL_params.weight = 1;
        ACTUAL_ORDER_TOTAL.setTextSize(13);
        ACTUAL_ORDER_TOTAL.setTypeface(Typeface.SANS_SERIF);
        ACTUAL_ORDER_TOTAL.setLayoutParams(ACTUAL_ORDER_TOTAL_params);
        order_cost_LL.addView(ACTUAL_ORDER_TOTAL);

        main_left.addView(order_cost_LL);

        //3. We do the order payment_method:
        //Create horizontal ll and add it to main left
        LinearLayout payment_method_LL = new LinearLayout(context);
        payment_method_LL.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams payment_method_LL_params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        payment_method_LL_params.setMargins(dpToPx(5,context),dpToPx(3,context),dpToPx(5,context),0);
        payment_method_LL.setLayoutParams(payment_method_LL_params);

        //Add label for paymentMethod
        lblPayment_Method = new TextView(context);
        lblPayment_Method.setText("Payment Method: ");
        LayoutParams lblPayment_Method_params = new LayoutParams(dpToPx(20,context), ViewGroup.LayoutParams.WRAP_CONTENT);
        lblPayment_Method.setTextSize(13);
        lblPayment_Method.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        lblPayment_Method_params.weight = 1;
        lblPayment_Method.setLayoutParams(lblPayment_Method_params);
        payment_method_LL.addView(lblPayment_Method);

        //Actual payment method:
        ACTUAL_PAYMENT_METHOD = new TextView(context); ACTUAL_PAYMENT_METHOD.setText(PAYMENT_METHOD);
        LayoutParams ACTUAL_PAYMENT_METHOD_params = new LayoutParams(dpToPx(20,context), ViewGroup.LayoutParams.WRAP_CONTENT);
        ACTUAL_PAYMENT_METHOD_params.weight = 1;
        ACTUAL_PAYMENT_METHOD.setTextSize(13);
        ACTUAL_PAYMENT_METHOD.setTypeface(Typeface.SANS_SERIF);
        ACTUAL_PAYMENT_METHOD.setLayoutParams(ACTUAL_PAYMENT_METHOD_params);
        payment_method_LL.addView(ACTUAL_PAYMENT_METHOD);

        main_left.addView(payment_method_LL);

        //4. We do the order payment_status:
        //Create horizontal ll and add it to main left
        LinearLayout payment_status_LL = new LinearLayout(context);
        payment_status_LL.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams payment_status_LL_params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        payment_status_LL_params.setMargins(dpToPx(5,context),dpToPx(3,context),dpToPx(5,context),0);
        payment_status_LL.setLayoutParams(payment_status_LL_params);

        //Add label for paymentStatus
        lblPayment_Status = new TextView(context);
        lblPayment_Status.setText("Payment Status: ");
        LayoutParams lblPayment_Status_params = new LayoutParams(dpToPx(20,context), ViewGroup.LayoutParams.WRAP_CONTENT);
        lblPayment_Status.setTextSize(13);
        lblPayment_Status.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        lblPayment_Status_params.weight = 1;
        lblPayment_Status.setLayoutParams(lblPayment_Status_params);
        payment_status_LL.addView(lblPayment_Status);

        //Actual payment status:
        ACTUAL_PAYMENT_STATUS = new TextView(context); ACTUAL_PAYMENT_STATUS.setText(PAYMENT_STATUS);
        LayoutParams ACTUAL_PAYMENT_STATUS_params = new LayoutParams(dpToPx(20,context), ViewGroup.LayoutParams.WRAP_CONTENT);
        ACTUAL_PAYMENT_STATUS_params.weight = 1;
        ACTUAL_PAYMENT_STATUS.setTextSize(13);
        ACTUAL_PAYMENT_STATUS.setTypeface(Typeface.SANS_SERIF);
        ACTUAL_PAYMENT_STATUS.setLayoutParams(ACTUAL_PAYMENT_STATUS_params);
        payment_status_LL.addView(ACTUAL_PAYMENT_STATUS);

        main_left.addView(payment_status_LL);

        //5. We do deliveryStatus:
        //Create horizontal ll and add it to main left
        LinearLayout delivery_status_LL = new LinearLayout(context);
        delivery_status_LL.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams delivery_status_LL_params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        delivery_status_LL_params.setMargins(dpToPx(5,context),dpToPx(3,context),dpToPx(5,context),0);
        delivery_status_LL.setLayoutParams(delivery_status_LL_params);

        //Add label for lblDeliveryStatus
        lblDeliveryStatus = new TextView(context);
        lblDeliveryStatus.setText("Delivery Status: ");
        LayoutParams lblDeliveryStatus_params = new LayoutParams(dpToPx(20,context), ViewGroup.LayoutParams.WRAP_CONTENT);
        lblDeliveryStatus.setTextSize(13);
        lblDeliveryStatus.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        lblDeliveryStatus_params.weight = 1;
        lblDeliveryStatus.setLayoutParams(lblDeliveryStatus_params);
        delivery_status_LL.addView(lblDeliveryStatus);

        //Actual delivery status:
        ACTUAL_DELIVERY_STATUS = new TextView(context); ACTUAL_DELIVERY_STATUS.setText(DELIVERY_STATUS);
        LayoutParams ACTUAL_DELIVERY_STATUS_params = new LayoutParams(dpToPx(20,context), ViewGroup.LayoutParams.WRAP_CONTENT);
        ACTUAL_DELIVERY_STATUS_params.weight = 1;
        ACTUAL_DELIVERY_STATUS.setTextSize(13);
        ACTUAL_DELIVERY_STATUS.setTypeface(Typeface.SANS_SERIF);
        ACTUAL_DELIVERY_STATUS.setLayoutParams(ACTUAL_DELIVERY_STATUS_params);
        delivery_status_LL.addView(ACTUAL_DELIVERY_STATUS);

        main_left.addView(delivery_status_LL);

        //6. We do placement date:
        //Create horizontal ll and add it to main left
        LinearLayout PlacementDate_LL = new LinearLayout(context);
        PlacementDate_LL.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams PlacementDate_LL_params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        PlacementDate_LL_params.setMargins(dpToPx(5,context),dpToPx(3,context),dpToPx(5,context),0);
        PlacementDate_LL.setLayoutParams(PlacementDate_LL_params);

        //Add label for lblDeliveryStatus
        lblPlacementDate = new TextView(context);
        lblPlacementDate.setText("Placed on: ");
        LayoutParams lblPlacementDate_params = new LayoutParams(dpToPx(20,context), ViewGroup.LayoutParams.WRAP_CONTENT);
        lblPlacementDate.setTextSize(13);
        lblPlacementDate.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        lblPlacementDate_params.weight = 1;
        lblPlacementDate.setLayoutParams(lblPlacementDate_params);
        PlacementDate_LL.addView(lblPlacementDate);

        //Actual delivery status:
        ACTUAL_PLACEMENT_DATE = new TextView(context); ACTUAL_PLACEMENT_DATE.setText(PLACEMENT_DATE);
        LayoutParams ACTUAL_PLACEMENT_DATE_params = new LayoutParams(dpToPx(20,context), ViewGroup.LayoutParams.WRAP_CONTENT);
        ACTUAL_PLACEMENT_DATE_params.weight = 1;
        ACTUAL_PLACEMENT_DATE.setTextSize(13);
        ACTUAL_PLACEMENT_DATE.setTypeface(Typeface.SANS_SERIF);
        ACTUAL_PLACEMENT_DATE.setLayoutParams(ACTUAL_PLACEMENT_DATE_params);
        PlacementDate_LL.addView(ACTUAL_PLACEMENT_DATE);

        main_left.addView(PlacementDate_LL);

         //7. We do placement date:
        //Create horizontal ll and add it to main left
        LinearLayout OrderDeliveryDate_LL = new LinearLayout(context);
        OrderDeliveryDate_LL.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams OrderDeliveryDate_LL_params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        OrderDeliveryDate_LL_params.setMargins(dpToPx(5,context),dpToPx(3,context),dpToPx(5,context),0);
        OrderDeliveryDate_LL.setLayoutParams(OrderDeliveryDate_LL_params);

        //Add label for lblDeliveryStatus
        lblOrderDeliveryDate = new TextView(context);
        lblOrderDeliveryDate.setText("Delivery Date: ");
        LayoutParams lblOrderDeliveryDate_params = new LayoutParams(dpToPx(20,context), ViewGroup.LayoutParams.WRAP_CONTENT);
        lblOrderDeliveryDate.setTextSize(13);
        lblOrderDeliveryDate.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        lblOrderDeliveryDate_params.weight = 1;
        lblOrderDeliveryDate.setLayoutParams(lblOrderDeliveryDate_params);
        OrderDeliveryDate_LL.addView(lblOrderDeliveryDate);

        //Actual delivery status:
        ACTUAL_DELIVERY_DATE = new TextView(context); ACTUAL_DELIVERY_DATE.setText(DELIVERY_DATE);
        LayoutParams ACTUAL_DELIVERY_DATE_params = new LayoutParams(dpToPx(20,context), ViewGroup.LayoutParams.WRAP_CONTENT);
        ACTUAL_DELIVERY_DATE_params.weight = 1;
        ACTUAL_DELIVERY_DATE.setTextSize(13);
        ACTUAL_DELIVERY_DATE.setTypeface(Typeface.SANS_SERIF);
        ACTUAL_DELIVERY_DATE.setLayoutParams(ACTUAL_DELIVERY_DATE_params);
        OrderDeliveryDate_LL.addView(ACTUAL_DELIVERY_DATE);

        main_left.addView(OrderDeliveryDate_LL);

        //8. We do OrderDeliveryAddress:
        //Create horizontal ll and add it to main left
        LinearLayout OrderDeliveryAddress_LL = new LinearLayout(context);
        OrderDeliveryAddress_LL.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams OrderDeliveryAddress_LL_params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        OrderDeliveryAddress_LL_params.setMargins(dpToPx(5,context),dpToPx(3,context),dpToPx(5,context),dpToPx(5,context));
        OrderDeliveryAddress_LL.setLayoutParams(OrderDeliveryAddress_LL_params);

        //Add label for lblDeliveryStatus
        lblOrderDeliveryAddress = new TextView(context);
        lblOrderDeliveryAddress.setText("Delivery Address: ");
        LayoutParams lblOrderDeliveryAddress_params = new LayoutParams(dpToPx(20,context), ViewGroup.LayoutParams.WRAP_CONTENT);
        lblOrderDeliveryAddress.setTextSize(13);
        lblOrderDeliveryAddress.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        lblOrderDeliveryAddress_params.weight = 1;
        lblOrderDeliveryAddress.setLayoutParams(lblOrderDeliveryAddress_params);
        OrderDeliveryAddress_LL.addView(lblOrderDeliveryAddress);

        //Actual delivery status:
        ACTUAL_DELIVERY_ADDRESS = new TextView(context); ACTUAL_DELIVERY_ADDRESS.setText(DELIVERY_ADDRESS);
        LayoutParams ACTUAL_DELIVERY_ADDRESS_params = new LayoutParams(dpToPx(20,context), ViewGroup.LayoutParams.WRAP_CONTENT);
        ACTUAL_DELIVERY_ADDRESS_params.weight = 1;
        ACTUAL_DELIVERY_ADDRESS.setTextSize(13);
        ACTUAL_DELIVERY_ADDRESS.setTypeface(Typeface.SANS_SERIF);
        ACTUAL_DELIVERY_ADDRESS.setLayoutParams(ACTUAL_DELIVERY_ADDRESS_params);
        OrderDeliveryAddress_LL.addView(ACTUAL_DELIVERY_ADDRESS);

        main_left.addView(OrderDeliveryAddress_LL);





        //Add above two layouts to this:
        this.addView(main_left); this.addView(main_right);
    }

    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
