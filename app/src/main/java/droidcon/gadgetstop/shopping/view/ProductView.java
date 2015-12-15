package droidcon.gadgetstop.shopping.view;

public interface ProductView {
  void renderProductTitle(String title);
  void renderProductPrice(String price);
  void renderProductPopularity(String popularityLabel, int popularityTextColor, int popularityVisibilityStatus);
}
