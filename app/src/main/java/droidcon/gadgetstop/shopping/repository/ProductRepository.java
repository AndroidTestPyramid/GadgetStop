package droidcon.gadgetstop.shopping.repository;

import java.util.List;

import droidcon.gadgetstop.shopping.cart.model.ProductInCart;
import droidcon.gadgetstop.shopping.model.Product;

public class ProductRepository {

  public boolean hasProduct(Product product) {
    List<ProductInCart> productsInCart = ProductInCart.find(ProductInCart.class, "product_id = ?", String.valueOf(product.getProductId()));
    return productsInCart.size() > 0;
  }

  public void save(Product product) {
    new ProductInCart(product.getProductId()).save();
  }

  public long totalCountOfProducts() {
    return ProductInCart.count(ProductInCart.class, null, null);
  }
}
