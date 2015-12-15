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

import droidcon.gadgetstop.R;
import droidcon.gadgetstop.shopping.model.Product;
import droidcon.gadgetstop.shopping.presenter.ProductDetailPresenter;
import droidcon.gadgetstop.shopping.repository.ProductRepository;

import static droidcon.gadgetstop.shopping.view.ProductsBaseFragment.PRODUCT_KEY;

public class ProductDetailActivity extends AppCompatActivity implements ProductDetailView {

  private Product product;
  private ProductDetailPresenter productDetailPresenter;
  private ProductImageViewControl productImageView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.product_details);
    final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    product = getIntent().getExtras().getParcelable(PRODUCT_KEY);

    productDetailPresenter = new ProductDetailPresenter(product, new ProductRepository(), getResources(), this);
    productDetailPresenter.renderDetailedView();

    productImageView = new ProductImageViewControl((ImageView) findViewById(R.id.product_image));
    productImageView.renderImage(product.getImageUrl());
  }

  public void addToCart(View view) {
    productDetailPresenter.saveProduct(product);
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

  @Override
  public void renderProductPopularity(String popularityLabel, int popularityTextColor, int popularityVisibilityStatus) {
    final TextView labelTextView = (TextView) findViewById(R.id.popularity);
    labelTextView.setText(popularityLabel);
    labelTextView.setTextColor(getResources().getColor(popularityTextColor));
    labelTextView.setVisibility(popularityVisibilityStatus);
  }

  @Override
  public void renderProductTitle(String title) {
    final TextView titleTextView = (TextView) findViewById(R.id.product_title);
    titleTextView.setText(title);
  }

  @Override
  public void renderProductPrice(String price) {
    TextView productPriceTextView = (TextView) findViewById(R.id.price);
    productPriceTextView.setText(price);
  }

  @Override
  public void renderDescription(String description) {
    final TextView descriptionTextView = (TextView) findViewById(R.id.product_description);
    descriptionTextView.setText(description);
  }

  @Override
  public void showToastWithMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void showDialogWithMessage(String message) {
    new AlertDialog.Builder(this).setMessage(message).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
      }
    }).show();
  }

}