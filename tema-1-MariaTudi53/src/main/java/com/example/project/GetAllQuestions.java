package com.example.project;

import java.util.ArrayList;
import java.util.HashMap;

public class GetAllQuestions extends Command{
    private HashMap<String, String> info;

    public GetAllQuestions(HashMap<String, String> info) {
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

        StringBuilder output = new StringBuilder("{'status':'ok','message':'[");

        String name = info.get("-u");
        ArrayList<Integer> userQuestions = Database.getUsersQuestions().get(name);
        for (Integer ID : userQuestions) {
            output.append("{\"question_id\" : \"").append(ID).append("\", \"question_name\" : \"").append(Database.getQuestions().get(ID).getText()).append("\"}, ");
        }
        output = new StringBuilder(output.substring(0, output.length() - 2));
        output.append("]'}");
        System.out.println(output);
    }
}
