package com.g07.onlinestore.DAO;

import com.g07.onlinestore.config.db.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AuthDAO {

  // LOGIN SYSTEM
  public String login(
      String username,
      String email) {

    try {

      Connection conn = DBConnect.getConnection();

      // CHECK CUSTOMER
      String customerSQL = "SELECT name FROM customers WHERE name=? AND email=?";

      PreparedStatement customerPS = conn.prepareStatement(customerSQL);

      customerPS.setString(
          1,
          username);

      customerPS.setString(
          2,
          email);

      ResultSet customerRS = customerPS.executeQuery();

      if (customerRS.next()) {

        return "CUSTOMER";

      }

      // CHECK STAFF / MANAGER
      String staffSQL = "SELECT staff_type FROM staff WHERE name=? AND email=?";

      PreparedStatement staffPS = conn.prepareStatement(staffSQL);

      staffPS.setString(
          1,
          username);

      staffPS.setString(
          2,
          email);

      ResultSet staffRS = staffPS.executeQuery();

      if (staffRS.next()) {

        return staffRS.getString("staff_type");

      }

    } catch (Exception e) {

      System.out.println(
          "[ERROR] Login Failed");

      e.printStackTrace();

    }

    return null;
  }
}