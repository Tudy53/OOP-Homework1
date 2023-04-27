package com.example.project;

import java.util.HashMap;

public class Tema1 {

    public static String getToken(String s) {
        return s.substring(s.indexOf('\'') + 1, s.lastIndexOf('\''));
    }

    public static String getFlag(String s) {
        return s.substring(0, s.indexOf(' '));
    }

    public static HashMap<String, String> processArgs(String[] args) {
        HashMap<String, String> res = new HashMap<>();
        int nrArgs = args.length;
        res.put("command", args[0]);
        int nrAnswers = 0;
        int nrQuestions = 0;
        for (int i = 1; i < nrArgs; i++) {
            String flag = getFlag(args[i]);
            String token = getToken(args[i]);
            res.put(flag, token);
            if (flag.contains("answer")) {
                nrAnswers++;
            }
            if (flag.contains("question")) {
                nrQuestions++;
            }
        }

        res.put("nrAnswers", Integer.toString(nrAnswers));
        res.put("nrQuestions", Integer.toString(nrQuestions));

        return res;
    }

    public static void main(final String[] args) {
        if (args == null) {
            System.out.print("Hello world!");
            return;
        }

        HashMap<String, String> info = processArgs(args);

        Command command = null;
        String commandName = info.get("command");

        if (commandName.equals("-create-user")) {
            command = new CreateUser(info);
        }

        if (commandName.equals("-create-question")) {
            command = new CreateQuestion(info);
        }

        if (commandName.equals("-get-question-id-by-text")) {
            command = new GetQuestionID(info);
        }

        if (commandName.equals("-get-all-questions")) {
            command = new GetAllQuestions(info);
        }

        if (commandName.equals("-create-quizz")) {
            command = new CreateQuizz(info);
        }

        if (commandName.equals("-get-quizz-by-name")) {
            command = new GetQuizzID(info);
        }

        if (commandName.equals("-get-all-quizzes")) {
            command = new GetAllQuizzes(info);
        }

        if (commandName.equals("-get-quizz-details-by-id")) {
            command = new GetQuizDetails(info);
        }

        if (commandName.equals("-cleanup-all")) {
            command = new Cleanup();
        }

        if (command == null) {
            System.out.println("Nicio comanda valida nu a fost introdusa!");
        } else {
            command.doIt();
        }

        //actualizez fisierele
        Database.actualize();
    }
}

