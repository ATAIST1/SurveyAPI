package org.example;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String conString = "jdbc:postgresql://localhost:5432/postgres";
        Connection con = null;
        ResultSet rs = null;
        Statement statement = null;
        try {
            con = DriverManager.getConnection(conString, "postgres", "0000");
            statement= con.createStatement();
            rs = statement.executeQuery("SELECT id,name,");



        }catch(SQLException e) {
            System.out.println("cnnection server " + e.getMessage());
        }
    }
}