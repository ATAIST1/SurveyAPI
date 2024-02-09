package org.example.Services;

import org.example.entities.Survey;
import org.example.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;


public class UserService {
    public void createSurvey() {
        String conString = "jdbc:postgresql://localhost:5432/SURVEY";
        Connection con = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        Scanner scanner = new Scanner(System.in);
        ArrayList<Survey> surveys = new ArrayList<Survey>();
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(conString, "postgres", "0000");
            preparedStatement = con.prepareStatement("INSERT INTO surveys (title, description) VALUES (?, ?)");
            String title =  scanner.nextLine();
            String description = scanner.nextLine();
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            Survey survey1 = new Survey(title, description);
            surveys.add(survey1);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User registered successfully!");
            } else {
                System.out.println("Failed to register user.");
            }


        } catch (SQLException e) {
            System.out.println("connection server: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("driver not found: " + e.getMessage());
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                System.out.println("could not close connection: " + e.getMessage());
            }

        }
    }
}
