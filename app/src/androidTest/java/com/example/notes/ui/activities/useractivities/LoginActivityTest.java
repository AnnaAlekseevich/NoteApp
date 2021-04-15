package com.example.notes.ui.activities.useractivities;

import androidx.test.rule.ActivityTestRule;

import com.example.notes.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void onClick() throws Exception {
        onView(withId(R.id.tv_register)).perform(click());
    }

}