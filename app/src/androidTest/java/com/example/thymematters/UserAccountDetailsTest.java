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

public class UserAccountDetailsTest {

    @Rule
    public ActivityTestRule<UserAccountDetails> userAccountDetailsTestRule = new ActivityTestRule<>(UserAccountDetails.class);
    private UserAccountDetails userAccountDetails = null;

    @Before
    public void setUp() throws Exception {
        userAccountDetails = userAccountDetailsTestRule.getActivity();
        userAccountDetails.sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
    }

    @Test
    public void detailsUIItem() throws Exception{
        onView(withId(R.id.textView7)).check(matches((isDisplayed())));
        onView(withId(R.id.et_FName)).check(matches((isDisplayed())));
        onView(withId(R.id.et_LName)).check(matches((isDisplayed())));
        onView(withId(R.id.et_DeliveryAddress)).check(matches((isDisplayed())));
        onView(withId(R.id.et_EmailAddress)).check(matches((isDisplayed())));
        onView(withId(R.id.et_Phone)).check(matches((isDisplayed())));
        onView(withId(R.id.et_Password)).check(matches((isDisplayed())));
        onView(withId(R.id.et_ConfirmPassword)).check(matches((isDisplayed())));
        onView(withId(R.id.btn_Register)).check(matches((isDisplayed())));
        onView(withId(R.id.btn_return)).check(matches((isDisplayed())));
    }

    @After
    public void tearDown() throws Exception {
        userAccountDetails = null;
    }
}