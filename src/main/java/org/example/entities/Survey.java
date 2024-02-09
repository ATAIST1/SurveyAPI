package org.example.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Getter
@Setter
public class Survey {
    private int id;
    private static int id_gen = 1;
    private String title;
    private String description;
    private ArrayList<Question> questions;

    public Survey() {
        questions = new ArrayList<Question>();
        id = id_gen++;
    }
    public void addQuestion(Question question) {questions.add(question);}

    public Survey(String title, String description) { setTitle(title);}
}
