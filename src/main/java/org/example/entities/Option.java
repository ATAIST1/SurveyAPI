package org.example.entities;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Option extends Question {
    private String text;
    private int questionId;

    public Option(String text, int questionId) {
        this.text = text;
        this.questionId = questionId;
    }
}
