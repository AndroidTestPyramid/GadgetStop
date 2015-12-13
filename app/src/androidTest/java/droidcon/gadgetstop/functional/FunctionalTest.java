package droidcon.gadgetstop.functional;

import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import droidcon.gadgetstop.R;
import droidcon.gadgetstop.login.view.LoginActivity;
import droidcon.gadgetstop.rule.DataResetRule;
import droidcon.gadgetstop.rule.MockWebServerRule;
import droidcon.gadgetstop.service.APIClient;

import static android.support.test.InstrumentationRegistry.getContext;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static droidcon.gadgetstop.util.TestUtilities.readInputStreamFrom;
import static org.hamcrest.CoreMatchers.anything;

@RunWith(AndroidJUnit4.class)
public class FunctionalTest {

  @Rule
  public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);

  @Rule
  public MockWebServerRule mockWebServerRule = new MockWebServerRule();

  @Rule
  public DataResetRule dataResetRule = new DataResetRule();

  String electronicsResponse = "[{\"product_id\":1,\"title\": \"Snoogg Peacock Real Case Cover For Google Nexus 5\",\"description\": \"Slim, light ,Sturdy & stylish polycarbonate case\", \"image_url\": \"http://localhost:4568/sample_images/nexus_5_case11.jpg\",\"isNew\":true,\"isPopular\":true,\"upcoming_deal\":51,\"price\":899}]";

  String accessoriesResponse = "[{\"product_id\":1,\"title\": \"Snoogg Peacock Real Case Cover For Google Nexus 5\",\"description\": \"Slim, light ,Sturdy & stylish polycarbonate case\", \"image_url\": \"http://localhost:4568/sample_images/nexus_5_case11.jpg\",\"isNew\":true,\"isPopular\":true,\"upcoming_deal\":51,\"price\":1220}]";

  @Test
  public void shouldBeAbleToAddItemsToTheCart() throws IOException, InterruptedException {
    String email = "v@v.com";
    String password = "12345";
    onView(withId(R.id.email)).perform(ViewActions.typeText(email));
    onView(withId(R.id.password)).perform(ViewActions.typeText(password));

    mockWebServerRule.mockResponse(String.format("/sample_images/%s/%s.json", email, password), APIClient.RequestType.GET.name(), "");
    mockWebServerRule.mockResponse("/sample_images/droidcon_electronics.json", APIClient.RequestType.GET.name(), electronicsResponse);
    mockWebServerRule.mockResponse("/sample_images/droidcon_accessories.json", APIClient.RequestType.GET.name(), accessoriesResponse);
    mockWebServerRule.mockResponse("/sample_images/nexus_5_case11.jpg", APIClient.RequestType.GET.name(), readInputStreamFrom("ic_launcher.png", getContext()));

    onView(withId(R.id.sign_in_button)).perform(click());

    onData(anything()).inAdapterView(withContentDescription(R.string.electronics)).atPosition(0).perform(click());

    onView(withId(R.id.add_to_cart)).perform(click());

    pressBack();

    onView(withId(R.id.num_of_products)).check(matches(withText("1")));
  }
}
