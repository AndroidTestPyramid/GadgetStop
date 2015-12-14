package droidcon.gadgetstop.shopping.view;

public interface ProductDetailView extends ProductView {
  void setDescription(String description);
  void showToastWithMessage(String string);
  void showDialogWithMessage(String string);
}
