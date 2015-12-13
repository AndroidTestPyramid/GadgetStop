package droidcon.gadgetstop.shopping.view;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import droidcon.gadgetstop.R;
import droidcon.gadgetstop.service.APIClient;
import droidcon.gadgetstop.service.APIClient.RequestType;
import droidcon.gadgetstop.service.ResponseCallback;
import droidcon.gadgetstop.service.ResponseDeserializerFactory;
import droidcon.gadgetstop.shopping.model.Product;
import droidcon.gadgetstop.shopping.util.ImageCache;
import droidcon.gadgetstop.shopping.viewmodel.ProductViewModel;

public class ShoppingItemsListAdapter extends BaseAdapter {
  public List<Product> products = new ArrayList<>();

  public ShoppingItemsListAdapter(List<Product> products) {
    this.products = products;
  }

  @Override
  public int getCount() {
    return products.size();
  }

  @Override
  public Object getItem(int position) {
    return products.get(position);
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }


  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout, parent, false);
    }

    Product product = products.get(position);

    final ProductViewModel productViewModel = new ProductViewModel(product, convertView.getResources());
    renderProductTitle(convertView, productViewModel);
    renderProductImage(convertView, product);
    renderProductCost(convertView, productViewModel);
    renderProductUpcomingDeal(convertView, productViewModel);
    renderProductPopularity(convertView, productViewModel);
    return convertView;
  }

  private void renderProductPopularity(View convertView, ProductViewModel productViewModel) {
    TextView popularityView = (TextView) convertView.findViewById(R.id.popularity);
    popularityView.setText(productViewModel.getPopularityLabel());
    popularityView.setTextColor(convertView.getResources().getColor(productViewModel.getPopularityTextColor()));
    popularityView.setVisibility(productViewModel.getPopularityVisibilityStatus());
  }

  private void renderProductUpcomingDeal(View convertView, ProductViewModel productViewModel) {
    final LinearLayout upcomingDealView = (LinearLayout) convertView.findViewById(R.id.upcoming_deal);
    upcomingDealView.setVisibility(productViewModel.getUpcomingDealVisibilityStatus());

    TextView percentage = (TextView) convertView.findViewById(R.id.percentage);
    percentage.setText(productViewModel.getUpcomingDeal());
  }

  private void renderProductTitle(View convertView, ProductViewModel productViewModel) {
    TextView titleTextView = (TextView) convertView.findViewById(R.id.title);
    titleTextView.setText(productViewModel.getTitle());
  }

  private void renderProductImage(View convertView, Product product) {
    ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
    fetchBitmap(product.getImageUrl(), imageView);
  }

  private void renderProductCost(View convertView, ProductViewModel productViewModel) {
    TextView costTextView = (TextView) convertView.findViewById(R.id.cost);
    costTextView.setText(productViewModel.getPrice());
  }

  private void fetchBitmap(String imageUrl, ImageView imageView) {
    APIClient apiClient = new APIClient();
    final Bitmap image = new ImageCache(imageView.getContext()).getImageFor(imageUrl);
    if (image != null) {
      imageView.setImageBitmap(image);
    } else {
      apiClient.execute(RequestType.GET, imageUrl, bitmapCallback(imageUrl, imageView));
    }
  }

  private ResponseCallback<Bitmap> bitmapCallback(final String imageUrl, final ImageView imageView) {
    return new ResponseCallback<Bitmap>() {
      @Override
      public Bitmap deserialize(InputStream response) {
        return ResponseDeserializerFactory.bitmapDeserializer().deserialize(response);
      }

      @Override
      public void onSuccess(Bitmap response) {
        new ImageCache(imageView.getContext()).save(imageUrl, response);
        imageView.setImageBitmap(response);
      }

      @Override
      public void onError(Exception exception) {

      }
    };
  }
}
