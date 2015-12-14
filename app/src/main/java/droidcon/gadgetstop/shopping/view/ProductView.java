package droidcon.gadgetstop.shopping.view;

import android.graphics.Bitmap;
import android.widget.ImageView;

public interface ProductView {
  void renderImage(ImageView imageView, Bitmap response);
  void renderProductTitle(String title);
  void renderProductPrice(String price);
  void renderProductPopularity(String popularityLabel, int popularityTextColor, int popularityVisibilityStatus);
}
