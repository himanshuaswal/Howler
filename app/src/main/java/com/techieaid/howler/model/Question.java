package com.techieaid.howler.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/*
 ** Created by Gautam Krishnan {@link https://github.com/GautiKrish}
 */public class Question extends RealmObject {
    @PrimaryKey
    private int iD;
    private String question;
    private String answers[];
    private String correctAnswer;

    public void setId(int iD) {
        this.iD = iD;
    }

    public int getiD() {
        return iD;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
