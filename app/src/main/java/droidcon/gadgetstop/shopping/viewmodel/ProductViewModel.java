package droidcon.gadgetstop.shopping.viewmodel;

import android.content.res.Resources;
import android.view.View;

import droidcon.gadgetstop.R;
import droidcon.gadgetstop.shopping.model.Product;

public class ProductViewModel  {
  private final Product product;
  private final Resources resources;

  public ProductViewModel(Product product, Resources resources) {
    this.product = product;
    this.resources = resources;
  }

  public String getTitle() {
    return product.getTitle();
  }

  public String getPrice() {
    return String.format("%s%d", resources.getString(R.string.indian_currency), product.getPrice());
  }

  public String getUpcomingDeal() {
    if (anyUpcomingDeal())
      return String.format("%d%s", product.getUpcomingDeal(), resources.getString(R.string.percentage_sign));
    return "";
  }

  public int getUpcomingDealVisibilityStatus() {
    if (anyUpcomingDeal())
      return View.VISIBLE;
    return View.GONE;
  }

  public String getPopularityLabel() {
    if (product.isPopular()) {
      return resources.getString(R.string.popular);
    }
    if (product.isNew()) {
      return resources.getString(R.string.label_new);
    }
    return "";
  }

  public int getPopularityLabelVisibility() {
    if (product.isNew() || product.isPopular()) {
      return View.VISIBLE;
    }
    return View.GONE;
  }

  public String getDescription() {
    return product.getDescription();
  }

  public int getPopularityLabelTextColor() {
    if (product.isPopular()) {
      return R.color.purple;
    }
    if (product.isNew()) {
      return R.color.red;
    }
    return R.color.white;
  }

  private boolean anyUpcomingDeal() {
    return product.getUpcomingDeal() != 0;
  }
}
