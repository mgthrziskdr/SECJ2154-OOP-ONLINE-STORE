package com.g07.onlinestore.Store;

import com.g07.onlinestore.Product.Product;
import com.g07.onlinestore.Order.Order;
import com.g07.onlinestore.Payment.Payment;
import com.g07.onlinestore.Person.Customer;
import com.g07.onlinestore.Person.Staff.Staff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

  // ================================
  // Product methods
  // ================================
  public void addProduct(Product product) {
    if (product != null) {
      products.add(product);
    }
  }

  public void removeProduct(Product product) {
    products.remove(product);
  }

  public void updateProduct(Product oldProduct, Product newProduct) {
    int index = products.indexOf(oldProduct);

    if(index != -1) {
      products.set(index, newProduct);
    }
  }

  public List<Product> getProducts() {
    return Collections.unmodifiableList(products);
  }

  // ================================
  // Order methods
  // ================================
  public void addOrder(Order order) {
    if (order != null) {
      orders.add(order);
    }
  }

  public List<Order> getOrders() {
    return Collections.unmodifiableList(orders);
  }

  // ================================
  // Customer methods
  // ================================
  public void addCustomer(Customer customer) {
    if (customer != null) {
      customers.add(customer);
    }
  }

  public List<Customer> getCustomers() {
    return Collections.unmodifiableList(customers);
  }

  // ================================
  // Staff methods
  // ================================
  public void addStaff(Staff staff) {
    if (staff != null) {
      staffList.add(staff);
    }
  }

  public List<Staff> getStaffList() {
    return Collections.unmodifiableList(staffList);
  }

  // ================================
  // Payment methods
  // ================================
  public void addPayment(Payment payment) {
    if (payment != null) {
      payments.add(payment);
    }
  }

  public List<Payment> getPayments() {
    return Collections.unmodifiableList(payments);
  }
}