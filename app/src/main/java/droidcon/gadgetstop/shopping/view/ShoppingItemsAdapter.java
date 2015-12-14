package droidcon.gadgetstop.shopping.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import droidcon.gadgetstop.R;
import droidcon.gadgetstop.service.APIClient;
import droidcon.gadgetstop.shopping.model.Product;
import droidcon.gadgetstop.shopping.presenter.ImagePresenter;
import droidcon.gadgetstop.shopping.presenter.ProductPresenter;
import droidcon.gadgetstop.shopping.repository.ImageRepository;
import droidcon.gadgetstop.shopping.service.ImageFetcher;
import droidcon.gadgetstop.shopping.viewmodel.ProductViewModel;

public class ShoppingItemsAdapter extends BaseAdapter implements ProductView {
  public List<ProductViewModel> productViewModels = new ArrayList<>();
  private final List<Product> products;
  private Context context;
  private ViewHolderItem viewHolder;

  public ShoppingItemsAdapter(List<ProductViewModel> productViewModels, List<Product> products, Context context) {
    this.productViewModels = productViewModels;
    this.products = products;
    this.context = context;
  }

  @Override
  public int getCount() {
    return productViewModels.size();
  }

  @Override
  public Object getItem(int position) {
    return productViewModels.get(position);
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout, parent, false);
      viewHolder = new ViewHolderItem();
      viewHolder.popularityLabelTextView = (TextView) convertView.findViewById(R.id.popularity);
      viewHolder.productTitleTextView = (TextView) convertView.findViewById(R.id.title);
      viewHolder.productPriceTextView = (TextView) convertView.findViewById(R.id.price);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolderItem) convertView.getTag();
    }
    final ImageFetcher imageFetcher = new ImageFetcher(new APIClient());
    final ImageRepository imageRepository = new ImageRepository(context);

    ProductPresenter productPresenter = new ProductPresenter(this, products.get(position), new ImagePresenter(this, imageFetcher, imageRepository), context.getResources());

    productPresenter.renderView();
    productPresenter.renderImageFor((ImageView) convertView.findViewById(R.id.imageView));

    return convertView;
  }

  @Override
  public void renderProductPopularity(String popularityLabel, int popularityTextColor, int popularityVisibilityStatus) {
    final TextView labelTextView = viewHolder.popularityLabelTextView;
    labelTextView.setText(popularityLabel);
    labelTextView.setTextColor(context.getResources().getColor(popularityTextColor));
    labelTextView.setVisibility(popularityVisibilityStatus);
  }

  @Override
  public void renderProductTitle(String title) {
    viewHolder.productTitleTextView.setText(title);
  }

  @Override
  public void renderProductPrice(String price) {
    viewHolder.productPriceTextView.setText(price);
  }

  @Override
  public void renderImage(ImageView imageView, Bitmap response) {
    imageView.setImageBitmap(response);
  }

  public Product getProductAt(int position) {
    return products.get(position);
  }

  static class ViewHolderItem {
    TextView popularityLabelTextView;
    TextView productTitleTextView;
    TextView productPriceTextView;
  }
}
