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
    String brand
  ) {
    super(
      productId,
      name,
      price,
      stockQuantity
    );

    this.warrantyPeriod = warrantyPeriod;
    this.brand = brand;
  }

  // Calculate price
  @Override
  public double calculatePrice() {
    return getPrice();
  }

  // Product details
  @Override
  public void getProductDetails() {
    System.out.println("Product ID: " + getProductId());
    System.out.println("Name: " + getName());
    System.out.println("Price: RM" + getPrice());
    System.out.println("Stock Quantity: " + getStockQuantity());
    System.out.println("Warranty Period: " + warrantyPeriod + " months");
    System.out.println("Brand: " + brand);
  }

  // Getters
  public int getWarrantyPeriod() {
    return warrantyPeriod;
  }

  public String getBrand() {
    return brand;
  }
}