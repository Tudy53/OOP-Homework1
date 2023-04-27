package com.example.project;

import java.util.ArrayList;
import java.util.HashMap;

public class CreateQuizz extends Command{
    private HashMap<String, String> info;

    public CreateQuizz(HashMap<String, String> info) {
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

        String name = info.get("-u");
        String nameQuiz = info.get("-name");
        if (Database.getQuizzes().get(nameQuiz) != null) {
            System.out.println("{ 'status' : 'error', 'message' : 'Quizz name already exists'}");
            return;
        }


        int nrQuestions = Integer.parseInt(info.get("nrQuestions"));
        if (nrQuestions > 10) {
            System.out.println("{ 'status' : 'error', 'message' : 'Quizz has more than 10 questions'}");
            return;
        }

        int idQuestion;
        ArrayList<Integer> questionsIds = new ArrayList<>();
        for (int i = 0; i < nrQuestions; i++) {
            idQuestion = Integer.parseInt(info.get("-question-" + (i + 1)));
            if (Database.getQuestions().get(idQuestion) == null) {
                System.out.println("{ 'status' : 'error', 'message' : 'Question ID for question " + (i + 1) + " does not exist'}");
                return;
            }
            questionsIds.add(idQuestion);
        }
        Quiz quiz = new Quiz(nameQuiz, questionsIds);
        Database.addQuiz(nameQuiz, quiz);
        Database.addQuizToUser(name, nameQuiz);
        System.out.println("{ 'status' : 'ok', 'message' : 'Quizz added succesfully'}");
    }
}
