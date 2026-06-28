package com.g07.onlinestore.Product;

public class Clothing extends Product {

  // Attributes
  private String size;
  private String material;

  // Constructor Clothing
  public Clothing(
      String productId,
      String name,
      double price,
      int stockQuantity,
      String size,
      String material) {
    super(productId, name, price, stockQuantity);

    this.size = size;
    this.material = material;
  }

  // ================================
  // CALCULATE PRICE
  // ================================
  @Override
  public double calculatePrice() {
    return getPrice();
  }

  // ================================
  // PRODUCT DETAILS (UI READY)
  // ================================
  @Override
  public String getProductDetails() {
    return "=== CLOTHING PRODUCT ===\n"
        + "Product ID: " + getProductId() + "\n"
        + "Name: " + getName() + "\n"
        + "Price: RM " + getPrice() + "\n"
        + "Stock Quantity: " + getStockQuantity() + "\n"
        + "Size: " + size + "\n"
        + "Material: " + material;
  }

  // ================================
  // GETTERS
  // ================================
  public String getSize() {
    return size;
  }

  public String getMaterial() {
    return material;
  }
}