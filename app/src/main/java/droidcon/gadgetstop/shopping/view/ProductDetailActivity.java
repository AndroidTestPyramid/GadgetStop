package droidcon.gadgetstop.shopping.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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

import static droidcon.gadgetstop.shopping.view.ProductsBaseFragment.PRODUCT_KEY;

public class ProductDetailActivity extends AppCompatActivity {

  private Product product;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.product_details);
    final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    product = getIntent().getExtras().getParcelable(PRODUCT_KEY);
    renderProductTitle();
    renderProductDescription();
    renderProductCost();
    renderProductImage();
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
    String popularity = null;
    int textColor = 0;
    TextView popularityView = (TextView)findViewById(R.id.popularity);
    if(product.isNew()){
      popularity = getString(R.string.label_new);
      textColor = R.color.red;
    }
    if(product.isPopular()){
      popularity = getString(R.string.popular);
      textColor = R.color.purple;
    }
    if(popularity != null) {
      popularityView.setText(popularity);
      popularityView.setTextColor(getResources().getColor(textColor));
      popularityView.setVisibility(View.VISIBLE);
    }
  }

  private void renderProductDescription() {
    TextView issueDescription = (TextView) findViewById(R.id.product_description);
    issueDescription.setText(product.getDescription());
  }

  private void renderProductTitle() {
    TextView productTitle = (TextView) findViewById(R.id.product_title);
    productTitle.setText(product.getTitle());
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

  private void renderProductCost() {
    TextView priceTextView = (TextView) findViewById(R.id.price);
    priceTextView.setText(String.format("%s%d", getString(R.string.indian_currency), product.getPrice()));
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
