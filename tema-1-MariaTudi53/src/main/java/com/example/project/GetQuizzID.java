package com.example.project;

import java.util.HashMap;

public class GetQuizzID extends Command {
    HashMap<String, String> info;

    public GetQuizzID(HashMap<String, String> info) {
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

        String nameQuiz = info.get("-name");
        Quiz quiz = Database.getQuizzes().get(nameQuiz);
        if (quiz == null) {
            System.out.println("{ 'status' : 'error', 'message' : 'Quizz does not exist'}");
            return;
        }

        System.out.println("{ 'status' : 'ok', 'message' : '" + quiz.getID() + "'}");
    }
}
