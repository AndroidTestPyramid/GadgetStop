package droidcon.gadgetstop.shopping.view;

import android.graphics.Bitmap;
import android.widget.ImageView;

public interface ProductView {
  void renderImage(ImageView imageView, Bitmap response);
  void renderProductTitle(String title);
  void renderProductCost(String price);
  void renderProductUpcomingDeal(int upcomingDealVisibilityStatus, String upcomingDeal);
  void renderProductPopularity(String popularityLabel, int popularityTextColor, int popularityVisibilityStatus);
}
