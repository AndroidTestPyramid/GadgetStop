package droidcon.gadgetstop.shopping.presenter;

import android.content.res.Resources;

import droidcon.gadgetstop.R;
import droidcon.gadgetstop.shopping.model.Product;
import droidcon.gadgetstop.shopping.repository.ProductRepository;
import droidcon.gadgetstop.shopping.view.ProductDetailView;
import droidcon.gadgetstop.shopping.viewmodel.ProductViewModel;

public class ProductDetailPresenter {

  private final ProductDetailView productDetailView;
  private final Resources resources;
  private ProductRepository productRepository;
  private final ProductViewModel productViewModel;

  public ProductDetailPresenter(Product product, ProductRepository productRepository, Resources resources, ProductDetailView productDetailView) {
    this.productDetailView = productDetailView;
    this.resources = resources;
    this.productRepository = productRepository;
    productViewModel = new ProductViewModel(product, resources);
  }

  public void renderDetailedView() {
    productDetailView.renderProductTitle(productViewModel.getTitle());
    productDetailView.renderProductPrice(productViewModel.getPrice());
    productDetailView.renderProductPopularity(productViewModel.getPopularityLabel(), productViewModel.getPopularityLabelTextColor(), productViewModel.getPopularityLabelVisibility());
    productDetailView.renderDescription(productViewModel.getDescription());
  }

  public void saveProduct(Product product) {
    if (productRepository.hasProduct(product)) {
      productDetailView.showDialogWithMessage(resources.getString(R.string.already_added_to_cart));
    } else {
      productRepository.save(product);
      productDetailView.showToastWithMessage(resources.getString(R.string.addedToCart));
    }
  }
}
