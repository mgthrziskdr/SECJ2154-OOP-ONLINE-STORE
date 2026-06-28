package com.g07.onlinestore.Product;

public abstract class Product {

  // Attributes
  private String productId;
  private String name;
  private double price;
  private int stockQuantity;

  // Constructor Product
  public Product(
      String productId,
      String name,
      double price,
      int stockQuantity) {
    this.productId = productId;
    this.name = name;
    this.price = price;
    this.stockQuantity = stockQuantity;
  }

  // ================================
  // ABSTRACT METHODS
  // ================================
  public abstract double calculatePrice();

  // NOTE:
  // Replaced void print-style method with String-based method
  // (better for JOptionPane + UI + MVC design)
  public abstract String getProductDetails();

  // ================================
  // GETTERS
  // ================================
  public String getProductId() {
    return productId;
  }

  public String getName() {
    return name;
  }

  public double getPrice() {
    return price;
  }

  public int getStockQuantity() {
    return stockQuantity;
  }

  // ================================
  // STOCK UPDATE METHOD
  // ================================
  public void reduceStock(int quantity) {
    if (quantity > 0 && quantity <= stockQuantity) {
      stockQuantity -= quantity;
    }
  }

  // Optional (GOOD FOR UI + DEBUG + UML)
  public void increaseStock(int quantity) {
    if (quantity > 0) {
      stockQuantity += quantity;
    }
  }

  // ================================
  // UI FRIENDLY SUMMARY (OPTIONAL BUT USEFUL)
  // ================================
  public String getBasicInfo() {
    return "ID: " + productId +
        "\nName: " + name +
        "\nPrice: RM " + price +
        "\nStock: " + stockQuantity;
  }
}