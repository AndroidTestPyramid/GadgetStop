package droidcon.gadgetstop.shopping.builder;

import droidcon.gadgetstop.shopping.model.Product;

public class ProductBuilder {

  private String title = "";
  private String description = "";
  private int price = 0;
  private Boolean isNew = false;
  private Boolean isPopular = false;

  public Product build(){
    int productId = 0;
    String imageUrl = "";
    return new Product(productId, imageUrl, price, title, description, isNew, isPopular);
  }

  public ProductBuilder withTitle(String title){
    this.title = title;
    return this;
  }

  public ProductBuilder withDescription(String description){
    this.description = description;
    return this;
  }

  public ProductBuilder withPrice(int price){
    this.price = price;
    return this;
  }

  public ProductBuilder withIsNew(){
    this.isNew = true;
    return this;
  }

  public ProductBuilder withIsPopular(){
    this.isPopular = true;
    return this;
  }
}

