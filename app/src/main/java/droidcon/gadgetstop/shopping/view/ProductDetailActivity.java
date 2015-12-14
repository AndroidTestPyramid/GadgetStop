package droidcon.gadgetstop.shopping.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.List;

import droidcon.gadgetstop.R;
import droidcon.gadgetstop.service.APIClient;
import droidcon.gadgetstop.service.APIClient.RequestType;
import droidcon.gadgetstop.service.ResponseCallback;
import droidcon.gadgetstop.service.ResponseDeserializerFactory;
import droidcon.gadgetstop.shopping.cart.model.ProductInCart;
import droidcon.gadgetstop.shopping.model.Product;
import droidcon.gadgetstop.shopping.util.ImageCache;
import droidcon.gadgetstop.shopping.viewmodel.ProductViewModel;

import static droidcon.gadgetstop.shopping.view.ProductsBaseFragment.PRODUCT_KEY;

public class ProductDetailActivity extends AppCompatActivity {

  private Product product;
  private ProductViewModel productViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.product_details);
    final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    product = getIntent().getExtras().getParcelable(PRODUCT_KEY);
    productViewModel = new ProductViewModel(product, getResources());
    renderProductTitle();
    renderProductDescription();
    renderProductPrice();
    renderProductImage();
    renderProductUpcomingDeal();
    renderProductPopularity();
  }

  public void addToCart(View view) {
    List<ProductInCart> productsInCart = ProductInCart.find(ProductInCart.class, "product_id=?", String.valueOf(product.getProductId()));
    if (productsInCart.size() > 0) {
      showDialogWithMessage(R.string.already_added_to_cart);
    } else {
      ProductInCart productInCart = new ProductInCart(product.getProductId());
      productInCart.save();
      showToastWithMessage(R.string.addedToCart);
    }
  }

  private void showToastWithMessage(int message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  private void showDialogWithMessage(int message) {
    new AlertDialog.Builder(this).setMessage(message).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
      }
    }).show();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void renderProductPopularity() {
    TextView popularityView = (TextView) findViewById(R.id.popularity);
    popularityView.setText(productViewModel.getPopularityLabel());
    popularityView.setTextColor(getResources().getColor(productViewModel.getPopularityLabelTextColor()));
    popularityView.setVisibility(productViewModel.getPopularityLabelVisibility());
  }

  private void renderProductDescription() {
    TextView issueDescription = (TextView) findViewById(R.id.product_description);
    issueDescription.setText(productViewModel.getDescription());
  }

  private void renderProductTitle() {
    TextView titleTextView = (TextView) findViewById(R.id.product_title);
    titleTextView.setText(productViewModel.getTitle());
  }

  private void renderProductImage() {
    APIClient apiClient = new APIClient();
    final ImageView imageView = (ImageView) findViewById(R.id.product_image);
    final Bitmap image = new ImageCache(this).getImageFor(product.getImageUrl());
    if (image != null) {
      imageView.setImageBitmap(image);
    } else {
      apiClient.execute(RequestType.GET, product.getImageUrl(), bitmapCallback(imageView));
    }
  }

  private void renderProductPrice() {
    TextView priceTextView = (TextView) findViewById(R.id.price);
    priceTextView.setText(productViewModel.getPrice());
  }

  private void renderProductUpcomingDeal() {
    final LinearLayout upcomingDealView = (LinearLayout) findViewById(R.id.upcoming_deal);
    upcomingDealView.setVisibility(productViewModel.getUpcomingDealVisibilityStatus());

    TextView percentage = (TextView) findViewById(R.id.percentage);
    percentage.setText(productViewModel.getUpcomingDeal());
  }

  private ResponseCallback<Bitmap> bitmapCallback(final ImageView imageView) {
    return new ResponseCallback<Bitmap>() {
      @Override
      public Bitmap deserialize(InputStream response) {
        return ResponseDeserializerFactory.bitmapDeserializer().deserialize(response);
      }

      @Override
      public void onSuccess(Bitmap response) {
        new ImageCache(ProductDetailActivity.this).save(product.getImageUrl(), response);
        imageView.setImageBitmap(response);
      }

      @Override
      public void onError(Exception exception) {
      }
    };
  }
}
