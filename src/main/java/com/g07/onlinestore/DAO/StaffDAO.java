package com.g07.onlinestore.DAO;

import com.g07.onlinestore.Person.Staff.Staff;
import com.g07.onlinestore.Person.Staff.Manager;
import com.g07.onlinestore.Person.Staff.DeliveryStaff;
import com.g07.onlinestore.config.db.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffDAO {

  // Insert staff
  public void insertStaff(Staff staff) {

    String sql = "INSERT INTO staff " +
        "(staff_id, person_id, name, email, phone_number, salary, staff_type) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?)";

    try {

      Connection conn = DBConnect.getConnection();

      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(
          1,
          staff.getStaffId());

      ps.setString(
          2,
          staff.getPersonId());

      ps.setString(
          3,
          staff.getName());

      ps.setString(
          4,
          staff.getEmail());

      ps.setString(
          5,
          staff.getPhoneNumber());

      ps.setDouble(
          6,
          staff.getSalary());

      ps.setString(
          7,
          staff.getClass().getSimpleName());

      ps.executeUpdate();

      if (staff instanceof Manager) {

        Manager manager = (Manager) staff;

        String sqlManager = "INSERT INTO manager " +
            "(staff_id, department, bonus_rate) " +
            "VALUES (?, ?, ?)";

        PreparedStatement psManager = conn.prepareStatement(sqlManager);

        psManager.setString(
            1,
            manager.getStaffId());

        psManager.setString(
            2,
            manager.getDepartment());

        psManager.setDouble(
            3,
            manager.getBonusRate());

        psManager.executeUpdate();

      } else if (staff instanceof DeliveryStaff) {

        DeliveryStaff delivery = (DeliveryStaff) staff;

        String sqlDelivery = "INSERT INTO delivery_staff " +
            "(staff_id, vehicle_no, delivery_zone) " +
            "VALUES (?, ?, ?)";

        PreparedStatement psDelivery = conn.prepareStatement(sqlDelivery);

        psDelivery.setString(
            1,
            delivery.getStaffId());

        psDelivery.setString(
            2,
            delivery.getVehicleNo());

        psDelivery.setString(
            3,
            delivery.getDeliveryZone());

        psDelivery.executeUpdate();

      }

      System.out.println(
          "[Staff_"
              + staff.getStaffId()
              + "] Inserted Successfully!");

    } catch (SQLException e) {

      System.out.println(
          "[ERROR] Insert Staff Failed");

      e.printStackTrace();

    } catch (Exception e) {

      System.out.println("[ERROR] Something Wrong?");
    }

  }

  // Get all staff
  public void getAllStaff() {

    String sql = "SELECT * FROM staff";

    try {

      Connection conn = DBConnect.getConnection();

      PreparedStatement ps = conn.prepareStatement(sql);

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {

        System.out.println("====================");

        System.out.println(
            "Staff ID: "
                + rs.getString("staff_id"));

        System.out.println(
            "Name: "
                + rs.getString("name"));

        System.out.println(
            "Email: "
                + rs.getString("email"));

        System.out.println(
            "Type: "
                + rs.getString("staff_type"));

        System.out.println(
            "Salary: RM "
                + rs.getDouble("salary"));

      }

    } catch (SQLException e) {

      System.out.println(
          "[ERROR] Retrieve Staff Failed");

      e.printStackTrace();

    } catch (Exception e) {

      System.out.println("[ERROR] Something Wrong?");
    }

  }

  // Get staff ID by name and email
  public String getStaffId(
      String name,
      String email) {

    String sql = "SELECT staff_id FROM staff WHERE name=? AND email=?";

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

        return rs.getString("staff_id");

      }

    } catch (SQLException e) {

      System.out.println(
          "[ERROR] Retrieve Staff ID Failed");

      e.printStackTrace();

    } catch (Exception e) {

      System.out.println("[ERROR] Something Wrong?");
    }

    return null;

  } 

  // Delete staff
  public void deleteStaff(
      String staffId) {

    try {

      Connection conn = DBConnect.getConnection();

      PreparedStatement ps1 = conn.prepareStatement(
          "DELETE FROM manager WHERE staff_id=?");

      ps1.setString(
          1,
          staffId);

      ps1.executeUpdate();

      PreparedStatement ps2 = conn.prepareStatement(
          "DELETE FROM delivery_staff WHERE staff_id=?");

      ps2.setString(
          1,
          staffId);

      ps2.executeUpdate();

      PreparedStatement ps3 = conn.prepareStatement(
          "DELETE FROM staff WHERE staff_id=?");

      ps3.setString(
          1,
          staffId);

      ps3.executeUpdate();

      System.out.println(
          "[Staff_"
              + staffId
              + "] Deleted Successfully!");

    } catch (SQLException e) {

      System.out.println(
          "[ERROR] Delete Staff Failed");

      e.printStackTrace();

    } catch (Exception e) {

      System.out.println("[ERROR] Something Wrong?");
    }

  }

}