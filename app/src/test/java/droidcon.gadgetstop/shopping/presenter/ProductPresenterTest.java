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
  public void shouldRenderDataOnView(){
    final ProductView productView = mock(ProductView.class);

    final ImagePresenter imagePresenter = mock(ImagePresenter.class);
    final Resources resources = mock(Resources.class);
    when(resources.getString(R.string.cost)).thenReturn("Rs. ");
    when(resources.getString(R.string.percentage_sign)).thenReturn("%");

    Product product = new ProductBuilder()
        .withTitle("watch")
        .withPrice(25)
        .withUpcomingDeal(50).build();

    final ProductPresenter productPresenter = new ProductPresenter(productView, product, imagePresenter, resources);

    productPresenter.renderView();

    verify(productView).renderProductTitle("watch");
    verify(productView).renderProductCost("Rs. 25");
    verify(productView).renderProductUpcomingDeal(View.VISIBLE, "50%");
    verify(productView).renderProductPopularity("", R.color.white, View.GONE);
  }
}
