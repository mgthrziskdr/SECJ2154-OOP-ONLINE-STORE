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

    String checkSql = "SELECT 1 FROM customers WHERE customer_id=?";

    String sql = "INSERT INTO customers " +
        "(customer_id, person_id, name, email, phone_number, loyalty_points) " +
        "VALUES (?, ?, ?, ?, ?, ?)";

    try {

      Connection conn = DBConnect.getConnection();

      PreparedStatement check = conn.prepareStatement(checkSql);

      check.setString(
          1,
          customer.getCustomerId());

      ResultSet rs = check.executeQuery();

      if (rs.next()) {

        System.out.println(
            "[ERROR] Customer already exists");

        return;
      }

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
              + "] Inserted Successfully");

    } catch (SQLException e) {

      System.out.println(
          "[ERROR] Insert Customer Failed");

      e.printStackTrace();
    }
  }

  // Get customer ID using login information
  public String getCustomerId(
      String name,
      String email) {

    String sql = "SELECT customer_id FROM customers WHERE name=? AND email=?";

    try {

      Connection conn = DBConnect.getConnection();

      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(
          1,
          name);

      ps.setString(
          2,
          email);

      ResultSet rs = ps.executeQuery();

      if (rs.next()) {

        return rs.getString(
            "customer_id");
      }

    } catch (SQLException e) {

      System.out.println(
          "[ERROR] Retrieve Customer ID Failed");

      e.printStackTrace();
    } catch (Exception e) {

      System.out.println("[ERROR] Something Wrong?");
    }

    return null;
  }

  // Find customer by ID
  public Customer getCustomerById(
      String customerId) {

    String sql = "SELECT * FROM customers WHERE customer_id=?";

    try {

      Connection conn = DBConnect.getConnection();

      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(
          1,
          customerId);

      ResultSet rs = ps.executeQuery();

      if (rs.next()) {

        Customer customer = new Customer(
            rs.getString("person_id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("phone_number"),
            rs.getString("customer_id"));

        customer.addLoyaltyPoints(
            rs.getInt("loyalty_points"));

        return customer;
      }

    } catch (SQLException e) {

      System.out.println(
          "[ERROR] Find Customer Failed");

      e.printStackTrace();
    } catch (Exception e) {

      System.out.println("[ERROR] Something Wrong?");
    }

    return null;
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

        System.out.println("====================");

      }

    } catch (SQLException e) {

      System.out.println(
          "[ERROR] Retrieve Customers Failed");

      e.printStackTrace();
    } catch (Exception e) {

      System.out.println("[ERROR] Something Wrong?");
    }
  }

  // Update loyalty points
  public void updateLoyaltyPoints(
      String customerId,
      int points) {

    String sql = "UPDATE customers SET loyalty_points=? WHERE customer_id=?";

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
              + "] Loyalty Updated");

    } catch (SQLException e) {

      System.out.println(
          "[ERROR] Update Loyalty Failed");

      e.printStackTrace();
    } catch (Exception e) {

      System.out.println("[ERROR] Something Wrong?");
    }
  }

}