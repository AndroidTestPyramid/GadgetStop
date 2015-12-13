package droidcon.gadgetstop.shopping.presenter;

import android.content.res.Resources;

import org.junit.Before;
import org.junit.Test;

import droidcon.gadgetstop.R;
import droidcon.gadgetstop.shopping.builder.ProductBuilder;
import droidcon.gadgetstop.shopping.cart.model.ProductInCart;
import droidcon.gadgetstop.shopping.model.Product;
import droidcon.gadgetstop.shopping.view.ProductDetailView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductDetailPresenterTest {
  private ProductDetailPresenter productDetailsPresenter;
  private ProductDetailView productDetailView;

  @Before
  public void setup(){
    Product product = new ProductBuilder()
        .withPrice(25)
        .withTitle("watch")
        .withUpcomingDeal(50)
        .withDescription("watch_desc").build();

    final Resources resources = mock(Resources.class);
    when(resources.getString(R.string.cost)).thenReturn("Rs. ");
    when(resources.getString(R.string.percentage_sign)).thenReturn("%");

    productDetailView = mock(ProductDetailView.class);
    final ProductPresenter productPresenter = mock(ProductPresenter.class);

    productDetailsPresenter = new ProductDetailPresenter(productDetailView, product, resources, productPresenter);
    when(resources.getString(R.string.addedToCart)).thenReturn("Item saved to cart");
    productDetailsPresenter.renderDetailedView();
  }

  @Test
  public void shouldInvokeSetDescriptionOnTheDetailsScreen(){
    verify(productDetailView).setDescription("watch_desc");
  }

  @Test
  public void shouldShowToastMessageOnSavingProductToDB(){
    final ProductInCart productInCart = mock(ProductInCart.class);

    productDetailsPresenter.saveProduct(productInCart);

    verify(productInCart).save();
    verify(productDetailView).showToastWithMessage("Item saved to cart");
  }
}