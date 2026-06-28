package com.g07.onlinestore.Order;

import com.g07.onlinestore.Product.Product;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Random;

public class Order {

  // Attributes
  private String orderId;
  private String orderDate;
  private String status;
  private ArrayList<Product> items;
  private double totalAmount;

  // Generate random order ID
  public static String createRandomOrder() {
    Random random = new Random();
    return "ORD" + (1000 + random.nextInt(9000));
  }

  // Generate random order date
  public static String createRandomDate() {
    return LocalDate.now().toString();
  }

  // Constructor Order
  public Order() {
    this.orderId = createRandomOrder();
    this.orderDate = createRandomDate();
    this.status = "Pending";
    this.items = new ArrayList<>();
    this.totalAmount = 0.0;
  }

  // ================================
  // Add product into order
  // ================================
  public void addItem(Product product) {
    if (product != null) {
      items.add(product);
    }
  }

  // ================================
  // Calculate total price
  // ================================
  public double calculateTotal() {
    totalAmount = 0;

    for (Product product : items) {
      totalAmount += product.calculatePrice();
    }

    return totalAmount;
  }

  // ================================
  // UI-FRIENDLY ORDER SUMMARY
  // (REPLACES System.out.println)
  // ================================
  public String getOrderSummary() {

    StringBuilder sb = new StringBuilder();

    sb.append("=== ORDER DETAILS ===\n");
    sb.append("Order ID: ").append(orderId).append("\n");
    sb.append("Date: ").append(orderDate).append("\n");
    sb.append("Status: ").append(status).append("\n\n");

    sb.append("Items:\n");

    if (items.isEmpty()) {
      sb.append("No items in order\n");
    } else {
      for (Product product : items) {
        sb.append("- ")
            .append(product.getName())
            .append(" (RM ")
            .append(product.getPrice())
            .append(")\n");
      }
    }

    sb.append("\nTotal: RM ").append(calculateTotal());

    return sb.toString();
  }

  // ================================
  // GETTERS
  // ================================
  public String getOrderId() {
    return orderId;
  }

  public String getOrderDate() {
    return orderDate;
  }

  public String getStatus() {
    return status;
  }

  public ArrayList<Product> getItems() {
    return items;
  }

  public double getTotalAmount() {
    return totalAmount;
  }

  // ================================
  // UPDATE STATUS
  // ================================
  public void setStatus(String status) {
    if (status != null) {
      this.status = status;
    }
  }
}