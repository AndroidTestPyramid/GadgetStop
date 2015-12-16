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
    when(resources.getString(R.string.indian_currency)).thenReturn("Rs. ");
    when(resources.getString(R.string.percentage_sign)).thenReturn("%");
    when(resources.getString(R.string.label_new)).thenReturn("New");
    when(resources.getString(R.string.popular)).thenReturn("Popular");

    Product product = new ProductBuilder()
        .withPrice(25).build();

    productViewModel = new ProductViewModel(product, resources);
  }

  @Test
  public void shouldGetMassagedPrice(){
    assertEquals("Rs. 25", productViewModel.getPrice());
  }

  @Test
  public void popularityViewShouldBeVisibleAsProductIsNew(){
    Product product = new ProductBuilder()
        .withIsNew().build();

    productViewModel = new ProductViewModel(product, resources);

    assertEquals(View.VISIBLE, productViewModel.getPopularityLabelVisibility());
  }

  @Test
  public void popularityViewShouldBeVisibleAsProductIsPopular(){
    Product product = new ProductBuilder()
        .withIsPopular().build();

    productViewModel = new ProductViewModel(product, resources);

    assertEquals(View.VISIBLE, productViewModel.getPopularityLabelVisibility());
  }

  @Test
  public void popularityViewShouldBeVisibleAsProductIsNewAndPopular(){
    Product product = new ProductBuilder()
        .withIsPopular()
        .withIsNew().build();

    productViewModel = new ProductViewModel(product, resources);

    assertEquals(View.VISIBLE, productViewModel.getPopularityLabelVisibility());
  }

  @Test
  public void popularityLabelShouldBeNewAsProductIsNew(){
    Product product = new ProductBuilder()
        .withIsNew().build();

    productViewModel = new ProductViewModel(product, resources);

    assertEquals("New", productViewModel.getPopularityLabel());
  }

  @Test
  public void popularityLabelShouldBePopularAsProductIsPopular(){
    Product product = new ProductBuilder()
        .withIsPopular().build();

    productViewModel = new ProductViewModel(product,resources);

    assertEquals("Popular", productViewModel.getPopularityLabel());
  }

  @Test
  public void popularityLabelShouldBePopularAsProductIsPopularAndNew(){
    Product product = new ProductBuilder()
        .withIsPopular()
        .withIsNew().build();

    productViewModel = new ProductViewModel(product, resources);

    assertEquals("Popular", productViewModel.getPopularityLabel());
  }

  @Test
  public void popularityLabelShouldBeRedAsProductIsNew(){
    Product product = new ProductBuilder()
        .withIsNew().build();

    productViewModel = new ProductViewModel(product, resources);

    assertEquals(R.color.red, productViewModel.getPopularityLabelTextColor());
  }

  @Test
  public void popularityLabelShouldBePurpleAsProductIsPopular(){
    Product product = new ProductBuilder()
        .withIsPopular().build();

    productViewModel = new ProductViewModel(product, resources);

    assertEquals(R.color.purple, productViewModel.getPopularityLabelTextColor());
  }

  @Test
  public void popularityLabelShouldBePurpleAsProductIsPopularAndNew(){
    Product product = new ProductBuilder()
        .withIsPopular()
        .withIsNew().build();

    productViewModel = new ProductViewModel(product, resources);

    assertEquals(R.color.purple, productViewModel.getPopularityLabelTextColor());
  }
}
