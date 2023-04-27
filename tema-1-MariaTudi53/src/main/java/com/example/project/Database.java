package com.example.project;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Database {
    public static final String USERS_FILE = "users.csv";
    public static final String QUESTIONS_FILE = "questions.csv";
    public static final String USERSQUESTIONS_FILE = "users_questions.csv";
    public static final String QUIZZES_FILE = "quizzes.csv";
    public static final String USERSQUIZZES_FILE = "users_quizzes.csv";
    private static HashMap<String, User> users;
    private static HashMap<Integer, Question> questions;
    private static HashMap<String, ArrayList<Integer>> usersQuestions;
    private static HashMap<String, ArrayList<String>> usersQuizzes;
    private static HashMap<String, Quiz> quizzes;

    public static boolean userExists(String username) {
        return getUsers().containsKey(username);
    }
    public static boolean questionExists(String questionText) {
        for (Question q : getQuestions().values()) {
            if (q.getText().equals(questionText)) {
                return true;
            }
        }

        return false;
    }

    public static Quiz getQuiz(int id) {
        HashMap<String, Quiz> quizzes = getQuizzes();
        for (Quiz q : quizzes.values()) {
            if (q.getID() == id) {
                return q;
            }
        }

        return null;
    }

    //la finalul programului se actualizeaza baza de date
    public static void addUser(User user) {
        users.put(user.getMyUsername(), user);
    }
    public static void addQuestion(Question question) {
        questions.put(question.getId(), question);
    }
    public static void addQuestionToUser(String username, int questionID) {
        ArrayList<Integer> questionsIDs = usersQuestions.get(username);
        if (questionsIDs == null) {
            questionsIDs = new ArrayList<>();
        }
        questionsIDs.add(questionID);
        usersQuestions.put(username, questionsIDs);
    }

    public static void addQuiz(String name, Quiz quiz) {
        quizzes.put(name, quiz);
    }

    public static void addQuizToUser(String username, String quizName) {
        ArrayList<String> quizzesNames = usersQuizzes.get(username);
        if (quizzesNames == null) {
            quizzesNames = new ArrayList<>();
        }

        quizzesNames.add(quizName);
        usersQuizzes.put(username, quizzesNames);
    }

    public static HashMap<String, User> getUsers() {

        if (users != null) {
            return users;
        }

        users = new HashMap<>();
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader((USERS_FILE)));
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                String name = tokens[0];
                String pas = tokens[1];
                User user = new User(name, pas);
                users.put(name, user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  users;
    }

    public static HashMap<Integer, Question> getQuestions() {

        if (questions != null) {
            return questions;
        }

        questions = new HashMap<>();
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader((QUESTIONS_FILE)));
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                int questionID = Integer.parseInt(tokens[0]);
                String questionText = tokens[1];
                String questionType = tokens[2];
                int nrAnswers = (tokens.length - 2) / 2;
                if (nrAnswers == 0) {
                    System.out.println("Not enough answers for the question " + questionText + " with id " + questionID);
                }

                ArrayList<Answer> answers = new ArrayList<>();
                int valueAnswer;
                String answerText;
                for (int i = 0; i < nrAnswers; i++) {
                    answerText = tokens[2 * i + 3];
                    valueAnswer = Integer.parseInt(tokens[2 * i + 4]);
                    Answer answer = new Answer(answerText, valueAnswer);
                    answers.add(answer);
                }
                Question question = new Question(questionText, answers, questionType);
                questions.put(questionID, question);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  questions;
    }

    public static HashMap<String, Quiz> getQuizzes() {

        if (quizzes != null) {
            return quizzes;
        }

        quizzes = new HashMap<>();
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader((QUIZZES_FILE)));
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                String name = tokens[0];
                ArrayList<Integer> questionsIDs = new ArrayList<>();
                int id;
                for (int i = 1; i < tokens.length; i++) {
                    id = Integer.parseInt(tokens[i]);
                    questionsIDs.add(id);
                }
                quizzes.put(name, new Quiz(name, questionsIDs));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  quizzes;
    }

    public static HashMap<String, ArrayList<Integer>> getUsersQuestions() {
        if (usersQuestions != null) {
            return usersQuestions;
        }

        usersQuestions = new HashMap<>();
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader((USERSQUESTIONS_FILE)));
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                String name = tokens[0];
                ArrayList<Integer> questionIDs = new ArrayList<>();
                for (int i = 1; i < tokens.length; i++) {
                    questionIDs.add(Integer.parseInt(tokens[i]));
                }
                usersQuestions.put(name, questionIDs);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usersQuestions;
    }

    public static HashMap<String, ArrayList<String>> getUsersQuizzes() {
        if (usersQuizzes != null) {
            return usersQuizzes;
        }

        usersQuizzes = new HashMap<>();
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader((USERSQUIZZES_FILE)));
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                String name = tokens[0];
                ArrayList<String> quizzesNames = new ArrayList<>(Arrays.asList(tokens).subList(1, tokens.length));
                usersQuizzes.put(name, quizzesNames);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usersQuizzes;
    }

    private static void actualizeUsers() {
        try (FileWriter fw = new FileWriter(USERS_FILE, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            for (User user : getUsers().values()) {
                out.println(user.getMyUsername() + "," + user.getMyPassword());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void actualizeQuestions() {
        try (FileWriter fw = new FileWriter(QUESTIONS_FILE, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            for (Question question : getQuestions().values()) {
                out.print(question.getId() + "," + question.getText() + "," + question.getType());
                ArrayList<Answer> answers = question.getAnswers();
                for (Answer a : answers) {
                    out.print("," + a.getText() + "," + a.getValue());
                }
                out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void actualizeUsersQuizzes() {
        try (FileWriter fw = new FileWriter(USERSQUIZZES_FILE, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            for (Map.Entry<String, ArrayList<String>> entry : getUsersQuizzes().entrySet()) {
                out.print(entry.getKey());
                ArrayList<String> quizzesNames = entry.getValue();
                for (String name : quizzesNames) {
                    out.print("," + name);
                }
                out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void actualizeQuizzes() {
        try (FileWriter fw = new FileWriter(QUIZZES_FILE, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            for (Quiz quiz : getQuizzes().values()) {
                out.print(quiz.getName());
                ArrayList<Integer> questionsIDs = quiz.getQuestionsIds();
                for (Integer i : questionsIDs) {
                    out.print("," + i);
                }
                out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void actualizeUsersQuestions() {
        try (FileWriter fw = new FileWriter(USERSQUESTIONS_FILE, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            for (Map.Entry<String, ArrayList<Integer>> entry : getUsersQuestions().entrySet()) {
                out.print(entry.getKey());
                ArrayList<Integer> questionIDs = entry.getValue();
                for (Integer i : questionIDs) {
                    out.print("," + i);
                }
                out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cleanup() {
        users = new HashMap<>();
        questions = new HashMap<>();
        usersQuestions = new HashMap<>();
        quizzes = new HashMap<>();
        usersQuizzes = new HashMap<>();
        Question.setNextID(1);
        Quiz.setNextID(1);
        Answer.setNextID(1);
    }

    public static void actualize() {
        actualizeUsers();
        actualizeQuestions();
        actualizeUsersQuestions();
        actualizeQuizzes();
        actualizeUsersQuizzes();
    }
}
