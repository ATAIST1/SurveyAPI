package org.example;

import org.example.entities.User;

import java.sql.*;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        String conString = "jdbc:postgresql://localhost:5432/postgres";
        Connection con = null;
        ResultSet rs = null;
        Statement statement = null;
        ArrayList<User> users = new ArrayList<User>();
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(conString, "postgres", "0000");
            statement= con.createStatement();
            rs = statement.executeQuery("SELECT id,name,surname,username FROM users ORDER BY id");

            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String username = rs.getString(("username"));

                User user = new User(id,name,surname,username);
                users.add(user);

            }


        }catch(SQLException e) {
            System.out.println("cnnection server: " + e.getMessage());
        }catch(ClassNotFoundException e){
            System.out.println("driver not found: " + e.getMessage());
        }

        for(User user : users){
            System.out.println(user);
        }



    }
}