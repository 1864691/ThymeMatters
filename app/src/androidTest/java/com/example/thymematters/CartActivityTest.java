package com.example.thymematters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

import android.content.Intent;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class CartActivityTest {

    @Rule
    public ActivityTestRule<CartActivity> cartActivityTestRule = new ActivityTestRule<>(CartActivity.class);
    private CartActivity cartActivity = null;

    @Before
    public void setUp() throws Exception {
        cartActivity = cartActivityTestRule.getActivity();
        cartActivity.sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
    }

    @Test
    public void cartUIItem() throws Exception{
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.cart)).check(matches((isDisplayed())));
        onView(withId(R.id.items)).check(matches((isDisplayed())));
        onView(withId(R.id.txt_price)).check(matches((isDisplayed())));
        onView(withId(R.id.btn_pay)).check(matches((isDisplayed())));
    }

    @After
    public void tearDown() throws Exception {
        cartActivity = null;
    }
}