package droidcon.gadgetstop.shopping.cart.model;

import com.orm.SugarRecord;

public class ProductInCart extends SugarRecord<ProductInCart> {
  private int productId;

  public ProductInCart(){}

  public ProductInCart(int productId) {
    this.productId = productId;
  }

  public int getProductId() {
    return productId;
  }
}
