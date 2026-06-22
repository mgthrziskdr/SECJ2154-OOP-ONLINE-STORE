package com.g07.onlinestore.Person;

import com.g07.onlinestore.Order.Order;

import java.util.ArrayList;

public class Customer extends Person {
  // Attributes
  private String customerId;
  private int loyaltyPoints;
  private ArrayList<Order> orderHistory;

  // Constructor Customer
  public Customer(
    String personId,
    String name,
    String email,
    String phoneNumber,
    String customerId
  ) {
    super(
      personId,
      name,
      email,
      phoneNumber
    );

    this.customerId = customerId;
    this.loyaltyPoints = 0;
    this.orderHistory = new ArrayList<>();
  }

  // Add order
  public void addOrder(Order order) {
    orderHistory.add(order);
  }

  // Abstract method implementation
  @Override
  public void getDetails() {
    System.out.println("Customer ID: " + customerId);
    System.out.println("Name: " + getName());
    System.out.println("Email: " + getEmail());
    System.out.println("Phone Number: " + getPhoneNumber());
    System.out.println("Loyalty Points: " + loyaltyPoints);
  }

  @Override
  public String getRoleDuties() {
    return "Purchase products and manage orders";
  }

  // Getters
  public String getCustomerId() {
    return customerId;
  }

  public int getLoyaltyPoints() {
    return loyaltyPoints;
  }

  public ArrayList<Order> getOrderHistory() {
    return orderHistory;
  }

  public int addLoyaltyPoints(int points) {
    loyaltyPoints += points;
    return loyaltyPoints;
  }

}