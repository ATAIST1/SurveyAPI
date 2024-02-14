package org.example.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Question extends Survey {
    private int surveyId;
    private int questionId;
    private static int id_gen = 1;
    private String text;
    private Option option1;
    private Option option2;
    private Option option3;
    private Option option4;

    public Question() {
        this.questionId = id_gen++;
    }

    public Question(int surveyId, String text, Option option1, Option option2, Option option3, Option option4) {
        this();
        this.surveyId = surveyId;
        setText(text);
        setOption1(option1);
        setOption2(option2);
        setOption3(option3);
        setOption4(option4);
    }

}
