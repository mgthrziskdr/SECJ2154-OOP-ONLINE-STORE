package com.g07.onlinestore.DAO;

import com.g07.onlinestore.config.db.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AuthDAO {

  // LOGIN CUSTOMER (DEMO: name + email)
  public boolean loginCustomer(String name, String email) {

    String sql = "SELECT * FROM customers WHERE name=? AND email=?";

    try {

      Connection conn = DBConnect.getConnection();
      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(1, name);
      ps.setString(2, email);

      ResultSet rs = ps.executeQuery();

      return rs.next();

    } catch (Exception e) {

      System.out.println("[ERROR] Customer login failed");
      return false;
    }
  }

  // LOGIN STAFF (DEMO: name + email)
  public boolean loginStaff(String name, String email) {

    String sql = "SELECT * FROM staff WHERE name=? AND email=?";

    try {

      Connection conn = DBConnect.getConnection();
      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(1, name);
      ps.setString(2, email);

      ResultSet rs = ps.executeQuery();

      return rs.next();

    } catch (Exception e) {

      System.out.println("[ERROR] Staff login failed");
      return false;
    }
  }
}