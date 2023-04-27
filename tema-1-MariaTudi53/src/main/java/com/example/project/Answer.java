package com.example.project;

public class Answer {
    private static int nextID = 1;
    private int id;
    private String text;
    private int value;

    public Answer(String text, int value) {
        this.text = text;
        this.value = value;
        id = nextID;
        nextID++;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static int getNextID() {
        return nextID;
    }

    public static void setNextID(int nextID) {
        Answer.nextID = nextID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
