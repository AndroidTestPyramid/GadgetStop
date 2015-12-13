package droidcon.gadgetstop.shopping.viewmodel;

import android.content.res.Resources;
import android.view.View;

import org.junit.Before;
import org.junit.Test;

import droidcon.gadgetstop.R;
import droidcon.gadgetstop.shopping.builder.ProductBuilder;
import droidcon.gadgetstop.shopping.model.Product;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductViewModelTest {

  private ProductViewModel productViewModel;
  private Resources resources;

  @Before
  public void setup(){
    resources = mock(Resources.class);
    when(resources.getString(R.string.cost)).thenReturn("Rs. ");
    when(resources.getString(R.string.percentage_sign)).thenReturn("%");
    when(resources.getString(R.string.product_new)).thenReturn("New");
    when(resources.getString(R.string.popular)).thenReturn("Popular");

    Product product = new ProductBuilder()
        .withPrice(25)
        .withUpcomingDeal(50).build();

    productViewModel = new ProductViewModel(product, resources);
  }

  @Test
  public void shouldGetMassagedPrice(){
    assertEquals("Rs. 25", productViewModel.getPrice());
  }

  @Test
  public void shouldGetMassagedUpcomingDeal(){
    assertEquals("50%", productViewModel.getUpcomingDeal());
  }

  @Test
  public void shouldGetEmptyUpcomingDeal(){
    Product product = new ProductBuilder()
        .withUpcomingDeal(0).build();

    productViewModel = new ProductViewModel(product, resources);

    assertEquals("", productViewModel.getUpcomingDeal());
  }

  @Test
  public void upcomingDealShouldBeVisible(){
    assertEquals(View.VISIBLE, productViewModel.getUpcomingDealVisibilityStatus());
  }

  @Test
  public void upcomingDealShouldBeNotVisible(){
    Product product = new ProductBuilder()
        .withUpcomingDeal(0).build();

    productViewModel = new ProductViewModel(product, resources);

    assertEquals(View.GONE, productViewModel.getUpcomingDealVisibilityStatus());
  }

  @Test
  public void newLabelShouldBeVisibleAsProductIsNew(){
    Product product = new ProductBuilder()
        .withIsNew().build();

    productViewModel = new ProductViewModel(product, resources);

    assertEquals("New", productViewModel.getPopularityLabel());
  }

  @Test
  public void popularLabelShouldBeVisibleAsProductIsPopularAndNew(){
    Product product = new ProductBuilder()
        .withIsPopular()
        .withIsNew().build();

    productViewModel = new ProductViewModel(product, resources);

    assertEquals("Popular", productViewModel.getPopularityLabel());
  }

  @Test
  public void popularLabelShouldBeVisibleAsProductIsPopular(){
    Product product = new ProductBuilder()
        .withIsPopular().build();

    productViewModel = new ProductViewModel(product,resources);

    assertEquals("Popular", productViewModel.getPopularityLabel());
  }

  @Test
  public void popularityViewShouldBeVisible(){
    Product product = new ProductBuilder()
        .withIsPopular()
        .withIsNew().build();

    productViewModel = new ProductViewModel(product, resources);

    assertEquals(View.VISIBLE, productViewModel.getPopularityVisibilityStatus());
  }

  @Test
  public void popularityViewShouldBeVisibleAsProductIsNew(){
    Product product = new ProductBuilder()
        .withIsNew().build();

    productViewModel = new ProductViewModel(product, resources);

    assertEquals(View.VISIBLE, productViewModel.getPopularityVisibilityStatus());
  }

  @Test
  public void popularityViewShouldBeVisibleAsProductIsPopular(){
    Product product = new ProductBuilder()
        .withIsPopular().build();

    productViewModel = new ProductViewModel(product, resources);

    assertEquals(View.VISIBLE, productViewModel.getPopularityVisibilityStatus());
  }

  @Test
  public void popularityLabelShouldBeGreenAsProductIsNew(){
    Product product = new ProductBuilder()
        .withIsNew().build();

    productViewModel = new ProductViewModel(product, resources);

    assertEquals(R.color.red, productViewModel.getPopularityTextColor());
  }

  @Test
  public void popularityLabelShouldBePurpleAsProductIsPopularAndNew(){
    Product product = new ProductBuilder()
        .withIsPopular()
        .withIsNew().build();

    productViewModel = new ProductViewModel(product, resources);

    assertEquals(R.color.purple, productViewModel.getPopularityTextColor());
  }

  @Test
  public void popularityLabelShouldBePurpleAsProductIsPopular(){
    Product product = new ProductBuilder()
        .withIsPopular().build();

    productViewModel = new ProductViewModel(product, resources);

    assertEquals(R.color.purple, productViewModel.getPopularityTextColor());
  }
}
