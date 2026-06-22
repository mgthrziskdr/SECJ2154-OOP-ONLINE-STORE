package com.g07.onlinestore;

import com.g07.onlinestore.Store.Store;
import com.g07.onlinestore.Product.Product;
import com.g07.onlinestore.Product.Electronic;
import com.g07.onlinestore.Product.Clothing;
import com.g07.onlinestore.Product.Food;
import com.g07.onlinestore.Order.Order;
import com.g07.onlinestore.Payment.Payment;

// import io.github.cdimascio.dotenv.Dotenv;

public class SysMain {
  public static void main(String[] args) {
    // Dotenv dotenv = Dotenv.load();

    // String message = dotenv.get("SUPABASE_USER");

    // System.out.println(message);

    try {
      // Create Store
      Store store = new Store("My Online Store");

      // Create Products
      Product laptop = new Electronic(
        "E001",
        "ASUS Laptop",
        2500,
        5,
        24,
        "ASUS"
      );

      Product shirt = new Clothing(
        "C001",
        "Cotton Shirt",
        50,
        20,
        "L",
        "Cotton"
      );

      Product bread = new Food(
        "F001",
        "Bread",
        5,
        50,
        "30-06-2026",
        "Bakery"
      );

      // Add products into Store
      store.addProduct(laptop);
      store.addProduct(shirt);
      store.addProduct(bread);

      // Create Order
      Order order = new Order();

      // Add products into order
      order.addItem(laptop);
      order.addItem(shirt);

      // Add order into Store
      store.addOrder(order);

      // Create Payment
      Payment payment = new Payment(
        "PAY001",
        "FPX Banking",
        order.calculateTotal()
      );

      payment.processPayment();

      // Add payment into Store
      store.addPayment(payment);

      // Display Result
      System.out.println("=================");
      System.out.println("STORE: " + store.getStoreName());
      System.out.println("\nPRODUCT LIST");
      System.out.println("=================");

      for (Product product : store.getProducts()) {
        product.getProductDetails();
        System.out.println();
      }

      System.out.println("=================");
      System.out.println("ORDER DETAILS");
      System.out.println("=================");

      order.getOrderDetails();

      System.out.println("\n=================");
      System.out.println("PAYMENT DETAILS");
      System.out.println("=================");

      payment.printPaymentDetails();

    } catch (Exception e) {
      System.out.println("[ERROR] " + e.getMessage());
    }
  }
}