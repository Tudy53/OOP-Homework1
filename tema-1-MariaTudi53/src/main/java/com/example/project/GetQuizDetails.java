package com.example.project;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;

public class GetQuizDetails extends Command {
    private HashMap<String, String> info;

    public GetQuizDetails(HashMap<String, String> info) {
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

        int quizID = Integer.parseInt(info.get("-id"));
        Quiz quiz = Database.getQuiz(quizID);
        if (quiz == null) {
            System.out.println("{ 'status' : 'error', 'message' : 'Quizz ID does not exist'}");
            return;
        }

        StringBuilder output = new StringBuilder("{'status':'ok','message':'[");
        ArrayList<Integer> questionsIDs = quiz.getQuestionsIds();
        Question q;
        for (int i : questionsIDs) {
            q = Database.getQuestions().get(i);
            output.append("{\"question-name\":\"").append(q.getText()).append("\", \"question_index\":\"").append(q.getId()).append("\", \"question_type\":\"").append(q.getType()).append("\", \"answers\":\"[");
            for (Answer a : q.getAnswers()) {
                output.append("{\"answer_name\":\"").append(a.getText()).append("\", \"answer_id\":\"").append(a.getId()).append("\"}, ");
            }

            output = new StringBuilder(output.substring(0, output.length() - 2));
            output.append("]\"}, ");
        }

        output = new StringBuilder(output.substring(0, output.length() - 2));
        output.append("]'}");
        System.out.println(output);
    }
}
