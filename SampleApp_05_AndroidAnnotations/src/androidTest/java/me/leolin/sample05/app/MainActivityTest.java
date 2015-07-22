package me.leolin.sample05.app;

import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * @author leolin
 */
@LargeTest
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity_> {

    public MainActivityTest() {
        super(MainActivity_.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    public void testTextViewAdviced() throws Exception {
        onView(ViewMatchers.withId(R.id.textView))
                .check(matches(isDisplayed()))
                .check(matches(withText("Hello AspectJ!")));
    }
}