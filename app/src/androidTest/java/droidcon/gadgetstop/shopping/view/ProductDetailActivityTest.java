package droidcon.gadgetstop.shopping.view;

import android.content.Intent;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

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

import static android.support.test.InstrumentationRegistry.getContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static droidcon.gadgetstop.util.TestUtilities.readFrom;
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class ProductDetailActivityTest {

  @Rule
  public ActivityTestRule<ProductDetailActivity> activityTestRule = new ActivityTestRule<>(ProductDetailActivity.class, false, false);

  @Rule
  public MockWebServerRule mockWebServerRule = new MockWebServerRule();

  @Rule
  public DataResetRule dataResetRule = new DataResetRule();

  @Before
  public void setup() throws IOException {
    mockWebServerRule.mockResponse("/sample_images/nexus_5_case11.jpg", APIClient.RequestType.GET.name(), readFrom("ic_launcher.png", getContext()));

    Product product = new Product(0, "http://localhost:4568/sample_images/nexus_5_case11.jpg", 20, "ProductTitle", "ProductDesc", 30, false, true);
    Intent intent = new Intent();
    intent.putExtra(ProductsBaseFragment.PRODUCT_KEY, product);
    activityTestRule.launchActivity(intent);
  }

  @Test
  public void shouldShowDetailsOfTheProduct() throws IOException {
    onView(withId(R.id.product_title)).check(matches(withText("ProductTitle")));
    onView(withId(R.id.product_description)).check(matches(withText("ProductDesc")));
    onView(withId(R.id.price)).check(matches(withText("Rs.20")));
    onView(withId(R.id.upcoming_deal)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    onView(withText(R.string.upcoming_deal)).check(matches(isDisplayed()));
    onView(withId(R.id.percentage)).check(matches(withText("30%")));
    onView(withId(R.id.popularity)).check(matches(withText("Popular")));
  }

  @Test
  public void shouldScrollAndAddItemToCart() throws IOException {
    assertEquals(0, ProductInCart.count(ProductInCart.class, null, null));

    onView(withId(R.id.product_details)).perform(scrollTo());
    onView(withId(R.id.add_to_cart)).perform(click());
    onView(withText(R.string.addedToCart)).inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));

    long numOfProductsInTheCart = ProductInCart.count(ProductInCart.class, null, null);
    assertEquals(1, numOfProductsInTheCart);
  }

  @Test
  public void shouldNotAddItemToCartIfAlreadyPresent() throws IOException {
    new ProductInCart(0).save();

    onView(withId(R.id.product_details)).perform(scrollTo());
    onView(withId(R.id.add_to_cart)).perform(click());
    onView(withText(R.string.already_added_to_cart)).inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));

    long numOfProductsInTheCart = ProductInCart.count(ProductInCart.class, null, null);
    assertEquals(1, numOfProductsInTheCart);
  }
}