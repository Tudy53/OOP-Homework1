package com.example.project;

import java.util.HashMap;

public class GetQuestionID extends Command{
    private HashMap<String, String> info;

    public GetQuestionID(HashMap<String, String> info) {
        this.info = info;
    }

    private boolean isCorrect() {
        if (info.get("-u") == null || info.get("-p") == null) {
            System.out.println("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
            return false;
        }

        String name = info.get("-u");
        String pass = info.get("-p");

        if (!Database.userExists(name)) {
            System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
            return false;
        }

        if (!Database.getUsers().get(name).getMyPassword().equals(pass)) {
            System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
            return false;
        }

        if (info.get("-text") == null) {
            System.out.println("{ 'status' : 'error', 'message' : 'Question does not exist'}");
            return false;
        }
        return true;
    }

    @Override
    void doIt() {
        if (!isCorrect()) {
            return;
        }
        String questionText = info.get("-text");

        HashMap<Integer, Question> questions = Database.getQuestions();
        for (Question q : questions.values()) {
            if (q.getText().equals(questionText)) {
                System.out.println("{ 'status' : 'ok', 'message' : '" + q.getId() + "'}");
                return;
            }
        }
        System.out.println("{ 'status' : 'error', 'message' : 'Question does not exist'}");
    }
}
