package org.example.Services;

import org.example.entities.*;

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
        ResultSet rs = null;
        Statement statement = null;
        Statement stmnt = null;

        try {
            con = DriverManager.getConnection(conString, "postgres", "0000");
            statement = con.createStatement();
            rs = statement.executeQuery("SELECT survey_id, title, description, created_at, user_id FROM surveys ORDER BY survey_id");

            Class.forName("org.postgresql.Driver");

            preparedStatement = con.prepareStatement("INSERT INTO surveys (title, description) VALUES (?, ?)");


            System.out.println("Enter survey title:");
            String title = scanner.nextLine();
            System.out.println("Enter survey description:");
            String description = scanner.nextLine();


            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);

            Survey survey1 = new Survey();
            while(rs.next()) {
                if(rs.isLast()) {
                    survey1 = new Survey(user_id, title, description, rs.getInt("survey_id"));
                }
            }



            surveys.add(survey1);

            stmnt = con.createStatement();
            ResultSet rsQuestion = stmnt.executeQuery("SELECT * FROM questions ORDER BY question_id");
            ResultSet rsOption = statement.executeQuery("SELECT option_id, question_id, option_text FROM options ORDER BY option_id");
            preparedStatement2 = con.prepareStatement("INSERT INTO questions (survey_id, question_text) VALUES (?, ?)");
            preparedStatement3 = con.prepareStatement("INSERT INTO options (question_id, option_text) VALUES(?, ?)");
            System.out.println("How many questions you want to add?");
            int questionNumber = scanner.nextInt();
            Question question = new Question();
            scanner.nextLine();
            for (int i = 1; i <= questionNumber; i++) {
                System.out.println("Your question number " + i + " is?");
                String text = scanner.nextLine();


                while(rsQuestion.next()) {
                    if (rsQuestion.isLast()) {
                        question = new Question(rs.getInt("survey_id"), text, rsQuestion.getInt("question_id"));
                    }
                }

                Option option1 = new Option();
                Option option2 = new Option();
                Option option3 = new Option();
                Option option4 = new Option();

                preparedStatement2.setInt(1, survey1.getId());

                System.out.println("Your first option:");
                String option1Text = scanner.nextLine();
                preparedStatement3.setInt(1, question.getQuestionId());
                preparedStatement3.setString(2, option1Text);
                while(rsOption.next()) {
                    if(rsOption.isLast()) {
                        option1 = new Option(option1Text, question.getQuestionId(), rsOption.getInt("option_id"));
                    }
                }
                System.out.println("Your second option:");
                String option2Text = scanner.nextLine();
                preparedStatement3.setInt(1, question.getQuestionId());
                preparedStatement3.setString(2, option2Text);
                while(rsOption.next()) {
                    if(rsOption.isLast()) {
                        option2 = new Option(option2Text, question.getQuestionId(), rsOption.getInt("option_id"));
                    }
                }

                System.out.println("Your third option:");
                String option3Text = scanner.nextLine();
                preparedStatement3.setInt(1, question.getQuestionId());
                preparedStatement3.setString(2, option3Text);
                while(rsOption.next()) {
                    if(rsOption.isLast()) {
                        option3 = new Option(option3Text, question.getQuestionId(), rsOption.getInt("option_id"));
                    }
                }

                System.out.println("Your fourth option:");
                String option4Text = scanner.nextLine();
                preparedStatement3.setInt(1, question.getQuestionId());
                preparedStatement3.setString(2, option4Text);
                while(rsOption.next()) {
                    if(rsOption.isLast()) {
                        option4 = new Option(option4Text, question.getQuestionId(), rsOption.getInt("option_id"));
                    }
                }


                question.addOption(option1);
                question.addOption(option2);
                question.addOption(option3);
                question.addOption(option4);
                preparedStatement2.setInt(1, survey1.getId());
                preparedStatement2.setString(2, text);

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
        }finally {
            try {
                if (con != null)
                    con.close();
                if (rs != null)
                    rs.close();
                if (statement != null)
                    statement.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (preparedStatement2 != null)
                    preparedStatement2.close();
                if (preparedStatement3 != null)
                    preparedStatement3.close();
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
        ResultSet rsQuestion = null;
        ResultSet rsOption = null;
        ArrayList<Survey> surveysTable = new ArrayList<Survey>();
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(conString, "postgres", "0000");
            statement = con.createStatement();
            Statement statement2 = con.createStatement();
            Statement statement3 = con.createStatement();
            rsQuestion = statement.executeQuery("SELECT question_id, survey_id, question_text FROM questions ORDER BY survey_id");
            rsOption = statement2.executeQuery("SELECT option_id, question_id, option_text FROM options ORDER BY question_id");
            rs = statement3.executeQuery("SELECT survey_id, title, description, user_id FROM surveys ORDER BY survey_id");
            while(rs.next()) {
                int survey_id = rs.getInt("survey_id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                int userId = rs.getInt("user_id");
                Survey survey = new Survey(userId, title, description, survey_id);
                Question question = new Question();
                while(rsQuestion.next()) {
                    if(rsQuestion.getInt("survey_id") == survey_id) {
                        int question_id = rsQuestion.getInt("question_id");
                        String QuestionText = rsQuestion.getString("question_text");
                        rsOption.beforeFirst();
                        while(rsOption.next()){
                            if(rsOption.getInt("question_id") == question_id) {
                                int option_id = rsOption.getInt("option_id");
                                String option_text = rsOption.getString("option_text");
                                Option option = new Option(option_text, question_id, option_id);
                                question.addOption(option);
                            }
                        }
                        question = new Question(survey_id, QuestionText, question_id);
                        survey.addQuestion(question);
                    }
                }
                surveysTable.add(survey);
            }
            for(Survey survey: surveysTable) {
                System.out.println(survey);
            }

            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO responses (survey_id, question_id, user_id, answer) VALUES (?, ?, ?, ?)");
            System.out.println("Which survey you want to answer? (Enter survey_id) ");
            int selectedSurveyId = scanner.nextInt();
            ArrayList<Response> responsesList = new ArrayList<Response>();

            for(Survey survey: surveysTable){
                if(survey.getId() == selectedSurveyId) {
                    System.out.println(survey.getTitle());
                    for(Question question : survey.getQuestions()) {
                        System.out.println(question.getText());
                        System.out.println("1. " + question.getOptions().get(1).getText());
                        System.out.println("2. " + question.getOptions().get(2).getText());
                        System.out.println("3. " + question.getOptions().get(3).getText());
                        System.out.println("4. " + question.getOptions().get(4).getText());
                        System.out.print("Your answer:");
                        int answer = scanner.nextInt();
                        Response response = new Response(survey.getId(), question.getQuestionId(), user_id, answer);
                        responsesList.add(response);

                    }
                    break;
                }

            }
            for(Response response: responsesList) {
                preparedStatement.setInt(1, response.getSurvey_id());
                preparedStatement.setInt(2, response.getQuestion_id());
                preparedStatement.setInt(3, response.getUser_id());
                preparedStatement.setInt(4, response.getAnswer());
                preparedStatement.setInt(5, response.getAnswer());
                preparedStatement.executeUpdate();
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
