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

      System.out.println(
          "[Staff_" + staff.getStaffId() + "] Inserted Successfully!");

      insertSubClass(staff);

    } catch (SQLException e) {

      System.out.println(
          "[ERROR] Insert Staff Failed");

      e.printStackTrace();
    }
  }

  // Insert staff subclass
  private void insertSubClass(Staff staff) {

    try {

      Connection conn = DBConnect.getConnection();

      if (staff instanceof Manager) {

        String sql = "INSERT INTO manager " +
            "(staff_id, department, bonus_rate) " +
            "VALUES (?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);

        Manager manager = (Manager) staff;

        ps.setString(
            1,
            manager.getStaffId());

        ps.setString(
            2,
            manager.getDepartment());

        ps.setDouble(
            3,
            manager.getBonusRate());

        ps.executeUpdate();

      } else if (staff instanceof DeliveryStaff) {

        String sql = "INSERT INTO delivery_staff " +
            "(staff_id, vehicle_no, delivery_zone) " +
            "VALUES (?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);

        DeliveryStaff delivery = (DeliveryStaff) staff;

        ps.setString(
            1,
            delivery.getStaffId());

        ps.setString(
            2,
            delivery.getVehicleNo());

        ps.setString(
            3,
            delivery.getDeliveryZone());

        ps.executeUpdate();

      }
    } catch (SQLException e) {

      System.out.println(
          "[ERROR] Insert Staff Details Failed");

      e.printStackTrace();
    }
  }

  // View all staff
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
            "Phone: "
                + rs.getString("phone_number"));

        System.out.println(
            "Salary: RM "
                + rs.getDouble("salary"));

        System.out.println(
            "Type: "
                + rs.getString("staff_type"));

      }
    } catch (SQLException e) {

      System.out.println(
          "[ERROR] Retrieve Staff Failed");

      e.printStackTrace();
    }
  }
}