package org.example.Services;

import org.example.entities.Survey;
import org.example.entities.Question;
import org.example.entities.Option;
import org.example.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Map;

public class UserService {
    ArrayList<Survey> surveys = new ArrayList<Survey>();
    public void createSurvey() {
        String conString = "jdbc:postgresql://localhost:5432/SURVEY";
        Connection con = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        Scanner scanner = new Scanner(System.in);

        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(conString, "postgres", "0000");
            preparedStatement = con.prepareStatement("INSERT INTO surveys (title, description) VALUES (?, ?)");

            String title = scanner.nextLine();
            String description = scanner.nextLine();

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);

            Survey survey1 = new Survey(title, description);
            surveys.add(survey1);

            System.out.println("How many questions you want to add?");
            int questionNumber = scanner.nextInt();
            Question question = new Question();
            scanner.nextLine();
            for (int i = 1; i <= questionNumber; i++) {
                System.out.println("Your question number " + i + "is?");
                String text = scanner.nextLine();

                System.out.println("Your first option:");
                Option option1 = new Option(scanner.nextLine(), question.getQuestionId());

                System.out.println("Your second option:");
                Option option2 = new Option(scanner.nextLine(), question.getQuestionId());

                System.out.println("Your third option:");
                Option option3 = new Option(scanner.nextLine(), question.getQuestionId());

                System.out.println("Your fourth option:");
                Option option4 = new Option(scanner.nextLine(), question.getQuestionId());

                question = new Question(text, option1, option2, option3, option4);

                survey1.addQuestion(question);
            }
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Survey created successfully!");
            } else {
                System.out.println("Failed to create Survey.");
            }


        }
        catch(SQLException e){
            System.out.println("connection server: " + e.getMessage());
        } catch(ClassNotFoundException e){
            System.out.println("driver not found: " + e.getMessage());
        }finally{
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                System.out.println("could not close connection: " + e.getMessage());
            }

        }
    }



}
