package droidcon.gadgetstop.shopping.presenter;

import android.content.res.Resources;
import android.widget.ImageView;

import droidcon.gadgetstop.R;
import droidcon.gadgetstop.shopping.cart.model.ProductInCart;
import droidcon.gadgetstop.shopping.model.Product;
import droidcon.gadgetstop.shopping.presenter.ProductPresenter;
import droidcon.gadgetstop.shopping.view.ProductDetailView;
import droidcon.gadgetstop.shopping.viewmodel.ProductViewModel;

public class ProductDetailPresenter {

  private final ProductDetailView productDetailView;
  private final Resources resources;
  private final ProductPresenter productPresenter;
  private final ProductViewModel productViewModel;

  public ProductDetailPresenter(ProductDetailView productDetailView, Product product, Resources resources, ProductPresenter productPresenter) {
    this.productDetailView = productDetailView;
    this.resources = resources;
    this.productPresenter = productPresenter;
    productViewModel = new ProductViewModel(product, resources);
  }

  public void renderDetailedView() {
    productPresenter.renderView();
    productDetailView.setDescription(productViewModel.getDescription());
  }

  public void saveProduct(ProductInCart product) {
    product.save();
    productDetailView.showToastWithMessage(resources.getString(R.string.addedToCart));
  }

  public void renderImageFor(ImageView imageView) {
    productPresenter.renderImageFor(imageView);
  }
}
