package com.example.thymematters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Intent;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class HomeActivityTest {

    @Rule
    public ActivityTestRule<HomeActivity> homeActivityTestRule = new ActivityTestRule<>(HomeActivity.class);
    private HomeActivity homeActivity = null;

    @Before
    public void setUp() throws Exception {
        homeActivity = homeActivityTestRule.getActivity();
        homeActivity.sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
    }

    @Test
    public void homeUIItem() throws Exception{
        onView(withId(R.id.menu)).check(matches((isDisplayed())));
        onView(withId(R.id.category)).check(matches((isDisplayed())));
        onView(withId(R.id.soup)).check(matches((isDisplayed())));
        onView(withId(R.id.fish)).check(matches((isDisplayed())));
        onView(withId(R.id.meat)).check(matches((isDisplayed())));
        onView(withId(R.id.vegetarian)).check(matches((isDisplayed())));
        onView(withId(R.id.dessert)).check(matches((isDisplayed())));
        onView(withId(R.id.tv_info)).check(matches((isDisplayed())));
    }

    @After
    public void tearDown() throws Exception {
        homeActivity = null;
    }
}