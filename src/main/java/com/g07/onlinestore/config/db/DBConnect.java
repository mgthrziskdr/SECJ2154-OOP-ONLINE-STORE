package com.g07.onlinestore.config.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class DBConnect {
  private static final Dotenv dotenv = Dotenv.load();

  public static Connection getConnection() {

    String url = dotenv.get("SUPABASE_URL");
    String user = dotenv.get("SUPABASE_USER");
    String pass = dotenv.get("SUPABASE_PASSWORD");

    try {
      Connection conn = DriverManager.getConnection(url, user, pass);

      System.out.println("Connected to Supabase!");

      return conn;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }
}
