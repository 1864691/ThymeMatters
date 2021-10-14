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

public class help_pageTest {

    @Rule
    public ActivityTestRule<help_page> helpPageTestRule = new ActivityTestRule<>(help_page.class);
    private help_page helpPage = null;

    @Before
    public void setUp() throws Exception {
        helpPage = helpPageTestRule.getActivity();
        helpPage.sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
    }

    @Test
    public void helpPageUIItem() throws Exception{
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.txt_help)).check(matches((isDisplayed())));
        onView(withId(R.id.textView3)).check(matches((isDisplayed())));
        onView(withId(R.id.change_delivery)).check(matches((isDisplayed())));
        onView(withId(R.id.change_date)).check(matches((isDisplayed())));
        onView(withId(R.id.change_personal_details)).check(matches((isDisplayed())));
        onView(withId(R.id.change_other)).check(matches((isDisplayed())));
        onView(withId(R.id.textView4)).check(matches((isDisplayed())));
    }

    @After
    public void tearDown() throws Exception {
        helpPage = null;
    }
}