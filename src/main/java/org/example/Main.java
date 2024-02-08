package org.example;

import org.example.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) {
        String conString = "jdbc:postgresql://localhost:5432/SURVEY";
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
        }finally {
            try{
                if(con!=null)
                    con.close();
            }catch(SQLException e){
                throw new RuntimeException(e);
            }

        }

        for(User user : users){
            System.out.println(user);
        }
        class RegisterUser {
            public static void main(String[] args) {
                // Assuming you have already established a database connection
                Connection connection = null;
                PreparedStatement preparedStatement = null;
                Scanner scanner = new Scanner(System.in);

                try {
                    // Create a PreparedStatement with a parameterized INSERT query
                    preparedStatement = connection.prepareStatement("INSERT INTO your_table_name (id, name, surname, username) VALUES (?, ?, ?, ?)");

                    // Ask the user for input
                    System.out.println("Enter ID:");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    System.out.println("Enter Name:");
                    String name = scanner.nextLine();
                    System.out.println("Enter Surname:");
                    String surname = scanner.nextLine();
                    System.out.println("Enter Username:");
                    String username = scanner.nextLine();

                    // Set the parameters of the prepared statement
                    preparedStatement.setInt(1, id);
                    preparedStatement.setString(2, name);
                    preparedStatement.setString(3, surname);
                    preparedStatement.setString(4, username);

                    // Execute the query
                    int rowsAffected = preparedStatement.executeUpdate();

                    // Check if the query was successful
                    if (rowsAffected > 0) {
                        System.out.println("User registered successfully!");
                    } else {
                        System.out.println("Failed to register user.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    // Close resources in a finally block
                    try {
                        if (preparedStatement != null) {
                            preparedStatement.close();
                        }
                        if (connection != null) {
                            connection.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }





    }
}