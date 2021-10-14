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

public class DessertActivityTest {

    @Rule
    public ActivityTestRule<DessertActivity> dessertActivityTestRule = new ActivityTestRule<>(DessertActivity.class);
    private DessertActivity dessertActivity = null;

    @Before
    public void setUp() throws Exception {
        dessertActivity = dessertActivityTestRule.getActivity();
        dessertActivity.sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
    }

    @Test
    public void dessertUIItem() throws Exception{
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.textView10)).check(matches((isDisplayed())));
        onView(withId(R.id.textView11)).check(matches((isDisplayed())));
    }

    @After
    public void tearDown() throws Exception {
        dessertActivity = null;
    }
}