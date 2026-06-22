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
    String category
  ) {
    super(
      productId,
      name,
      price,
      stockQuantity
    );

    this.expiryDate = expiryDate;
    this.category = category;
  }

  // Calculate price
  @Override
  public double calculatePrice() {
    return getPrice();
  }

  // Product details
  @Override
  public void getProductDetails() {
    System.out.println("Food");
    System.out.println("Name: " + getName());
    System.out.println("Category: " + category);
    System.out.println("Price: RM " + getPrice());
    System.out.println("Expiry Date: " + expiryDate);
    System.out.println("Stock Quantity: " + getStockQuantity());
  }

  // Getters
  public String getExpiryDate() {
    return expiryDate;
  }

  public String getCategory() {
    return category;
  }
}