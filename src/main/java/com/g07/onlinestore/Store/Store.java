package com.g07.onlinestore.Store;

import com.g07.onlinestore.Product.Product;
import com.g07.onlinestore.Order.Order;
import com.g07.onlinestore.Payment.Payment;
import com.g07.onlinestore.Person.Customer;
import com.g07.onlinestore.Person.Staff.Staff;

import java.util.ArrayList;

public class Store {
  private String storeName;

  // Temporary storage
  private ArrayList<Product> products;
  private ArrayList<Order> orders;
  private ArrayList<Customer> customers;
  private ArrayList<Staff> staffList;
  private ArrayList<Payment> payments;

  // Constructor Store
  public Store(String storeName) {
    this.storeName = storeName;
    products = new ArrayList<>();
    orders = new ArrayList<>();
    customers = new ArrayList<>();
    staffList = new ArrayList<>();
    payments = new ArrayList<>();
  }

  // Getter methods
  public String getStoreName() {
    return storeName;
  }

  // Product methods
  public void addProduct(Product product) {
    products.add(product);
  }

  public ArrayList<Product> getProducts() {
    return products;
  }

  // Order methods
  public void addOrder(Order order) {
    orders.add(order);
  }

  public ArrayList<Order> getOrders() {
    return orders;
  }

  // Customer methods
  public void addCustomer(Customer customer) {
    customers.add(customer);
  }

  public ArrayList<Customer> getCustomers() {
    return customers;
  }

  // Staff methods
  public void addStaff(Staff staff) {
    staffList.add(staff);
  }

  public ArrayList<Staff> getStaffList() {
    return staffList;
  }

  // Payment methods
  public void addPayment(Payment payment) {
    payments.add(payment);
  }

  public ArrayList<Payment> getPayments() {
    return payments;
  }
}