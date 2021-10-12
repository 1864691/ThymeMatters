package com.example.thymematters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CartItemCustomView extends LinearLayout {

    TextView ITEM_TOTAL_COST;
    TextView SERVING_SIZE;
    TextView ADDITIONAL_NOTES;
    TextView MEAL_NAME;


    //Labels:
    TextView labelSelectedMeal;
    TextView labelAdditionalNotes;
    TextView labelServingSize;
    TextView labelItemPrice;

    public CartItemCustomView(Context p, ImageView meal_pic, String meal_name, String Add_Notes, String Provided_Serving_Size, String Provided_Price, ImageView delete_bin) {
        super(p);

        //Outermost layout
        this.setOrientation(LinearLayout.VERTICAL);
        LayoutParams main_layout = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(main_layout);
        //main_layout.setMargins(left, top, right, bottom);
        main_layout.setMargins(0, dpToPx(7,p), 0, 0);

        //Create main horizontal layout: (ie. The one with the 3 boxes inside it.
        LinearLayout main_horizontal = new LinearLayout(p);
        LayoutParams main_horizontal_params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(190,p));
        main_horizontal.setLayoutParams(main_horizontal_params);
        main_horizontal.setOrientation(LinearLayout.HORIZONTAL);
        //main_horizontal.setBackgroundResource(R.color.white);
        main_horizontal.setBackgroundResource(R.color.white);

        //Create main left:
        LinearLayout main_left = new LinearLayout(p);
        LayoutParams main_left_params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        main_left_params.weight=1;
        main_left.setLayoutParams(main_left_params);
        main_left.setOrientation(LinearLayout.VERTICAL);
        main_left.setBackgroundResource(R.color.cart_item_background);

        //Create main middle:
        LinearLayout main_middle = new LinearLayout(p);
        LayoutParams main_middle_params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        main_middle_params.weight = 0.7f;
        main_middle.setLayoutParams(main_middle_params);
        main_middle.setOrientation(LinearLayout.VERTICAL);
        main_middle.setBackgroundResource(R.color.cart_item_background);

        //Create main right:
        LinearLayout main_right = new LinearLayout(p);
        LayoutParams main_right_params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        main_right_params.weight = 1;
        main_right_params.gravity= Gravity.CENTER_HORIZONTAL;
        main_right.setLayoutParams(main_right_params);
        main_right.setOrientation(LinearLayout.VERTICAL);
        main_right.setBackgroundResource(R.color.cart_item_background);

        //We now have the main 3 containers and they must now be populated.

        //Populate main_left
        LayoutParams meal_pic_params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        meal_pic_params.setMargins(dpToPx(5,p), dpToPx(5,p), dpToPx(5,p), dpToPx(5,p));
        meal_pic.setLayoutParams(meal_pic_params);
        main_left.addView(meal_pic);

        //Populate main_middle

        //Selected Meal Label:
        labelSelectedMeal = new TextView(p);
        labelSelectedMeal.setText("Selected Meal:");
        LayoutParams labelSelectedMeal_params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        labelSelectedMeal_params.setMargins(dpToPx(5,p), dpToPx(5,p), dpToPx(5,p), 0);
        labelSelectedMeal.setTextSize(15);
        labelSelectedMeal.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        labelSelectedMeal.setLayoutParams(labelSelectedMeal_params);
        main_middle.addView(labelSelectedMeal);

        //Actual Meal name:
        MEAL_NAME = new TextView(p); MEAL_NAME.setText(meal_name);
        LayoutParams actualMealName_params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        actualMealName_params.setMargins(dpToPx(5,p),0,dpToPx(5,p),dpToPx(5,p));
        MEAL_NAME.setTextSize(15);
        MEAL_NAME.setTypeface(Typeface.SANS_SERIF);
        MEAL_NAME.setLayoutParams(actualMealName_params);
        main_middle.addView(MEAL_NAME);

        //LabelAddNotes and ActualAddNotes are both in a linear layout, we will add this linear layout to main middle:
        LinearLayout addNotesLL = new LinearLayout(p);
        LayoutParams addNotesLL_params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addNotesLL_params.setMargins(dpToPx(5,p),0,dpToPx(5,p),dpToPx(5,p));
        addNotesLL.setOrientation(LinearLayout.VERTICAL);
        addNotesLL.setLayoutParams(addNotesLL_params);

        //Add labelAdd notes to above LL:
        labelAdditionalNotes = new TextView(p);
        labelAdditionalNotes.setText("Additional Notes:");
        LayoutParams labelAdditionalNotes_params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        labelAdditionalNotes.setTextSize(15);
        labelAdditionalNotes.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        labelAdditionalNotes_params.weight = 1;
        labelAdditionalNotes.setLayoutParams(labelAdditionalNotes_params);
        addNotesLL.addView(labelAdditionalNotes);

        //Add actualAdditional notes to above LL:
        ADDITIONAL_NOTES = new TextView(p); ADDITIONAL_NOTES.setText(Add_Notes);
        LayoutParams ActualAdditionalNotes_params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ActualAdditionalNotes_params.weight = 1;
        ADDITIONAL_NOTES.setTextSize(15);
        ADDITIONAL_NOTES.setTypeface(Typeface.SANS_SERIF);
        addNotesLL.addView(ADDITIONAL_NOTES);

        //Now add addNotesLL to main_middle:
        main_middle.addView(addNotesLL);

        //LabelServingSize and ActualServingSize are both in a linear layout, we will add this linear layout to main middle:
        LinearLayout servingSizeLL = new LinearLayout(p);
        LayoutParams servingSizeLL_params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        servingSizeLL.setOrientation(LinearLayout.HORIZONTAL);
        servingSizeLL_params.setMargins(dpToPx(5,p),0,dpToPx(5,p),0);
        servingSizeLL.setLayoutParams(servingSizeLL_params);

        //Add labelServingSize notes to above servingSizeLL:
        labelServingSize = new TextView(p); labelServingSize.setText("Serving Size:");
        LayoutParams labelServingSize_params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        labelServingSize.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        labelServingSize.setTextSize(15);
        labelServingSize_params.weight = 1;
        labelServingSize.setLayoutParams(labelServingSize_params);
        servingSizeLL.addView(labelServingSize);

        //Add actualServingSize to above servingSizeLL:
        SERVING_SIZE = new TextView(p); SERVING_SIZE.setText(Provided_Serving_Size);
        LayoutParams ActualServingSize_params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ActualServingSize_params.weight = 1;
        SERVING_SIZE.setTextSize(15);
        SERVING_SIZE.setLayoutParams(ActualServingSize_params);
        SERVING_SIZE.setTypeface(Typeface.SANS_SERIF);
        servingSizeLL.addView(SERVING_SIZE);

        //Now add servingSizeLL to main_middle:
        main_middle.addView(servingSizeLL);

        //LabelItemPrice and ActualItemPrice are both in a linear layout, we will add this linear layout to main middle:
        LinearLayout ItemPriceLL = new LinearLayout(p);
        LayoutParams ItemPriceLL_params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ItemPriceLL.setOrientation(LinearLayout.HORIZONTAL);
        ItemPriceLL_params.setMargins(dpToPx(5,p),0,dpToPx(5,p),dpToPx(5,p));
        ItemPriceLL.setLayoutParams(ItemPriceLL_params);

        //Add labelItemPrice to above ItemPriceLL:
        labelItemPrice = new TextView(p); labelItemPrice.setText("Item Price:");
        LayoutParams labelItemPrice_params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        labelItemPrice.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        labelItemPrice.setTextSize(15);
        labelItemPrice_params.weight = 1;
        labelItemPrice.setLayoutParams(labelItemPrice_params);
        ItemPriceLL.addView(labelItemPrice);

        //Add actualItemPrice to above ItemPriceLL:
        ITEM_TOTAL_COST = new TextView(p); ITEM_TOTAL_COST.setText("R "+ Provided_Price);
        LayoutParams ActualItemCost_params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ActualItemCost_params.weight = 1;
        ITEM_TOTAL_COST.setTextSize(15);
        ITEM_TOTAL_COST.setTypeface(Typeface.SANS_SERIF);
        ITEM_TOTAL_COST.setLayoutParams(ActualItemCost_params);
        ItemPriceLL.addView(ITEM_TOTAL_COST);

        //Now add ItemPriceLL to main_middle:
        main_middle.addView(ItemPriceLL);




        //Populate main_right
        LayoutParams delete_bin_params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        delete_bin_params.weight = 1;
        delete_bin_params.gravity = Gravity.CENTER_HORIZONTAL;
        delete_bin.setLayoutParams(delete_bin_params);
        main_right.addView(delete_bin);









        //Test
        main_horizontal.addView(main_left);
        main_horizontal.addView(main_middle);
        main_horizontal.addView(main_right);


        addView(main_horizontal);




    }
    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }





}
