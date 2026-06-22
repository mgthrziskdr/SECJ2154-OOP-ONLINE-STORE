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
    int stockQuantity
  ) {
    this.productId = productId;
    this.name = name;
    this.price = price;
    this.stockQuantity = stockQuantity;
  }

  // Abstract methods
  public abstract double calculatePrice();
  public abstract void getProductDetails();

  // Getter methods
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

  // Stock update method
  public void reduceStock(int quantity) {
    stockQuantity -= quantity;
  }

}