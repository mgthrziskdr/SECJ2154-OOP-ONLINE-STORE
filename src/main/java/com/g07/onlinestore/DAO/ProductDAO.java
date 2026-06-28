package com.g07.onlinestore.DAO;

import com.g07.onlinestore.Product.Product;
import com.g07.onlinestore.Product.Electronic;
import com.g07.onlinestore.Product.Clothing;
import com.g07.onlinestore.Product.Food;
import com.g07.onlinestore.config.db.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class ProductDAO {

  private void insertSB_PopMessage(Product product) {
    System.out.println(
        "[Product_" + product.getProductId() + "] SB Inserted Successfully!");
  }

  // Insert product
  public void insertProduct(Product product) {

    String sql_spclass = "INSERT INTO products " +
        "(product_id, product_name, product_price, product_type, stock_quantity, updated_at) " +
        "VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

    try {
      Connection conn = DBConnect.getConnection();

      // Check if product ID already exists
      String checkSql = "SELECT 1 FROM products WHERE product_id = ?";
      PreparedStatement checkPs = conn.prepareStatement(checkSql);
      checkPs.setString(1, product.getProductId());

      ResultSet rs = checkPs.executeQuery();

      if (rs.next()) {
        System.out.println("Product ID already exists: " + product.getProductId());
        return;
      }

      PreparedStatement ps = conn.prepareStatement(sql_spclass);

      ps.setString(1, product.getProductId());
      ps.setString(2, product.getName());
      ps.setDouble(3, product.getPrice());
      ps.setString(4, product.getClass().getSimpleName());
      ps.setInt(5, product.getStockQuantity());

      ps.executeUpdate();

      System.out.println(
          "[Product_" + product.getProductId() + "] SP Inserted Successfully!");

      try {
        if (product instanceof Electronic) {

          String sql_sbclass_electronic = "INSERT INTO products_electronic " +
              "(product_id, brand, warranty_period) " +
              "VALUES (?, ?, ?)";

          PreparedStatement ps_electronic = conn.prepareStatement(sql_sbclass_electronic);

          Electronic electronic = (Electronic) product;

          ps_electronic.setString(1, electronic.getProductId());
          ps_electronic.setString(2, electronic.getBrand());
          ps_electronic.setInt(3, electronic.getWarrantyPeriod());

          ps_electronic.executeUpdate();

          insertSB_PopMessage(product);

        } else if (product instanceof Clothing) {

          String sql_sbclass_clothing = "INSERT INTO products_clothing " +
              "(product_id, size, material) " +
              "VALUES (?, ?, ?)";

          PreparedStatement ps_clothing = conn.prepareStatement(sql_sbclass_clothing);

          Clothing clothing = (Clothing) product;

          ps_clothing.setString(1, clothing.getProductId());
          ps_clothing.setString(2, clothing.getSize());
          ps_clothing.setString(3, clothing.getMaterial());

          ps_clothing.executeUpdate();

          insertSB_PopMessage(product);

        } else if (product instanceof Food) {

          String sql_sbclass_food = "INSERT INTO products_food " +
              "(product_id, category, expiry_date) " +
              "VALUES (?, ?, ?)";

          PreparedStatement ps_food = conn.prepareStatement(sql_sbclass_food);

          Food food = (Food) product;

          ps_food.setString(1, food.getProductId());
          ps_food.setString(2, food.getCategory());
          ps_food.setDate(3, Date.valueOf(food.getExpiryDate()));

          ps_food.executeUpdate();

          insertSB_PopMessage(product);
        }

      } catch (SQLException e) {
        System.out.println(
            "[Product_" + product.getProductId() + "] SB Product Insert Failed!!!");

        e.printStackTrace();
      }

    } catch (SQLException e) {

      System.out.println(
          "[Product_" + product.getProductId() + "] SP Product Insert Failed!!!");

      e.printStackTrace();
    }
  }

  // Get all products
  public void getAllProducts() {

    String sql = "SELECT * FROM products";

    try {

      Connection conn = DBConnect.getConnection();

      PreparedStatement ps = conn.prepareStatement(sql);

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {

        System.out.println("====================");

        System.out.println(
            "Product ID: " + rs.getString("product_id"));

        System.out.println(
            "Name: " + rs.getString("product_name"));

        System.out.println(
            "Price: RM " + rs.getDouble("product_price"));

        System.out.println(
            "Type: " + rs.getString("product_type"));

        System.out.println(
            "Stock: " + rs.getInt("stock_quantity"));
      }

    } catch (SQLException e) {

      System.out.println("[ERROR] Cannot retrieve products");

      e.printStackTrace();
    }
  }

  // Delete product
  public void deleteProduct(String productId) {

    try {

      Connection conn = DBConnect.getConnection();

      // Delete subclass first

      String deleteElectronic = "DELETE FROM products_electronic WHERE product_id=?";

      PreparedStatement ps1 = conn.prepareStatement(deleteElectronic);

      ps1.setString(1, productId);

      ps1.executeUpdate();

      String deleteClothing = "DELETE FROM products_clothing WHERE product_id=?";

      PreparedStatement ps2 = conn.prepareStatement(deleteClothing);

      ps2.setString(1, productId);

      ps2.executeUpdate();

      String deleteFood = "DELETE FROM products_food WHERE product_id=?";

      PreparedStatement ps3 = conn.prepareStatement(deleteFood);

      ps3.setString(1, productId);

      ps3.executeUpdate();

      String deleteProduct = "DELETE FROM products WHERE product_id=?";

      PreparedStatement ps4 = conn.prepareStatement(deleteProduct);

      ps4.setString(1, productId);

      int result = ps4.executeUpdate();

      if (result > 0) {

        System.out.println(
            "[Product_" + productId + "] Deleted Successfully!");

      } else {

        System.out.println("Product not found");
      }

    } catch (SQLException e) {

      System.out.println("[ERROR] Delete Product Failed");

      e.printStackTrace();
    }
  }

  // Update product
  public void updateProduct(Product product) {

    String sql = "UPDATE products SET " +
        "product_name=?, " +
        "product_price=?, " +
        "stock_quantity=?, " +
        "updated_at=CURRENT_TIMESTAMP " +
        "WHERE product_id=?";

    try {

      Connection conn = DBConnect.getConnection();

      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(1, product.getName());
      ps.setDouble(2, product.getPrice());
      ps.setInt(3, product.getStockQuantity());
      ps.setString(4, product.getProductId());

      ps.executeUpdate();

      System.out.println(
          "[Product_" + product.getProductId() + "] Updated Successfully!");

    } catch (SQLException e) {

      System.out.println("[ERROR] Update Product Failed");

      e.printStackTrace();
    }
  }
}