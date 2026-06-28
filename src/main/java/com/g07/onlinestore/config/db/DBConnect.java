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
      if (url == null || user == null || pass == null) {
        throw new RuntimeException("[.env] variables missing");
      }

      Connection conn = DriverManager.getConnection(url, user, pass);

      System.out.println("[Connected to Supabase] ... OK\n");
      
      return conn;
    } catch (SQLException e) {
      System.out.println("[Database connection failed] ... ERROR\n");

      e.printStackTrace();

      return null;
    }
  }

  public void DBTestConnect(Connection obj) {
    if (obj != null) {
      System.out.println("[Database test successful] ... OK\n");
    }
  }
}
