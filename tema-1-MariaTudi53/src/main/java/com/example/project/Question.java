package com.example.project;

import java.util.ArrayList;

public class Question {
    private static int nextID = 1;
    private int id;
    private String text;
    private ArrayList<Answer> answers;
    private String type;

    public Question(String text, ArrayList<Answer> answers, String type) {
        this.text = text;
        this.answers = answers;
        this.id = nextID;
        this.type = type;
        nextID++;
    }

    public Question(int id, String text, ArrayList<Answer> answers) {
        this.id = id;
        this.text = text;
        this.answers = answers;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    public static int getNextID() {
        return nextID;
    }

    public static void setNextID(int nextID) {
        Question.nextID = nextID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
