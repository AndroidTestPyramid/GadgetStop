package droidcon.gadgetstop.shopping.presenter;

import android.content.res.Resources;
import android.widget.ImageView;

import droidcon.gadgetstop.R;
import droidcon.gadgetstop.shopping.model.Product;
import droidcon.gadgetstop.shopping.repository.ProductRepository;
import droidcon.gadgetstop.shopping.view.ProductDetailView;
import droidcon.gadgetstop.shopping.viewmodel.ProductViewModel;

public class ProductDetailPresenter {

  private final ProductDetailView productDetailView;
  private final Resources resources;
  private final ProductPresenter productPresenter;
  private ProductRepository productRepository;
  private final ProductViewModel productViewModel;

  public ProductDetailPresenter(ProductDetailView productDetailView, Product product, Resources resources, ProductPresenter productPresenter, ProductRepository productRepository) {
    this.productDetailView = productDetailView;
    this.resources = resources;
    this.productPresenter = productPresenter;
    this.productRepository = productRepository;
    productViewModel = new ProductViewModel(product, resources);
  }

  public void renderDetailedView() {
    productPresenter.renderView();
    productDetailView.setDescription(productViewModel.getDescription());
  }

  public void saveProduct(Product product) {
    if (productRepository.hasProduct(product)) {
      productDetailView.showDialogWithMessage(resources.getString(R.string.already_added_to_cart));
    } else {
      productRepository.save(product);
      productDetailView.showToastWithMessage(resources.getString(R.string.addedToCart));
    }
  }

  public void renderImageFor(ImageView imageView) {
    productPresenter.renderImageFor(imageView);
  }
}
