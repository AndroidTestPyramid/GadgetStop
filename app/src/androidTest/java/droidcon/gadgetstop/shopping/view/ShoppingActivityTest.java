package droidcon.gadgetstop.shopping.view;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import droidcon.gadgetstop.R;
import droidcon.gadgetstop.rule.DataResetRule;
import droidcon.gadgetstop.rule.MockWebServerRule;
import droidcon.gadgetstop.service.APIClient;
import droidcon.gadgetstop.shopping.cart.model.ProductInCart;
import droidcon.gadgetstop.shopping.model.Product;

import static android.app.Activity.RESULT_OK;
import static android.support.test.InstrumentationRegistry.getContext;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.anyIntent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static droidcon.gadgetstop.util.TestUtilities.readInputStreamFrom;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class ShoppingActivityTest {

  @Rule
  public IntentsTestRule<ShoppingActivity> activityTestRule = new IntentsTestRule<>(ShoppingActivity.class, false, false);

  @Rule
  public MockWebServerRule mockWebServerRule = new MockWebServerRule();

  @Rule
  public DataResetRule dataResetRule = new DataResetRule();

  @Before
  public void setup() throws IOException {
    String electronics = "[{\"product_id\":1,\"title\": \"Snoogg Peacock Real Case Cover For Google Nexus 5\",\"description\": \"Slim, light ,Sturdy & stylish polycarbonate case\", \"image_url\": \"http://localhost:4568/sample_images/nexus_5_case11.jpg\",\"isNew\":true,\"isPopular\":true,\"price\":899}]";
    String accessories = "[{\"product_id\":2,\"title\": \"Snoogg Peacock\",\"description\": \"Slim, light ,Sturdy & stylish polycarbonate case\", \"image_url\": \"http://localhost:4568/sample_images/nexus_5_case11.jpg\",\"isNew\":true,\"isPopular\":true,\"price\":2500}]";

    mockWebServerRule.mockResponse("/sample_images/droidcon_electronics.json", APIClient.RequestType.GET.name(), electronics);
    mockWebServerRule.mockResponse("/sample_images/droidcon_accessories.json", APIClient.RequestType.GET.name(), accessories);
    mockWebServerRule.mockResponse("/sample_images/nexus_5_case11.jpg", APIClient.RequestType.GET.name(), readInputStreamFrom("ic_launcher.png", getContext()));
  }

  @Test
  public void numOfItemsInCartShouldBeVisible() throws IOException {

    ProductInCart productInCart = new ProductInCart(0);
    productInCart.save();

    activityTestRule.launchActivity(new Intent());

    onView(withId(R.id.num_of_products)).check(matches(withText("1")));
  }

  @Test
  public void shouldSwipeToAccessoriesTabAndClickOnItemToSeeDetails() throws InterruptedException {
    activityTestRule.launchActivity(new Intent());

    onView(withId(R.id.viewPager))
        .perform(swipeLeft());

    Thread.sleep(2000);

    final Product product = new Product(2,
        "http://localhost:4568/sample_images/nexus_5_case11.jpg", 2500,
        "Snoogg Peacock",
        "Slim, light ,Sturdy & stylish polycarbonate case", true, true);

    intending(anyIntent()).respondWith(new Instrumentation.ActivityResult(RESULT_OK, new Intent()));

    onData(CoreMatchers.anything()).inAdapterView(withContentDescription(R.string.accessories)).atPosition(0).perform(click());

    intended(allOf(hasComponent(ProductDetailActivity.class.getName()), hasExtra(ProductsBaseFragment.PRODUCT_KEY, product)));
  }
}