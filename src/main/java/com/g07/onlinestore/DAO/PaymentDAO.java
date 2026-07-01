package com.g07.onlinestore.DAO;

import com.g07.onlinestore.Payment.Payment;
import com.g07.onlinestore.config.db.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentDAO {

  // Generate payment ID
  public String generatePaymentId() {

    return "PAY" + System.currentTimeMillis();

  }

  // Insert Payment
  public void insertPayment(Payment payment, String orderId, String customerId) {

    String insertPaymentSql = "INSERT INTO payments " +
        "(payment_id, order_id, payment_method, amount, transaction_fee, status) " +
        "VALUES (?, ?, ?, ?, ?, ?)";

    String updateOrderSql = "UPDATE orders SET status = ? WHERE customer_id = ?";

    Connection conn = null;

    try {

      conn = DBConnect.getConnection();
      conn.setAutoCommit(false);

      // Insert payment
      PreparedStatement ps = conn.prepareStatement(insertPaymentSql);

      ps.setString(1, payment.getPaymentId());
      ps.setString(2, orderId);
      ps.setString(3, payment.getPaymentMethod().toString());
      ps.setDouble(4, payment.getAmount());
      ps.setDouble(5, payment.getTransactionFee());
      ps.setString(6, payment.getStatus());

      ps.executeUpdate();

      // Update order status
      PreparedStatement ps2 = conn.prepareStatement(updateOrderSql);

      ps2.setString(1, "Success");
      ps2.setString(2, customerId);

      int rows = ps2.executeUpdate();

      if (rows == 0) {
        throw new SQLException("Order not found: " + orderId);
      }

      conn.commit();

      System.out.println(
          "[Payment_" + payment.getPaymentId() + "] Processed Successfully!");
      System.out.println(
          "[Order_" + orderId + "] Status Updated to Success!");

      ps.close();
      ps2.close();

    } catch (SQLException e) {

      try {
        if (conn != null) {
          conn.rollback();
        }
      } catch (SQLException ex) {
        ex.printStackTrace();
      }

      System.out.println("[ERROR] Payment Processing Failed");
      e.printStackTrace();

    } catch (Exception e) {

      try {
        if (conn != null) {
          conn.rollback();
        }
      } catch (SQLException ex) {
        ex.printStackTrace();
      }

      System.out.println("[ERROR] Something Went Wrong");
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

  // Get all payments
  public void getAllPayments() {

    String sql = "SELECT * FROM payments";

    try {

      Connection conn = DBConnect.getConnection();

      PreparedStatement ps = conn.prepareStatement(sql);

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {

        System.out.println(
            "====================");

        System.out.println(
            "Payment ID: "
                + rs.getString("payment_id"));

        System.out.println(
            "Order ID: "
                + rs.getString("order_id"));

        System.out.println(
            "Method: "
                + rs.getString("payment_method"));

        System.out.println(
            "Amount: RM "
                + rs.getDouble("amount"));

        System.out.println(
            "Status: "
                + rs.getString("status"));

      }

    } catch (SQLException e) {

      System.out.println(
          "[ERROR] Retrieve Payment Failed");

      e.printStackTrace();

    } catch (Exception e) {

      System.out.println("[ERROR] Something Wrong?");
    }

  }

  // Update payment status
  public void updatePaymentStatus(
      String paymentId,
      String status) {

    String sql = "UPDATE payments SET status=? " +
        "WHERE payment_id=?";

    try {

      Connection conn = DBConnect.getConnection();

      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(
          1,
          status);

      ps.setString(
          2,
          paymentId);

      ps.executeUpdate();

      System.out.println(
          "Payment Status Updated");

    } catch (SQLException e) {

      System.out.println(
          "[ERROR] Update Payment Failed");

      e.printStackTrace();

    } catch (Exception e) {

      System.out.println("[ERROR] Something Wrong?");
    }

  }

  // Check payment exists
  public boolean paymentExists(
      String paymentId) {

    try {

      Connection conn = DBConnect.getConnection();

      PreparedStatement ps = conn.prepareStatement(
          "SELECT 1 FROM payments WHERE payment_id=?");

      ps.setString(
          1,
          paymentId);

      ResultSet rs = ps.executeQuery();

      return rs.next();

    } catch (Exception e) {

      return false;

    }

  }

}