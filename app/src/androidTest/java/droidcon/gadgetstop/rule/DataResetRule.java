package droidcon.gadgetstop.rule;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import droidcon.gadgetstop.shopping.cart.model.ProductInCart;

public class DataResetRule implements TestRule {

  @Override
  public Statement apply(final Statement base, Description description) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        try {
          ProductInCart.deleteAll(ProductInCart.class);
          base.evaluate();
        } finally {
          ProductInCart.deleteAll(ProductInCart.class);
        }
      }
    };
  }
}
