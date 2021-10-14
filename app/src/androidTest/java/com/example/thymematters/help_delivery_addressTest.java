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

public class help_delivery_addressTest {

    @Rule
    public ActivityTestRule<help_delivery_address> helpAddrressTestRule = new ActivityTestRule<>(help_delivery_address.class);
    private help_delivery_address helpAddrress = null;

    @Before
    public void setUp() throws Exception {
        helpAddrress = helpAddrressTestRule.getActivity();
        helpAddrress.sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
    }

    @Test
    public void helpAddrressUIItem() throws Exception{
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.txt_help2)).check(matches((isDisplayed())));
        onView(withId(R.id.txt_help)).check(matches((isDisplayed())));
        onView(withId(R.id.et_new_address)).check(matches((isDisplayed())));
        onView(withId(R.id.textView3)).check(matches((isDisplayed())));
        onView(withId(R.id.RV_UsersOrders)).check(matches((isDisplayed())));
        onView(withId(R.id.textView4)).check(matches((isDisplayed())));
        onView(withId(R.id.btn_confirm_address_change)).check(matches((isDisplayed())));
    }

    @After
    public void tearDown() throws Exception {
        helpAddrress = null;
    }
}