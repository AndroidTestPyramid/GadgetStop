package droidcon.gadgetstop.functional;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import droidcon.gadgetstop.R;
import droidcon.gadgetstop.rule.DataResetRule;
import droidcon.gadgetstop.rule.MockWebServerRule;
import droidcon.gadgetstop.service.APIClient;
import droidcon.gadgetstop.shopping.view.ShoppingActivity;

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
  public ActivityTestRule<ShoppingActivity> activityTestRule = new ActivityTestRule<>(ShoppingActivity.class, false, false);

  @Rule
  public MockWebServerRule mockWebServerRule = new MockWebServerRule();

  @Rule
  public DataResetRule dataResetRule = new DataResetRule();

  String electronicsResponse = "[{\"product_id\":1,\"title\": \"Snoogg Peacock Real Case Cover For Google Nexus 5\",\"description\": \"Slim, light ,Sturdy & stylish polycarbonate case\", \"image_url\": \"http://localhost:4568/sample_images/nexus_5_case11.jpg\",\"isNew\":true,\"isPopular\":true,\"price\":899}]";

  String accessoriesResponse = "[{\"product_id\":1,\"title\": \"Snoogg Peacock Real Case Cover For Google Nexus 5\",\"description\": \"Slim, light ,Sturdy & stylish polycarbonate case\", \"image_url\": \"http://localhost:4568/sample_images/nexus_5_case11.jpg\",\"isNew\":true,\"isPopular\":true,\"price\":1220}]";

  @Test
  public void shouldBeAbleToAddItemsToTheCart() throws IOException, InterruptedException {
    mockWebServerRule.mockResponse("/sample_images/droidcon_electronics.json", APIClient.RequestType.GET.name(), electronicsResponse);
    mockWebServerRule.mockResponse("/sample_images/droidcon_accessories.json", APIClient.RequestType.GET.name(), accessoriesResponse);
    mockWebServerRule.mockResponse("/sample_images/nexus_5_case11.jpg", APIClient.RequestType.GET.name(), readInputStreamFrom("ic_launcher.png", getContext()));

    activityTestRule.launchActivity(new Intent());

    onData(anything()).inAdapterView(withContentDescription(R.string.electronics)).atPosition(0).perform(click());

    onView(withId(R.id.add_to_cart)).perform(click());

    pressBack();

    onView(withId(R.id.num_of_products)).check(matches(withText("1")));
  }
}
