package org.example.entities;

import lombok.Getter;
import lombok.Setter;

import javax.management.ConstructorParameters;

@Getter
@Setter

public class Question {
    private String text;
    private String option1;
    private String option2;
    private String option3;
    private String option4;

    public Question(String text, String option1, String option2, String option3, String option4) {
        setText(text);
        setOption1(option1);
        setOption2(option2);
        setOption3(option3);
        setOption4(option4);
    }

}
