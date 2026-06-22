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
    String material
  ) {
    super(
      productId,
      name,
      price,
      stockQuantity
    );

    this.size = size;
    this.material = material;
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
    System.out.println("Size: " + size);
    System.out.println("Material: " + material);
  }

  // Getters
  public String getSize() {
    return size;
  }

  public String getMaterial() {
    return material;
  }
}