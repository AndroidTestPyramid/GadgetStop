package droidcon.gadgetstop.shopping.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Product implements Parcelable {

  public static final byte BYTE_TRUE = 1;
  public static final byte BYTE_FALSE = 0;
  @SerializedName("image_url")
  private final String imageUrl;
  @SerializedName("product_id")
  private final int productId;
  private final int price;
  private final String title;
  private final String description;
  @SerializedName("upcoming_deal")
  private final int upcomingDeal;
  private final boolean isNew;
  private final boolean isPopular;

  public String getDescription() {
    return description;
  }

  public String getTitle() {
    return title;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public int getProductId() {
    return productId;
  }

  public int getPrice() {
    return price;
  }

  public int getUpcomingDeal() {
    return upcomingDeal;
  }

  public boolean isNew() {
    return isNew;
  }

  public boolean isPopular() {
    return isPopular;
  }

  public boolean anyUpcomingDeal() {
    return upcomingDeal != 0;
  }

  @Override
  public String toString() {
    return title;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(productId);
    dest.writeString(title);
    dest.writeString(description);
    dest.writeString(imageUrl);
    dest.writeInt(price);
    dest.writeInt(upcomingDeal);
    dest.writeByte(isNew ? BYTE_TRUE : BYTE_FALSE);
    dest.writeInt(isPopular ? BYTE_TRUE : BYTE_FALSE);
  }

  private Product(Parcel in) {
    this.productId = in.readInt();
    this.title = in.readString();
    this.description = in.readString();
    this.imageUrl = in.readString();
    this.price = in.readInt();
    this.upcomingDeal = in.readInt();
    this.isNew = in.readByte() == BYTE_TRUE;
    this.isPopular = in.readByte() == BYTE_TRUE;
  }

  public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
    public Product createFromParcel(Parcel in) {
      return new Product(in);
    }

    public Product[] newArray(int size) {
      return new Product[size];
    }
  };
}