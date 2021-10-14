package com.example.thymematters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.app.Instrumentation;
import android.content.Intent;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    private MainActivity mainActivity = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(Register.class.getName(),null ,false);
    Instrumentation.ActivityMonitor monitor2 = getInstrumentation().addMonitor(AdminLogin.class.getName(),null ,false);
    Instrumentation.ActivityMonitor monitor3 = getInstrumentation().addMonitor(HomeActivity.class.getName(),null ,false);

    public static final String STRING_TO_BE_TYPED_EMAIL = "tristenhav@gmail.com";
    public static final String STRING_TO_BE_TYPED_EMAIL1 = "tristenhav@gmail.com";
    public static final String STRING_TO_BE_TYPED_PHONE = "123456789";
    public static final String STRING_TO_BE_TYPED_USERNAME = "unittester";
    public static final String STRING_TO_BE_TYPED_PASSWORD = "Exciting";

    @Before
    public void setUp() throws Exception {
        mainActivity = mainActivityTestRule.getActivity();
        mainActivity.sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
    }


    @Test
    public void uiItems() throws Exception{
        onView(withId(R.id.TV_email)).check(matches((isDisplayed())));
    }

    @After
    public void tearDown() throws Exception {
        mainActivity = null;
    }
}