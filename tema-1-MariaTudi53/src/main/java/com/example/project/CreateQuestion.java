package com.example.project;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class CreateQuestion extends Command{
    private HashMap<String, String> info;

    public CreateQuestion(HashMap<String, String> info) {
        this.info = info;
    }

    private boolean isCorrect() {

        if (info.get("-u") == null || info.get("-p") == null) {
            System.out.println("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
            return false;
        }

        String name = info.get("-u");
        String pass = info.get("-p");

        // parcurg hashmap si vad daca am username ul ala

        if (!Database.userExists(name)) {
            System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
            return false;
        }

        if (!Database.getUsers().get(name).getMyPassword().equals(pass)) {
            System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
            return false;
        }

        if (info.get("-text") == null) {
            System.out.println("{ 'status' : 'error', 'message' : 'No question text provided'}");
            return false;
        }

        if (info.get("nrAnswers").equals("0")) {
            System.out.println("{ 'status' : 'error', 'message' : 'No answer provided'}");
            return false;
        }

        if (info.get("nrAnswers").equals("2")) {
            System.out.println("{ 'status' : 'error', 'message' : 'Only one answer provided'}");
            return false;
        }

        if (info.get("nrAnswers").equals("10")) {
            System.out.println("{ 'status' : 'error', 'message' : 'More than 5 answers were submitted'}");
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

        String questionText = info.get("-text");
        if (Database.questionExists(questionText)) {
            System.out.println("{ 'status' : 'error', 'message' : 'Question already exists'}");
            return;
        }

        ArrayList<Answer> answers = new ArrayList<>();
        HashMap<String, Boolean> haveAnswer = new HashMap<>();

        int numberOfAnswers = Integer.parseInt(info.get("nrAnswers")) / 2;
        int sumOfValues = 0;

        for (int i = 0; i < numberOfAnswers; i++) {
            String answerText = info.get("-answer-" + (i + 1));
            if (answerText == null) {
                System.out.println("{ 'status' : 'error', 'message' : 'Answer " + (i + 1) + " has no answer description'}");
                return;
            }
            String answerValue = info.get("-answer-" + (i + 1) + "-is-correct");
            if (answerValue == null) {
                System.out.println("{ 'status' : 'error', 'message' : 'Answer " + (i + 1) + " has no answer correct flag'}");
                return;
            }

            int value = Integer.parseInt(answerValue);

            Answer answer = new Answer(answerText, value);

            if (haveAnswer.get(answerText) != null) {
                System.out.println("{ 'status' : 'error', 'message' : 'Same answer provided more than once'}");
                return;
            }

            haveAnswer.put(answerText, true);

            sumOfValues += value;
            answers.add(answer);
        }

        if (info.get("-type").equals("single") && sumOfValues != 1) {
            System.out.println("{ 'status' : 'error', 'message' : 'Single correct answer question has more than one correct answer'}");
            return;
        }

        Question question = new Question(questionText, answers, info.get("-type"));
        Database.addQuestion(question);
        Database.addQuestionToUser(name, question.getId());
        System.out.println("{ 'status' : 'ok', 'message' : 'Question added successfully'}");
    }
}
