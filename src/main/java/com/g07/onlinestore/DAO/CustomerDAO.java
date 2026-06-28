package com.g07.onlinestore.DAO;

import com.g07.onlinestore.Person.Customer;
import com.g07.onlinestore.config.db.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {

  // Insert customer
  public void insertCustomer(Customer customer) {

    String sql = "INSERT INTO customers " +
        "(customer_id, person_id, name, email, phone_number, loyalty_points) " +
        "VALUES (?, ?, ?, ?, ?, ?)";

    try {

      Connection conn = DBConnect.getConnection();

      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(
          1,
          customer.getCustomerId());

      ps.setString(
          2,
          customer.getPersonId());

      ps.setString(
          3,
          customer.getName());

      ps.setString(
          4,
          customer.getEmail());

      ps.setString(
          5,
          customer.getPhoneNumber());

      ps.setInt(
          6,
          customer.getLoyaltyPoints());

      ps.executeUpdate();

      System.out.println(
          "[Customer_"
              + customer.getCustomerId()
              + "] Inserted Successfully!");

    } catch (SQLException e) {

      System.out.println(
          "[ERROR] Insert Customer Failed");

      e.printStackTrace();
    }
  }

  // View all customers
  public void getAllCustomers() {

    String sql = "SELECT * FROM customers";

    try {

      Connection conn = DBConnect.getConnection();

      PreparedStatement ps = conn.prepareStatement(sql);

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {

        System.out.println("====================");

        System.out.println(
            "Customer ID: "
                + rs.getString("customer_id"));

        System.out.println(
            "Name: "
                + rs.getString("name"));

        System.out.println(
            "Email: "
                + rs.getString("email"));

        System.out.println(
            "Phone: "
                + rs.getString("phone_number"));

        System.out.println(
            "Loyalty Points: "
                + rs.getInt("loyalty_points"));
      }

    } catch (SQLException e) {

      System.out.println(
          "[ERROR] Retrieve Customers Failed");

      e.printStackTrace();
    }
  }

  // Find customer
  public Customer getCustomerById(String customerId) {

    String sql = "SELECT * FROM customers WHERE customer_id=?";

    try {

      Connection conn = DBConnect.getConnection();

      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(
          1,
          customerId);

      ResultSet rs = ps.executeQuery();

      if (rs.next()) {

        return new Customer(
            rs.getString("person_id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("phone_number"),
            rs.getString("customer_id"));
      }

    } catch (SQLException e) {

      System.out.println(
          "[ERROR] Find Customer Failed");

      e.printStackTrace();
    }

    return null;
  }

  // Update loyalty points
  public void updateLoyaltyPoints(
      String customerId,
      int points) {

    String sql = "UPDATE customers SET " +
        "loyalty_points=? " +
        "WHERE customer_id=?";

    try {

      Connection conn = DBConnect.getConnection();

      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setInt(
          1,
          points);

      ps.setString(
          2,
          customerId);

      ps.executeUpdate();

      System.out.println(
          "[Customer_"
              + customerId
              + "] Loyalty Points Updated!");

    } catch (SQLException e) {

      System.out.println(
          "[ERROR] Update Loyalty Points Failed");

      e.printStackTrace();
    }
  }
}