package org.example.entities;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Option extends Question {

    private String text;
    private int questionId;
    private int optionId;
    private static int id_gen = 1;
    public Option(String text, int questionId) {
        optionId = id_gen++;
        this.text = text;
        this.questionId = questionId;
    }
}
