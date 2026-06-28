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
  public void insertOrder(Order order) {

    String sql = "INSERT INTO orders " +
        "(order_id, order_date, status, total_amount, updated_at) " +
        "VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)";

    try {

      Connection conn = DBConnect.getConnection();

      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(
          1,
          order.getOrderId());

      ps.setDate(
          2,
          java.sql.Date.valueOf(order.getOrderDate()));

      ps.setString(
          3,
          order.getStatus());

      ps.setDouble(
          4,
          order.calculateTotal());

      ps.executeUpdate();

      System.out.println(
          "[Order_" + order.getOrderId() + "] Inserted Successfully!");

      insertOrderItems(order);

    } catch (SQLException e) {

      System.out.println(
          "[ERROR] Insert Order Failed");

      e.printStackTrace();
    }
  }

  // Insert order items
  private void insertOrderItems(Order order) {

    String sql = "INSERT INTO order_items " +
        "(order_id, product_id, quantity) " +
        "VALUES (?, ?, ?)";

    try {

      Connection conn = DBConnect.getConnection();

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

      System.out.println(
          "[ERROR] Insert Order Items Failed");

      e.printStackTrace();
    }
  }

  // View incoming orders
  public void getAllOrders() {

    String sql = "SELECT * FROM orders";

    try {

      Connection conn = DBConnect.getConnection();

      PreparedStatement ps = conn.prepareStatement(sql);

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {

        System.out.println("====================");

        System.out.println(
            "Order ID: "
                + rs.getString("order_id"));

        System.out.println(
            "Date: "
                + rs.getDate("order_date"));

        System.out.println(
            "Status: "
                + rs.getString("status"));

        System.out.println(
            "Total: RM "
                + rs.getDouble("total_amount"));

        getOrderItems(
            rs.getString("order_id"));
      }

    } catch (SQLException e) {

      System.out.println(
          "[ERROR] Retrieve Orders Failed");

      e.printStackTrace();
    }
  }

  // View order items
  private void getOrderItems(String orderId) {

    String sql = "SELECT order_items.product_id, " +
        "products.product_name, " +
        "order_items.quantity " +
        "FROM order_items " +
        "JOIN products " +
        "ON order_items.product_id = products.product_id " +
        "WHERE order_id=?";

    try {

      Connection conn = DBConnect.getConnection();

      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(
          1,
          orderId);

      ResultSet rs = ps.executeQuery();

      System.out.println("Items:");

      while (rs.next()) {

        System.out.println(
            "- "
                + rs.getString("product_name")
                + " x"
                + rs.getInt("quantity"));
      }

    } catch (SQLException e) {

      System.out.println(
          "[ERROR] Retrieve Order Items Failed");

      e.printStackTrace();
    }
  }

  // Update order status
  public void updateOrderStatus(
      String orderId,
      String status) {

    String sql = "UPDATE orders SET " +
        "status=?, " +
        "updated_at=CURRENT_TIMESTAMP " +
        "WHERE order_id=?";

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
          "[Order_"
              + orderId
              + "] Status Updated!");

    } catch (SQLException e) {

      System.out.println(
          "[ERROR] Update Order Failed");

      e.printStackTrace();
    }
  }
} 