package droidcon.gadgetstop.shopping.presenter;

import android.content.res.Resources;

import org.junit.Before;
import org.junit.Test;

import droidcon.gadgetstop.R;
import droidcon.gadgetstop.shopping.builder.ProductBuilder;
import droidcon.gadgetstop.shopping.model.Product;
import droidcon.gadgetstop.shopping.repository.ProductRepository;
import droidcon.gadgetstop.shopping.view.ProductDetailView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductDetailPresenterTest {
  private ProductDetailPresenter productDetailsPresenter;
  private ProductDetailView productDetailView;
  private ProductRepository productRepository;
  private Product product;

  @Before
  public void setup(){
    product = new ProductBuilder()
        .withPrice(25)
        .withTitle("watch")
        .withDescription("watch_desc").build();

    final Resources resources = mock(Resources.class);
    when(resources.getString(R.string.indian_currency)).thenReturn("Rs. ");
    when(resources.getString(R.string.percentage_sign)).thenReturn("%");
    when(resources.getString(R.string.addedToCart)).thenReturn("Item saved to cart");
    when(resources.getString(R.string.already_added_to_cart)).thenReturn("Item already added to cart");

    productDetailView = mock(ProductDetailView.class);

    productRepository = mock(ProductRepository.class);

    productDetailsPresenter = new ProductDetailPresenter(product, productRepository, resources, productDetailView);
    productDetailsPresenter.renderDetailedView();
  }

  @Test
  public void shouldInvokeSetDescriptionOnTheDetailsScreen(){
    verify(productDetailView).renderDescription("watch_desc");
  }

  @Test
  public void shouldShowToastMessageOnSavingProductToDB(){
    productDetailsPresenter.addToCart(product);

    verify(productRepository).save(product);
    verify(productDetailView).showToastWithMessage("Item saved to cart");
  }

  @Test
  public void shouldShowDialogMessageWhenProductIsAlreadyAddedToCart(){
    when(productRepository.hasProduct(product)).thenReturn(true);
    productDetailsPresenter.addToCart(product);

    verify(productRepository, never()).save(product);
    verify(productDetailView).showDialogWithMessage("Item already added to cart");
  }
}