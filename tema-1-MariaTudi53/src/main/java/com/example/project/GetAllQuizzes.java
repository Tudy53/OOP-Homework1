package com.example.project;

import java.util.HashMap;

public class GetAllQuizzes extends Command {
    private HashMap<String, String> info;

    public GetAllQuizzes(HashMap<String, String> info) {
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

        return true;
    }

    @Override
    void doIt() {
        if (!isCorrect()) {
            return;
        }

        HashMap<String, Quiz> quizzes = Database.getQuizzes();
        StringBuilder output = new StringBuilder("{'status':'ok','message':'[");
        for (Quiz quiz : quizzes.values()) {
            output.append("{\"quizz_id\" : \"").append(quiz.getID()).append("\", \"quizz_name\" : \"").append(quiz.getName()).append("\", \"is_completed\" : \"").append(quiz.isCompleted()).append("\"}, ");
        }
        output = new StringBuilder(output.substring(0, output.length() - 2));
        output.append("]'}");

        System.out.println(output);
    }
}
