package com.example.project;

import java.util.ArrayList;

public class Quiz {
    private static int nextID = 1;
    private String name;
    private boolean isCompleted;
    private int ID;
    private ArrayList<Integer> questionsIds;

    public Quiz(String name, ArrayList<Integer> questionsIds) {
        this.name = name;
        this.questionsIds = questionsIds;
        this.ID = nextID;
        isCompleted = false;
        nextID++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Integer> getQuestionsIds() {
        return questionsIds;
    }

    public void setQuestionsIds(ArrayList<Integer> questionsIds) {
        this.questionsIds = questionsIds;
    }

    public static int getNextID() {
        return nextID;
    }

    public static void setNextID(int nextID) {
        Quiz.nextID = nextID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void completeQuiz() {
        isCompleted = true;
    }

    public String isCompleted() {
        if (isCompleted) {
            return "True";
        }

        return "False";
    }
}
