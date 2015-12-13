package droidcon.gadgetstop.shopping.presenter;

import android.content.res.Resources;

import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import droidcon.gadgetstop.R;
import droidcon.gadgetstop.service.ResponseCallback;
import droidcon.gadgetstop.service.ResponseDeserializer;
import droidcon.gadgetstop.service.ResponseDeserializerFactory;
import droidcon.gadgetstop.shopping.model.Product;
import droidcon.gadgetstop.shopping.service.ProductsFetcherService;
import droidcon.gadgetstop.shopping.view.ProductListView;
import droidcon.gadgetstop.shopping.viewmodel.ProductViewModel;

public class ProductListPresenter {
  private final ProductListView productListView;
  private final ProductsFetcherService productsFetcherService;
  private final Resources resources;

  public ProductListPresenter(ProductListView productListView, ProductsFetcherService productsFetcherService, Resources resources) {
    this.productListView = productListView;
    this.productsFetcherService = productsFetcherService;
    this.resources = resources;
  }

  public void fetch() {
    productsFetcherService.execute(productsCallback());
  }

  private ResponseCallback<ArrayList<Product>> productsCallback() {
    return new ResponseCallback<ArrayList<Product>>() {
      @Override
      public ArrayList<Product> deserialize(InputStream response) {
        final TypeToken<ArrayList<Product>> typeToken = new TypeToken<ArrayList<Product>>() {
        };
        ResponseDeserializer<ArrayList<Product>> objectResponseDeserializer = ResponseDeserializerFactory.objectDeserializer(typeToken.getType());
        return objectResponseDeserializer.deserialize(response);
      }

      @Override
      public void onSuccess(ArrayList<Product> response) {
        productListView.dismissLoader();
        final List<ProductViewModel> productViewModels = new ArrayList<>();
        for (Product product : response) {
          productViewModels.add(new ProductViewModel(product, resources));
        }
        productListView.render(response, productViewModels);
      }

      @Override
      public void onError(Exception exception) {
        productListView.dismissLoader();
        productListView.showErrorDialog(resources.getString(R.string.technical_difficulty));
      }
    };
  }
}
