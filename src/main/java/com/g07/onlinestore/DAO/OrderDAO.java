package com.g07.onlinestore.DAO;

import com.g07.onlinestore.Order.Order;
import com.g07.onlinestore.Product.Product;
import com.g07.onlinestore.config.db.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDAO {

  // Insert order
  public void insertOrder(Order order, String customerId) {

    String orderSql = "INSERT INTO orders " +
        "(order_id, customer_id, order_date, status, total_amount, updated_at) " +
        "VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

    String checkStockSql = "SELECT stock_quantity FROM products WHERE product_id = ?";

    String updateStockSql = "UPDATE products SET stock_quantity = stock_quantity - ? WHERE product_id = ?";

    Connection conn = null;

    try {

      conn = DBConnect.getConnection();
      conn.setAutoCommit(false); // Start transaction

      // Insert order
      PreparedStatement ps = conn.prepareStatement(orderSql);

      ps.setString(1, order.getOrderId());
      ps.setString(2, customerId);
      ps.setDate(3, java.sql.Date.valueOf(order.getOrderDate()));
      ps.setString(4, order.getStatus());
      ps.setDouble(5, order.calculateTotal());

      ps.executeUpdate();

      // Process each order item
      for (Product product : order.getItems()) {

        // Check stock
        PreparedStatement checkStmt = conn.prepareStatement(checkStockSql);

        checkStmt.setString(1, product.getProductId());

        ResultSet rs = checkStmt.executeQuery();

        if (rs.next()) {

          int stock = rs.getInt("stock_quantity");

          if (stock < product.getStockQuantity()) {
            throw new Exception(
                "Insufficient stock for product "
                    + product.getProductId());
          }

          // Deduct stock
          PreparedStatement updateStmt = conn.prepareStatement(updateStockSql);

          updateStmt.setInt(1, 1);
          updateStmt.setString(2, product.getProductId());

          updateStmt.executeUpdate();
        }

        rs.close();
        checkStmt.close();
      }

      // Insert order items
      insertOrderItems(conn, order);

      conn.commit();

      System.out.println("[Order_" + order.getOrderId() + "] Added Successfully!");

    } catch (Exception e) {

      try {
        if (conn != null)
          conn.rollback();
      } catch (SQLException ex) {
        ex.printStackTrace();
      }

      e.printStackTrace();

    } finally {

      try {
        if (conn != null) {
          conn.setAutoCommit(true);
          conn.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  // Insert order items
  private void insertOrderItems(Connection conn, Order order) {

    String sql = "INSERT INTO order_items " +
        "(order_id, product_id, quantity) " +
        "VALUES (?, ?, ?)";

    try {

      PreparedStatement ps = conn.prepareStatement(sql);

      for (Product product : order.getItems()) {

        ps.setString(
            1,
            order.getOrderId());

        ps.setString(
            2,
            product.getProductId());

        ps.setInt(
            3,
            1);

        ps.executeUpdate();

      }

    } catch (SQLException e) {

      System.out.println("[ERROR] Insert Order Items Failed");
      e.printStackTrace();

    } catch (Exception e) {

      System.out.println("[ERROR] Something Wrong?");
    }

  }

  // Staff view all incoming orders
  public String getAllOrders() {

    StringBuilder result = new StringBuilder();

    String sql = "SELECT * FROM orders";

    try {

      Connection conn = DBConnect.getConnection();

      PreparedStatement ps = conn.prepareStatement(sql);

      ResultSet rs = ps.executeQuery();

      boolean found = false;

      while (rs.next()) {

        found = true;

        result.append("====================\n");

        result.append("Order ID: ")
            .append(rs.getString("order_id"))
            .append("\n");

        result.append("Customer ID: ")
            .append(rs.getString("customer_id"))
            .append("\n");

        result.append("Date: ")
            .append(rs.getDate("order_date"))
            .append("\n");

        result.append("Status: ")
            .append(rs.getString("status"))
            .append("\n");

        result.append("Total: RM ")
            .append(rs.getDouble("total_amount"))
            .append("\n");

        result.append(getOrderItems(
            rs.getString("order_id")));

        result.append("\n");

      }

      if (!found) {

        return "No incoming orders found.";

      }

    } catch (SQLException e) {

      return "[ERROR] Retrieve Orders Failed";

    } catch (Exception e) {

      return "[ERROR] Something Wrong?";
    }

    return result.toString();

  }

  // Get order items
  private String getOrderItems(String orderId) {

    StringBuilder result = new StringBuilder();

    String sql = "SELECT products.product_name, order_items.quantity " +
        "FROM order_items " +
        "JOIN products " +
        "ON order_items.product_id = products.product_id " +
        "WHERE order_items.order_id=?";

    try {

      Connection conn = DBConnect.getConnection();

      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(
          1,
          orderId);

      ResultSet rs = ps.executeQuery();

      result.append("Items:\n");

      while (rs.next()) {

        result.append("- ")
            .append(rs.getString("product_name"))
            .append(" x")
            .append(rs.getInt("quantity"))
            .append("\n");

      }

    } catch (SQLException e) {

      result.append("[ERROR] Retrieve Items Failed");

      return "[ERROR] Retrieve Items Failed";

    } catch (Exception e) {

      return "[ERROR] Something Wrong?";
    }

    return result.toString();

  }

  public double getCustomerTotalSpent(String customerId) {

    double total = 0;

    // String sql = "SELECT SUM(products.product_price * order_items.quantity) AS
    // total " +
    // "FROM orders " +
    // "JOIN order_items " +
    // "ON orders.order_id = order_items.order_id " +
    // "JOIN products " +
    // "ON order_items.product_id = products.product_id " +
    // "WHERE orders.customer_id = ? " +
    // "AND orders.status = ?";

    String sql = "SELECT SUM(total_amount) AS total " +
        "FROM orders " +
        "WHERE customer_id = ? " +
        "AND status = ?";

    try {

      Connection conn = DBConnect.getConnection();

      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(1, customerId);
      ps.setString(2, "Pending");

      ResultSet rs = ps.executeQuery();

      if (rs.next()) {

        total = rs.getDouble("total");

      }

    } catch (SQLException e) {

      e.printStackTrace();

    }

    return total;

  }

  public String getLatestPendingOrderId(String customerId) {

    String sql = "SELECT order_id " +
        "FROM orders " +
        "WHERE customer_id = ? AND status = 'Pending' " +
        "ORDER BY created_at DESC " +
        "LIMIT 1";

    try {

      Connection conn = DBConnect.getConnection();

      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(1, customerId);

      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        return rs.getString("order_id");
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null;
  }

  // Update order status
  public void updateOrderStatus(
      String orderId,
      String status) {

    String sql = "UPDATE orders SET status=?, updated_at=CURRENT_TIMESTAMP WHERE order_id=?";

    try {

      Connection conn = DBConnect.getConnection();

      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(
          1,
          status);

      ps.setString(
          2,
          orderId);

      ps.executeUpdate();

      System.out.println(
          "[Order_" + orderId + "] Status Updated!");

    } catch (SQLException e) {

      System.out.println("[ERROR] Update Order Failed");

      e.printStackTrace();

    } catch (Exception e) {

      System.out.println("[ERROR] Something Wrong?");
    }

  }

  // Customer view order history
  public String getCustomerOrderHistory(String customerId) {

    StringBuilder result = new StringBuilder();

    String sql = "SELECT * FROM orders WHERE customer_id=?";

    try {

      Connection conn = DBConnect.getConnection();

      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(
          1,
          customerId);

      ResultSet rs = ps.executeQuery();

      boolean found = false;

      while (rs.next()) {

        found = true;

        result.append("====================\n");

        result.append("Order ID: ")
            .append(rs.getString("order_id"))
            .append("\n");

        result.append("Date: ")
            .append(rs.getDate("order_date"))
            .append("\n");

        result.append("Status: ")
            .append(rs.getString("status"))
            .append("\n");

        result.append("Total: RM ")
            .append(rs.getDouble("total_amount"))
            .append("\n");

        result.append(
            getOrderItems(
                rs.getString("order_id")));

        result.append("\n");

      }

      if (!found) {

        return "No previous orders found.";

      }

    } catch (SQLException e) {

      return "[ERROR] Retrieve Customer History Failed";

    } catch (Exception e) {

      return "[ERROR] Something Wrong?";
    }

    return result.toString();

  }

}