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

    String orderTempId = "ORD" + (1000 + random.nextInt(9000));

    return orderTempId;
  }

  // Generate random order date
  public static String createRandomDate() {
    LocalDate orderDate = LocalDate.now();
    return orderDate.toString();
  }

  // Constructor Order
  public Order() {
    this.orderId = createRandomOrder();
    this.orderDate = createRandomDate();
    this.status = "Pending";
    this.items = new ArrayList<>();
    this.totalAmount = 0.0;
  }

  // Add product into order
  public void addItem(Product product) {
    items.add(product);
  }

  // Calculate total price
  public double calculateTotal() {
    totalAmount = 0;

    for (Product product : items) {
      totalAmount += product.calculatePrice();
    }

    return totalAmount;
  }

  // Order details
  public void getOrderDetails() {
    System.out.println("Order ID: " + orderId);
    System.out.println("Date: " + orderDate);
    System.out.println("Status: " + status);
    System.out.println("Total: RM " + calculateTotal());
  }

  // Getter methods
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

  // Update status
  public void setStatus(String status) {
    this.status = status;
  }
}