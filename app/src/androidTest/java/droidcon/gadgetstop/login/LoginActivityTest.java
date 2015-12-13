package droidcon.gadgetstop.login;


import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import com.orm.dsl.Ignore;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import droidcon.gadgetstop.R;
import droidcon.gadgetstop.login.view.LoginActivity;
import droidcon.gadgetstop.rule.MockWebServerRule;
import droidcon.gadgetstop.service.APIClient;
import droidcon.gadgetstop.shopping.view.ShoppingActivity;

import static android.app.Activity.RESULT_OK;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.anyIntent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

  @Rule
  public IntentsTestRule activityTestRule = new IntentsTestRule(LoginActivity.class);

  @Rule
  public MockWebServerRule mockWebServerRule = new MockWebServerRule();

  @Test
  public void shouldDisplayEmailError() {
    onView(withId(R.id.sign_in_button)).perform(ViewActions.click());

    onView(withId(R.id.email)).check(matches(hasFocus()));
    onView(withId(R.id.email)).check(matches(hasErrorText("This field is required")));
  }

  @Test
  public void shouldDisplayPasswordError() {
    onView(withId(R.id.email)).perform(ViewActions.typeText("v@v.com"));
    onView(withId(R.id.sign_in_button)).perform(ViewActions.click());

    onView(withId(R.id.password)).check(matches(hasFocus()));
    onView(withId(R.id.password)).check(matches(hasErrorText("This field is required")));
  }

  @Test
  public void shouldDisplayErrorOnInvalidCredential() {
    onView(withId(R.id.email)).perform(ViewActions.typeText("v@v.com"));
    onView(withId(R.id.password)).perform(ViewActions.typeText("12345"));

    onView(withId(R.id.sign_in_button)).perform(ViewActions.click());

    onView(withText(R.string.invalid_credential)).inRoot(withDecorView(ViewMatchers.withChild(ViewMatchers.withText(R.string.invalid_credential)))).check(matches(isDisplayed()));
    onView(withId(R.id.email)).check(matches(withText("")));
    onView(withId(R.id.password)).check(matches(withText("")));
    onView(withId(R.id.email)).check(matches(hasFocus()));
  }

  @Test
  public void shouldNavigateToShoppingActivityOnValidCredential() throws IOException {
    String email = "v@v.com";
    String password = "12345";
    onView(withId(R.id.email)).perform(ViewActions.typeText(email));
    onView(withId(R.id.password)).perform(ViewActions.typeText(password));

    String url = String.format("/sample_images/%s/%s.json", email, password);
    mockWebServerRule.mockResponse(url, APIClient.RequestType.GET.name(), "");

    intending(anyIntent()).respondWith(new Instrumentation.ActivityResult(RESULT_OK, new Intent()));

    onView(withId(R.id.sign_in_button)).perform(ViewActions.click());

    intended(hasComponent(ShoppingActivity.class.getName()));
  }

}
