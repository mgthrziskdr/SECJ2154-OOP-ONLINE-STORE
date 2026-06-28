package com.g07.onlinestore;

import com.g07.onlinestore.Store.Store;
import com.g07.onlinestore.Product.*;
import com.g07.onlinestore.Order.Order;
import com.g07.onlinestore.Payment.Payment;
import com.g07.onlinestore.Payment.Payment.PaymentMethod;
import com.g07.onlinestore.DAO.*;
import com.g07.onlinestore.config.db.DBConnect;

import javax.swing.JOptionPane;
import java.sql.Connection;

public class SysMain {

  public static void main(String[] args) {

    DBConnect dbConnect = new DBConnect();
    Connection conn = DBConnect.getConnection();
    dbConnect.DBTestConnect(conn);

    ProductDAO productDAO = new ProductDAO();
    OrderDAO orderDAO = new OrderDAO();
    PaymentDAO paymentDAO = new PaymentDAO();
    AuthDAO authDAO = new AuthDAO();

    try {

      // =========================
      // LOGIN SYSTEM (WITH DAO)
      // =========================
      String username = JOptionPane.showInputDialog("Enter Name:");
      if (username == null) return;

      String email = JOptionPane.showInputDialog("Enter Email:");
      if (email == null) return;

      String role = JOptionPane.showInputDialog(
          "=== ROLE LOGIN ===\n\n1. Customer\n2. Staff\n\nEnter choice:"
      );

      if (role == null) return;

      boolean loginSuccess = false;

      // AUTH CHECK USING DAO
      if (role.equals("1")) {
        loginSuccess = authDAO.loginCustomer(username, email);
      } else if (role.equals("2")) {
        loginSuccess = authDAO.loginStaff(username, email);
      }
      
      if (!loginSuccess) {
        JOptionPane.showMessageDialog(null, "Login Failed! User not found in database.", "Login Failed", JOptionPane.WARNING_MESSAGE);
        return;
      }

      JOptionPane.showMessageDialog(null, "Login Success! Welcome " + username, "Login Success", JOptionPane.INFORMATION_MESSAGE);

      Store store = new Store("My Online Store");

      Product laptop = new Electronic("E001", "ASUS Laptop", 2500, 5, 24, "ASUS");
      Product shirt = new Clothing("C001", "Cotton Shirt", 50, 20, "L", "Cotton");
      Product bread = new Food("F001", "Bread", 5, 50, "2026-01-01", "Bakery");

      store.addProduct(laptop);
      store.addProduct(shirt);
      store.addProduct(bread);

      // =========================================================
      // STAFF MENU
      // =========================================================
      if (role.equals("2")) {

        while (true) {

          String menu = "=== STAFF MENU ===\n\n"
              + "1. Add Product\n"
              + "2. Delete Product\n"
              + "3. Update Product\n"
              + "4. View Products\n"
              + "5. View Orders (DB)\n"
              + "6. Exit";

          String choice = JOptionPane.showInputDialog(menu);
          if (choice == null) break;

          switch (choice) {

            case "1": {
              Product newProduct = new Food(
                  "F002",
                  "Milk",
                  8,
                  30,
                  "2026-01-01",
                  "Drink");

              store.addProduct(newProduct);
              productDAO.insertProduct(newProduct);

              JOptionPane.showMessageDialog(null, "Product Added", "Product Added", JOptionPane.INFORMATION_MESSAGE);
              break;
            }

            case "2": {
              String del = JOptionPane.showInputDialog("Enter Product ID");
              store.getProducts().removeIf(p -> p.getProductId().equals(del));

              JOptionPane.showMessageDialog(null, "Product Deleted", "Product Deleted", JOptionPane.INFORMATION_MESSAGE);
              break;
            }

            case "3": {
              String up = JOptionPane.showInputDialog("Enter Product ID");

              for (int i = 0; i < store.getProducts().size(); i++) {
                Product p = store.getProducts().get(i);

                if (p.getProductId().equals(up)) {

                  String newName = JOptionPane.showInputDialog("New Name");

                  Product updated = new Food(
                      p.getProductId(),
                      newName,
                      p.getPrice(),
                      p.getStockQuantity(),
                      "2026-01-01",
                      "Updated");

                  store.getProducts().set(i, updated);

                  JOptionPane.showMessageDialog(null, "Updated Successfully", "Product Updated", JOptionPane.INFORMATION_MESSAGE);
                  break;
                }
              }
              break;
            }

            case "4": {
              StringBuilder sb = new StringBuilder("=== PRODUCTS ===\n\n");

              for (Product p : store.getProducts()) {
                sb.append(p.getName())
                    .append(" - RM ")
                    .append(p.getPrice())
                    .append("\n");
              }

              JOptionPane.showMessageDialog(null, sb.toString());
              break;
            }

            case "5": {
              orderDAO.getAllOrders();
              JOptionPane.showMessageDialog(null, "Check console for orders");
              break;
            }

            case "6":
              return;

            default:
              JOptionPane.showMessageDialog(null, "Invalid Option");
          }
        }
      }

      // =========================================================
      // CUSTOMER MENU
      // =========================================================
      else if (role.equals("1")) {

        Order order = null;

        while (true) {

          String menu = "=== CUSTOMER MENU ===\n\n"
              + "1. View Products\n"
              + "2. Create Order\n"
              + "3. Make Payment\n"
              + "4. View Order\n"
              + "5. Exit";

          String choice = JOptionPane.showInputDialog(menu);
          
          if (choice == null) {
            break;
          }

          switch (choice) {

            case "1": {
              StringBuilder list = new StringBuilder("=== PRODUCTS ===\n\n");

              for (Product p : store.getProducts()) {
                list.append(p.getName())
                    .append(" - RM ")
                    .append(p.getPrice())
                    .append("\n");
              }

              JOptionPane.showMessageDialog(null, list.toString());
              break;
            }

            case "2": {
              order = new Order();

              Product[] products = { laptop, shirt, bread };

              String input = JOptionPane.showInputDialog(
                  "1 Laptop\n2 Shirt\n3 Bread\nEnter choice:");

              int idx = Integer.parseInt(input) - 1;

              if (idx >= 0 && idx < products.length) {
                order.addItem(products[idx]);
              }

              store.addOrder(order);
              orderDAO.insertOrder(order);

              JOptionPane.showMessageDialog(null, "Order Created");
              break;
            }

            case "3": {
              if (order == null) {
                JOptionPane.showMessageDialog(null, "Create order first!");
                break;
              }

              Payment payment = new Payment(
                  "PAY001",
                  PaymentMethod.FPX_BANKING,
                  order.calculateTotal());

              payment.processPayment();

              store.addPayment(payment);
              paymentDAO.insertPayment(payment, order.getOrderId());

              payment.showPaymentDetails();
              break;
            }

            case "4": {
              if (order == null) {
                JOptionPane.showMessageDialog(null, "No order yet");
              } else {
                JOptionPane.showMessageDialog(null,
                    "Order ID: " + order.getOrderId() +
                        "\nTotal: RM " + order.calculateTotal());
              }
              break;
            }

            case "5":
              return;

            default:
              JOptionPane.showMessageDialog(null, "Invalid Option");
          }
        }
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "[ERROR] " + e.getMessage());
    }
  }
}