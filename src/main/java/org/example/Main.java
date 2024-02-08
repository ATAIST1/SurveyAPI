package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String conString = "jdbc:postgresql://localhost:5432/postgres";
        try {
            Connection con = DriverManager.getConnection(conString, "postgres", "0000");
        }catch(SQLException e) {
            System.out.println("cnnection server " + e.getMessage());
        }
    }
}