package com.g07.onlinestore.Product;

public class Electronic extends Product {

  // Attributes
  private int warrantyPeriod;
  private String brand;

  // Constructor Electronic
  public Electronic(
      String productId,
      String name,
      double price,
      int stockQuantity,
      int warrantyPeriod,
      String brand) {
    super(productId, name, price, stockQuantity);

    this.warrantyPeriod = warrantyPeriod;
    this.brand = brand;
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
    return "=== ELECTRONIC PRODUCT ===\n"
        + "Product ID: " + getProductId() + "\n"
        + "Name: " + getName() + "\n"
        + "Price: RM " + getPrice() + "\n"
        + "Stock Quantity: " + getStockQuantity() + "\n"
        + "Warranty Period: " + warrantyPeriod + " months\n"
        + "Brand: " + brand;
  }

  // ================================
  // GETTERS
  // ================================
  public int getWarrantyPeriod() {
    return warrantyPeriod;
  }

  public String getBrand() {
    return brand;
  }
}