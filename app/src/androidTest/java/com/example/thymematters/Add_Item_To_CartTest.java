package com.example.thymematters;

import android.content.Intent;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

public class Add_Item_To_CartTest {

    @Rule
    public ActivityTestRule<Add_Item_To_Cart> addToCartTestRule = new ActivityTestRule<>(Add_Item_To_Cart.class);
    private Add_Item_To_Cart addToCart = null;

    @Before
    public void setUp() throws Exception {
        addToCart = addToCartTestRule.getActivity();
        addToCart.sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
    }

//    @Test
//    public void addToCartUIItem() throws Exception{
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        onView(withId(R.id.textView10)).check(matches((isDisplayed())));
//        onView(withId(R.id.textView89)).check(matches((isDisplayed())));
//        onView(withId(R.id.textView90)).check(matches((isDisplayed())));
//        onView(withId(R.id.spinnerServingSize)).check(matches((isDisplayed())));
//        onView(withId(R.id.NotePrices)).check(matches((isDisplayed())));
//        onView(withId(R.id.textView91)).check(matches((isDisplayed())));
//        onView(withId(R.id.et_Additional_Notes)).check(matches((isDisplayed())));
//        onView(withId(R.id.btn_addMeal_toCart)).check(matches((isDisplayed())));
//    }

    @After
    public void tearDown() throws Exception {
        addToCart = null;
    }
}