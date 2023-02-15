package algonquin.cst2335.lee00824;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Test case to verify the password validation when an invalid password is entered.
     */
    @Test
    public void mainActivityTest() {

        ViewInteraction appCompatEditText = onView(withId(R.id.editText));
        appCompatEditText.perform(replaceText("12345"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textView));
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Test case to verify the password validation when an upper case letter is missing.
     */
    @Test
    public void testFindMissingUpperCase() {
        //find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.editText));
        //type in password123#$*
        appCompatEditText.perform(replaceText("password123#$*"));

        //find the button
        ViewInteraction materialButton = onView( withId(R.id.button));
        //click the button
        materialButton.perform(click());

        //find the text view
        ViewInteraction textView = onView( withId(R.id.textView));
        //check the text
        textView.check(matches(withText("You shall not pass!")));

    }

    /**
     * Test case to verify the password validation when a lower case letter is missing.
     */
    @Test
    public void testFindMissingLowerCase() {
        //find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.editText));
        //type in PASSWORD123@#
        appCompatEditText.perform(replaceText("PASSWORD123@#"));

        //find the button
        ViewInteraction materialButton = onView( withId(R.id.button));
        //click the button
        materialButton.perform(click());

        //find the text view
        ViewInteraction textView = onView( withId(R.id.textView));
        //check the text
        textView.check(matches(withText("You shall not pass!")));

    }

    /**
     * Test case to verify the password validation when a character letter is missing.
     */
    @Test
    public void testFindMissingCharacter() {
        //find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.editText));
        //type in Password12345
        appCompatEditText.perform(replaceText("Password12345"));

        //find the button
        ViewInteraction materialButton = onView( withId(R.id.button));
        //click the button
        materialButton.perform(click());

        //find the text view
        ViewInteraction textView = onView( withId(R.id.textView));
        //check the text
        textView.check(matches(withText("You shall not pass!")));

    }
    /**
     * Test case to verify the password validation when a number is missing.
     */
    @Test
    public void testFindMissingNumber() {
        //find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.editText));
        //type in Password@#
        appCompatEditText.perform(replaceText("Password@#"));

        //find the button
        ViewInteraction materialButton = onView( withId(R.id.button));
        //click the button
        materialButton.perform(click());

        //find the text view
        ViewInteraction textView = onView( withId(R.id.textView));
        //check the text
        textView.check(matches(withText("You shall not pass!")));

    }
    /**
     * Test case to verify if the password meets the requirements.
     */
    @Test
    public void testComplexEnough() {

        ViewInteraction appCompatEditText = onView(withId(R.id.editText));
        appCompatEditText.perform(replaceText("Password123@"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textView));
        textView.check(matches(withText("Your password meets the requirements.")));
    }


}