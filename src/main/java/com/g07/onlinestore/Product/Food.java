package com.g07.onlinestore.Product;

public class Food extends Product {

  // Attributes
  private String expiryDate;
  private String category;

  // Constructor Food
  public Food(
      String productId,
      String name,
      double price,
      int stockQuantity,
      String expiryDate,
      String category) {
    super(productId, name, price, stockQuantity);

    this.expiryDate = expiryDate;
    this.category = category;
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
    return "=== FOOD PRODUCT ===\n"
        + "Product ID: " + getProductId() + "\n"
        + "Name: " + getName() + "\n"
        + "Category: " + category + "\n"
        + "Price: RM " + getPrice() + "\n"
        + "Expiry Date: " + expiryDate + "\n"
        + "Stock Quantity: " + getStockQuantity();
  }

  // ================================
  // GETTERS
  // ================================
  public String getExpiryDate() {
    return expiryDate;
  }

  public String getCategory() {
    return category;
  }
}