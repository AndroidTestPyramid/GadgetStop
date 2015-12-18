package droidcon.gadgetstop.shopping.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.List;

import droidcon.gadgetstop.R;
import droidcon.gadgetstop.shopping.model.Product;
import droidcon.gadgetstop.shopping.viewmodel.ProductViewModel;

public abstract class ProductsBaseFragment extends Fragment implements ProductListView {

  public static final String PRODUCT_KEY = "droidcon.gadgetstop.cart.current_product";
  private ProgressDialog progressDialog;
  private GridView gridView;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    fetchProducts();
  }

  abstract public void fetchProducts();

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.products_layout, container, false);
    progressDialog = new ProgressDialog(this.getActivity());
    progressDialog.setMessage("Loading...");
    progressDialog.setCancelable(false);
    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    progressDialog.show();
    gridView = (GridView) view.findViewById(R.id.grid_view);
    return view;
  }

  @Override
  public void render(List<Product> products, final List<ProductViewModel> productViewModels) {
    final ShoppingItemsAdapter shoppingItemsAdapter = new ShoppingItemsAdapter(productViewModels, products, getActivity());
    gridView.setAdapter(shoppingItemsAdapter);

    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Product product = shoppingItemsAdapter.getProductAt(position);
        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        intent.putExtra(PRODUCT_KEY, product);
        startActivity(intent);
      }
    });
  }

  @Override
  public void dismissLoader() {
    progressDialog.dismiss();
  }

  @Override
  public void showErrorDialog(String message) {
    new AlertDialog.Builder(getActivity()).setMessage(message).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        getActivity().finish();
      }
    });
  }
}
