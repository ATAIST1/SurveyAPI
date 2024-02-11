package org.example.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Getter
@Setter
public class Survey {
    private int id;
    private static int id_gen = 1;
    private int user_id;
    private String title;
    private String description;
    private ArrayList<Question> questions;

    public Survey() {

    }
    public void addQuestion(Question question) {questions.add(question);}

    public Survey(String title, String description) {
        questions = new ArrayList<Question>();
        id = id_gen++;
        setTitle(title);
        setDescription(description);}
}
