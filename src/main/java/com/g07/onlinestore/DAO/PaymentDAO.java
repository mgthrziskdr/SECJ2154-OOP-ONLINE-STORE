package com.g07.onlinestore.DAO;

import com.g07.onlinestore.Payment.Payment;
import com.g07.onlinestore.config.db.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentDAO {

  // Insert payment
  public void insertPayment(
      Payment payment,
      String orderId) {

    String sql = "INSERT INTO payments " +
        "(payment_id, order_id, payment_method, amount, transaction_fee, status) " +
        "VALUES (?, ?, ?, ?, ?, ?)";

    try {

      Connection conn = DBConnect.getConnection();

      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(
          1,
          payment.getPaymentId());

      ps.setString(
          2,
          orderId);

      ps.setString(
          3,
          payment.getPaymentMethod().toString());

      ps.setDouble(
          4,
          payment.getAmount());

      ps.setDouble(
          5,
          payment.getTransactionFee());

      ps.setString(
          6,
          payment.getStatus());

      ps.executeUpdate();

      System.out.println(
          "[Payment_"
              + payment.getPaymentId()
              + "] Inserted Successfully!");

    } catch (SQLException e) {

      System.out.println(
          "[ERROR] Insert Payment Failed");

      e.printStackTrace();
    }
  }

  // View all payments
  public void getAllPayments() {

    String sql = "SELECT * FROM payments";

    try {

      Connection conn = DBConnect.getConnection();

      PreparedStatement ps = conn.prepareStatement(sql);

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {

        System.out.println("====================");

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
            "Transaction Fee: RM "
                + rs.getDouble("transaction_fee"));

        System.out.println(
            "Status: "
                + rs.getString("status"));
      }

    } catch (SQLException e) {

      System.out.println(
          "[ERROR] Retrieve Payments Failed");

      e.printStackTrace();
    }
  }

  // Update payment status
  public void updatePaymentStatus(
      String paymentId,
      String status) {

    String sql = "UPDATE payments SET " +
        "status=? " +
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
          "[Payment_"
              + paymentId
              + "] Status Updated!");

    } catch (SQLException e) {

      System.out.println(
          "[ERROR] Update Payment Failed");

      e.printStackTrace();
    }
  }
}