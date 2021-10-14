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

public class View_ReportsTest {

    @Rule
    public ActivityTestRule<View_Reports> viewReportTestRule = new ActivityTestRule<>(View_Reports.class);
    private View_Reports viewReport = null;

    @Before
    public void setUp() throws Exception {
        viewReport = viewReportTestRule.getActivity();
        viewReport.sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
    }

    @Test
    public void viewReportUIItem() throws Exception{
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.textView2)).check(matches((isDisplayed())));
        onView(withId(R.id.salesReportbtn)).check(matches((isDisplayed())));
        onView(withId(R.id.mealsReportBtn)).check(matches((isDisplayed())));
        onView(withId(R.id.customerReportbtn)).check(matches((isDisplayed())));
        onView(withId(R.id.reportbtn)).check(matches((isDisplayed())));
        onView(withId(R.id.paymentMethodReportbtn)).check(matches((isDisplayed())));
    }

    @After
    public void tearDown() throws Exception {
        viewReport = null;
    }
}