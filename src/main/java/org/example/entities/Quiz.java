package org.example.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Getter
@Setter
public class Quiz {
    private String quizName;
    private ArrayList<Question> questions;

    public Quiz() {
        questions = new ArrayList<Question>();
    }

    public
}