package droidcon.gadgetstop.shopping.view;

import java.util.List;

import droidcon.gadgetstop.shopping.model.Product;
import droidcon.gadgetstop.shopping.viewmodel.ProductViewModel;

public interface ProductListView {
  void render(List<Product> products, List<ProductViewModel> productViewModels);
  void dismissLoader();
  void showErrorDialog(String string);
}
