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

public class CustomerViewOrderHistoryTest {

    @Rule
    public ActivityTestRule<CustomerViewOrderHistory> historyTestRule = new ActivityTestRule<>(CustomerViewOrderHistory.class);
    private CustomerViewOrderHistory customerViewOrderHistory = null;

    @Before
    public void setUp() throws Exception {
        customerViewOrderHistory = historyTestRule.getActivity();
        customerViewOrderHistory.sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
    }

    @Test
    public void orderHistoryUIItem() throws Exception{
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.lblOrderHistory)).check(matches((isDisplayed())));
        onView(withId(R.id.lblLoggedInAs)).check(matches((isDisplayed())));
    }

    @After
    public void tearDown() throws Exception {
        customerViewOrderHistory = null;
    }
}
