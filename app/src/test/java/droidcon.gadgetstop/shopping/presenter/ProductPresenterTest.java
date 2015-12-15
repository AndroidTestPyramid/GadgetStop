package droidcon.gadgetstop.shopping.presenter;

import android.content.res.Resources;
import android.view.View;

import org.junit.Test;

import droidcon.gadgetstop.R;
import droidcon.gadgetstop.shopping.builder.ProductBuilder;
import droidcon.gadgetstop.shopping.model.Product;
import droidcon.gadgetstop.shopping.view.ProductView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductPresenterTest {

  @Test
  public void shouldRenderDataOnView() {
    final ProductView productView = mock(ProductView.class);

    final Resources resources = mock(Resources.class);
    when(resources.getString(R.string.currency)).thenReturn("Rs. ");
    when(resources.getString(R.string.percentage_sign)).thenReturn("%");
    when(resources.getString(R.string.empty_string)).thenReturn("");

    Product product = new ProductBuilder()
        .withTitle("watch")
        .withPrice(25).build();

    final ProductPresenter productPresenter = new ProductPresenter(productView, product, resources);

    productPresenter.renderView();

    verify(productView).renderProductTitle("watch");
    verify(productView).renderProductPrice("Rs. 25");
    verify(productView).renderProductPopularity("", R.color.white, View.GONE);
  }
}
