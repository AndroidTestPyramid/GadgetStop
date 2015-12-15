package droidcon.gadgetstop.shopping.presenter;

import android.content.res.Resources;
import android.widget.ImageView;

import droidcon.gadgetstop.shopping.model.Product;
import droidcon.gadgetstop.shopping.view.ProductView;
import droidcon.gadgetstop.shopping.viewmodel.ProductViewModel;

public class ProductPresenter {

  private final ProductView productView;
  private final ProductViewModel productViewModel;

  public ProductPresenter(ProductView productView, Product product, Resources resources) {
    this.productView = productView;
    this.productViewModel = new ProductViewModel(product, resources);
  }

  public void renderView() {
    productView.renderProductTitle(productViewModel.getTitle());
    productView.renderProductPrice(productViewModel.getPrice());
    productView.renderProductPopularity(productViewModel.getPopularityLabel(), productViewModel.getPopularityLabelTextColor(), productViewModel.getPopularityLabelVisibility());
  }

}
