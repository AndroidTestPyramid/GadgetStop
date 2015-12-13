package droidcon.gadgetstop.shopping.presenter;

import android.content.res.Resources;
import android.widget.ImageView;

import droidcon.gadgetstop.shopping.model.Product;
import droidcon.gadgetstop.shopping.view.ProductView;
import droidcon.gadgetstop.shopping.viewmodel.ProductViewModel;

public class ProductPresenter {

  private final ProductView productView;
  private final Product product;
  private final ProductViewModel productViewModel;
  private final ImagePresenter imagePresenter;

  public ProductPresenter(ProductView productView, Product product, ImagePresenter imagePresenter, Resources resources) {
    this.productView = productView;
    this.product = product;
    this.productViewModel = new ProductViewModel(product, resources);
    this.imagePresenter = imagePresenter;
  }

  public void renderView() {
    productView.renderProductTitle(productViewModel.getTitle());
    productView.renderProductCost(productViewModel.getPrice());
    productView.renderProductUpcomingDeal(productViewModel.getUpcomingDealVisibilityStatus(), productViewModel.getUpcomingDeal());
    productView.renderProductPopularity(productViewModel.getPopularityLabel(), productViewModel.getPopularityTextColor(), productViewModel.getPopularityVisibilityStatus());
  }

  public void renderImageFor(ImageView imageView) {
    imagePresenter.fetchImageFor(imageView, product.getImageUrl());
  }
}
