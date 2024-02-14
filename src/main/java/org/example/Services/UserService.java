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
    public void createSurvey(int user_id) {
        String conString = "jdbc:postgresql://localhost:5432/SURVEY";
        Connection con = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
        PreparedStatement preparedStatement3 = null;

        Scanner scanner = new Scanner(System.in);

        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(conString, "postgres", "0000");
            preparedStatement = con.prepareStatement("INSERT INTO surveys (survey_id, title, description, user_id) VALUES (?, ?, ?, ?)");


            System.out.println("Enter survey title:");
            String title = scanner.nextLine();
            System.out.println("Enter survey description:");
            String description = scanner.nextLine();

            Survey survey1 = new Survey(user_id, title, description);

            preparedStatement.setInt(1, survey1.getId());
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, description);
            preparedStatement.setInt(4, survey1.getUser_id());



            surveys.add(survey1);


            preparedStatement2 = con.prepareStatement("INSERT INTO questions (question_id, survey_id, question_text) VALUES (?, ?, ?)");
            preparedStatement3 = con.prepareStatement("INSERT INTO options (option_id, question_id, option_text) VALUES(?, ?, ?)");
            System.out.println("How many questions you want to add?");
            int questionNumber = scanner.nextInt();
            Question question = new Question();
            scanner.nextLine();
            for (int i = 1; i <= questionNumber; i++) {
                System.out.println("Your question number " + i + " is?");
                String text = scanner.nextLine();

                System.out.println("How many options you want to add?");
                int numOfOptions = scanner.nextInt();

                System.out.println("Your first option:");
                Option option1 = new Option(scanner.nextLine(), question.getQuestionId());
                preparedStatement3.setInt(1, option1.getOptionId());
                preparedStatement3.setInt(2, question.getQuestionId());

                System.out.println("Your second option:");
                Option option2 = new Option(scanner.nextLine(), question.getQuestionId());
                preparedStatement3.setInt(1, option2.getOptionId());
                preparedStatement3.setInt(2, question.getQuestionId());

                System.out.println("Your third option:");
                Option option3 = new Option(scanner.nextLine(), question.getQuestionId());
                preparedStatement3.setInt(1, option3.getOptionId());
                preparedStatement3.setInt(2, question.getQuestionId());

                System.out.println("Your fourth option:");
                Option option4 = new Option(scanner.nextLine(), question.getQuestionId());
                preparedStatement3.setInt(1, option4.getOptionId());
                preparedStatement3.setInt(2, question.getQuestionId());


                int surveyId = survey1.getId();

                question = new Question(surveyId, text, option1, option2, option3, option4);
                preparedStatement2.setInt(1, question.getQuestionId());
                preparedStatement2.setInt(2, survey1.getId());
                preparedStatement2.setString(3, text);

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
    public void participateSurvey(int user_id) {
        String conString = "jdbc:postgresql://localhost:5432/SURVEY";
        Connection con = null;
        Scanner scanner = new Scanner(System.in);
        Statement statement = null;
        ResultSet rs = null;
        ArrayList<Survey> surveysTable = new ArrayList<Survey>();
        try {

            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(conString, "postgres", "0000");
            statement = con.createStatement();
            rs = statement.executeQuery("SELECT survey_id, title, description, user_id FROM users ORDER BY id");
            while(rs.next()) {
                int survey_id = rs.getInt("survey_id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                int userId = rs.getInt("user_id");
                Survey survey = new Survey(userId, title, description);
                surveysTable.add(survey);
            }
            for(Survey survey: surveysTable) {
                System.out.println(survey);
            }

            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO responses (response_id, survey_id, question_id, user_id, answer) VALUES (?, ?, ?, ?, ?)");
            System.out.println("Which survey you want to answer? (Enter survey_id) ");
            int selectedSurveyId = scanner.nextInt();
            for(Survey survey: surveysTable){
                if(survey.getId() == selectedSurveyId) {

                }
            }


        }
        catch (SQLException e) {
            System.out.println("connection server: "+ e.getMessage());
        }
        catch (ClassNotFoundException e) {
            System.out.println("Class not found: " + e.getMessage());
        }

    }


}
