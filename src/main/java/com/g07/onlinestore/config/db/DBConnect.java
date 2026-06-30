package com.g07.onlinestore.config.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class DBConnect {

  private static final Dotenv dotenv = Dotenv.load();

  private static Connection conn;

  public static Connection getConnection() {

    try {

      if (conn == null || conn.isClosed()) {

        String url = dotenv.get("SUPABASE_URL");
        String user = dotenv.get("SUPABASE_USER");
        String pass = dotenv.get("SUPABASE_PASSWORD");

        if (url == null || user == null || pass == null) {

          throw new RuntimeException(
              "[.env] variables missing");

        }

        conn = DriverManager.getConnection(url, user, pass);

        System.out.println(
            "[Connected to Supabase] ... OK");

      }

    } catch (SQLException e) {

      System.out.println(
          "[Database connection failed] ... ERROR");

      e.printStackTrace();

    }

    return conn;

  }

  public void DBTestConnect(Connection obj) {

    if (obj != null) {

      System.out.println(
          "[Database connected] ... OK\n");

    }

  }

}