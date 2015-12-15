package droidcon.gadgetstop.shopping.view;

public interface ProductDetailView {
  void renderProductTitle(String title);
  void renderProductPrice(String price);
  void renderProductPopularity(String popularityLabel, int popularityTextColor, int popularityVisibilityStatus);
  void renderDescription(String description);
  void showToastWithMessage(String string);
  void showDialogWithMessage(String string);
}
