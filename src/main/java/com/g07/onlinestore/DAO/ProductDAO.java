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

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductDAO {

  // Insert Product
  public void insertProduct(Product product) {

    Connection conn = null;

    try {

      conn = DBConnect.getConnection();

      conn.setAutoCommit(false);

      String sql = "INSERT INTO products " +
          "(product_id, product_name, product_price, product_type, stock_quantity) " +
          "VALUES (?, ?, ?, ?, ?)";

      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(
          1,
          product.getProductId());

      ps.setString(
          2,
          product.getName());

      ps.setDouble(
          3,
          product.getPrice());

      ps.setString(
          4,
          product.getClass().getSimpleName());

      ps.setInt(
          5,
          product.getStockQuantity());

      ps.executeUpdate();

      insertSubclass(conn, product);

      conn.commit();

      System.out.println(
          "[Product_" +
              product.getProductId() +
              "] Added Successfully!");

    } catch (Exception e) {

      try {

        if (conn != null)
          conn.rollback();

      } catch (SQLException ex) {
      } catch (Exception ex) {

        System.out.println("[ERROR] Something Wrong?");
      }

      System.out.println("[ERROR] Insert Product Failed");
      e.printStackTrace();

    }
  }

  // Insert subclass table
  private void insertSubclass(
      Connection conn,
      Product product)
      throws SQLException {

    if (product instanceof Electronic) {

      Electronic e = (Electronic) product;

      String sql = "INSERT INTO products_electronic " +
          "(product_id, brand, warranty_period) " +
          "VALUES (?, ?, ?)";

      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(
          1,
          e.getProductId());

      ps.setString(
          2,
          e.getBrand());

      ps.setInt(
          3,
          e.getWarrantyPeriod());

      ps.executeUpdate();

    }

    else if (product instanceof Clothing) {

      Clothing c = (Clothing) product;

      String sql = "INSERT INTO products_clothing " +
          "(product_id, size, material) " +
          "VALUES (?, ?, ?)";

      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(
          1,
          c.getProductId());

      ps.setString(
          2,
          c.getSize());

      ps.setString(
          3,
          c.getMaterial());

      ps.executeUpdate();

    }

    else if (product instanceof Food) {

      Food f = (Food) product;

      String sql = "INSERT INTO products_food " +
          "(product_id, category, expiry_date) " +
          "VALUES (?, ?, ?)";

      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(
          1,
          f.getProductId());

      ps.setString(
          2,
          f.getCategory());

      ps.setDate(
          3,
          Date.valueOf(f.getExpiryDate()));

      ps.executeUpdate();

    }

  }

  // Display product list for JOptionPane
  public String getListOfProducts() {

    StringBuilder result = new StringBuilder();

    // Force two decimal places (rounds up/down)
    DecimalFormat dfZeros = new DecimalFormat("0.00");

    String sql = "SELECT * FROM products";

    try {

      Connection conn = DBConnect.getConnection();

      PreparedStatement ps = conn.prepareStatement(sql);

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        String price = dfZeros.format(rs.getDouble("product_price"));

        result.append(
            rs.getString("product_id"))
            .append(" - ")
            .append(
                rs.getString("product_name"))
            .append(" (RM ")
            .append(price)
            .append(")\n");

      }

    } catch (SQLException e) {

      e.printStackTrace();

    } catch (Exception e) {

      System.out.println("[ERROR] Something Wrong?");
    }

    return result.toString();

  }

  public String[] getListOfProductId() {
    String sql = "SELECT product_id FROM products";

    ArrayList<String> productList = new ArrayList<>();

    try {
      Connection conn = DBConnect.getConnection();
      PreparedStatement ps = conn.prepareStatement(sql);
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        productList.add(rs.getString("product_id"));
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return productList.toArray(new String[0]);
  }

  public String getProductType(String id) {

    String sql = "SELECT product_type FROM products WHERE product_id=?";

    try {

      Connection conn = DBConnect.getConnection();

      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(1, id);

      ResultSet rs = ps.executeQuery();

      if (rs.next()) {

        return rs.getString("product_type");

      }

    } catch (SQLException e) {

      e.printStackTrace();

    } catch (Exception e) {

      System.out.println("[ERROR] Something Wrong?");
    }

    return null;

  }

  public Product getProduct(String productId) {

    String sql = "SELECT * FROM products WHERE product_id=?";

    try {

      Connection conn = DBConnect.getConnection();

      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(1, productId);

      ResultSet rs = ps.executeQuery();

      if (rs.next()) {

        return new Electronic(
            rs.getString("product_id"),
            rs.getString("product_name"),
            rs.getDouble("product_price"),
            rs.getInt("stock_quantity"),
            0,
            "");

      }

    } catch (SQLException e) {

      e.printStackTrace();

    }

    return null;
  }

  // Delete Product
  public void deleteProduct(String productId) {

    try {

      Connection conn = DBConnect.getConnection();

      String[] tables = {

          "order_items",
          "products_electronic",
          "products_clothing",
          "products_food",
          "products"

      };

      for (String table : tables) {

        PreparedStatement ps = conn.prepareStatement(
            "DELETE FROM "
                + table +
                " WHERE product_id=?");

        ps.setString(
            1,
            productId);

        ps.executeUpdate();

      }

      System.out.println(
          "[Product_" + productId + "] Deleted Successfully!");

    } catch (SQLException e) {

      System.out.println(
          "[ERROR] Delete Failed");

      e.printStackTrace();

    } catch (Exception e) {

      System.out.println("[ERROR] Something Wrong?");
    }

  }

  // Update Product
  public void updateProduct(Product product) {

    String sql = "UPDATE products SET "
        + "product_name=?, "
        + "product_price=?, "
        + "stock_quantity=?, "
        + "updated_at=CURRENT_TIMESTAMP "
        + "WHERE product_id=?";

    try {

      Connection conn = DBConnect.getConnection();

      // Update products table
      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(1, product.getName());
      ps.setDouble(2, product.getPrice());
      ps.setInt(3, product.getStockQuantity());
      ps.setString(4, product.getProductId());

      ps.executeUpdate();

      // Update subclass table
      if (product instanceof Electronic) {

        Electronic e = (Electronic) product;

        String electronicSql = "UPDATE products_electronic "
            + "SET brand=?, warranty_period=? "
            + "WHERE product_id=?";

        PreparedStatement eps = conn.prepareStatement(electronicSql);

        eps.setString(1, e.getBrand());
        eps.setInt(2, e.getWarrantyPeriod());
        eps.setString(3, e.getProductId());

        eps.executeUpdate();

        eps.close();

      } else if (product instanceof Clothing) {

        Clothing c = (Clothing) product;

        String clothingSql = "UPDATE products_clothing "
            + "SET size=?, material=? "
            + "WHERE product_id=?";

        PreparedStatement cps = conn.prepareStatement(clothingSql);

        cps.setString(1, c.getSize());
        cps.setString(2, c.getMaterial());
        cps.setString(3, c.getProductId());

        cps.executeUpdate();

        cps.close();

      } else if (product instanceof Food) {

        Food f = (Food) product;

        String foodSql = "UPDATE products_food "
            + "SET category=?, expiry_date=? "
            + "WHERE product_id=?";

        PreparedStatement fps = conn.prepareStatement(foodSql);

        fps.setString(1, f.getCategory());
        fps.setDate(2, java.sql.Date.valueOf(f.getExpiryDate()));
        fps.setString(3, f.getProductId());

        fps.executeUpdate();

        fps.close();
      }

      ps.close();

      System.out.println("[Product_" + product.getProductId() + "] Updated Successfully!");

    } catch (SQLException e) {

      System.out.println("[ERROR] Update Product Failed");
      e.printStackTrace();

    } catch (Exception e) {

      System.out.println("[ERROR] Something Wrong?");
      e.printStackTrace();
    }
  }

  // Check product exists
  public boolean productExists(String id) {

    try {

      Connection conn = DBConnect.getConnection();

      PreparedStatement ps = conn.prepareStatement(
          "SELECT 1 FROM products WHERE product_id=?");

      ps.setString(
          1,
          id);

      ResultSet rs = ps.executeQuery();

      return rs.next();

    } catch (Exception e) {

      return false;

    }

  }

}