package me.leolin.sample02.app;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;

/**
 * @author leolin
 */
@LargeTest
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    public void testTextViewAdviced() throws Exception {
        onView(withId(R.id.textView))
                .check(matches(isDisplayed()))
                .check(matches(withText("Hello AspectJ!")));
    }
}