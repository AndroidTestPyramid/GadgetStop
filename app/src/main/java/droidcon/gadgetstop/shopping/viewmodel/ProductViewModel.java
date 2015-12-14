package droidcon.gadgetstop.shopping.viewmodel;

import android.content.res.Resources;
import android.view.View;

import droidcon.gadgetstop.R;
import droidcon.gadgetstop.shopping.model.Product;

public class ProductViewModel  {
  private final Product product;
  private final Resources resources;
  private final PopularityLabel popularityLabel;

  public ProductViewModel(Product product, Resources resources) {
    this.product = product;
    this.resources = resources;
    popularityLabel = PopularityLabel.createFrom(product);
  }

  public String getTitle() {
    return product.getTitle();
  }

  public String getPrice() {
    return String.format("%s%d", resources.getString(R.string.currency), product.getPrice());
  }


  public String getPopularityLabel() {
    return resources.getString(popularityLabel.getTextId());
  }

  public int getPopularityLabelVisibility() {
    return popularityLabel.getVisibility();
  }

  public String getDescription() {
    return product.getDescription();
  }

  public int getPopularityLabelTextColor() {
    return popularityLabel.getColor();
  }

  private enum PopularityLabel {
    POPULAR(R.string.popular, R.color.purple, View.VISIBLE),
    NEW(R.string.label_new, R.color.red, View.VISIBLE),
    DEFAULT(R.string.empty_string, R.color.white, View.GONE);

    private final int textId;
    private final int color;
    private final int visibility;

    PopularityLabel(int textId, int color, int visibility) {
      this.textId = textId;
      this.color = color;
      this.visibility = visibility;
    }

    public static PopularityLabel createFrom(Product product){
      if (product.isPopular()) {
        return POPULAR;
      }
      if (product.isNew()) {
        return NEW;
      }
      return DEFAULT;
    }

    public int getTextId() {
      return textId;
    }

    public int getColor() {
      return color;
    }

    public int getVisibility() {
      return visibility;
    }
  }
}
