package com.example.project;

import java.util.HashMap;

public class CreateUser extends Command {
    private HashMap<String, String> info;

    public CreateUser(HashMap<String, String> info) {
        this.info = info;
    }

    private boolean isCorrect() {
        if (info.get("-u") == null) {
            System.out.println("{ 'status' : 'error', 'message' : 'Please provide username'}");
            return false;
        }

        if (info.get("-p") == null) {
            System.out.println("{ 'status' : 'error', 'message' : 'Please provide password'}");
            return false;
        }

        return true;
    }

    @Override
    public void doIt() {
        if (!isCorrect()) {
            return;
        }

        String name = info.get("-u");
        String pass = info.get("-p");

        if (Database.userExists(name)) {
            System.out.println("{ 'status' : 'error', 'message' : 'User already exists'}");
            return;
        }

        User user = new User(name, pass);

        // se creeaza userul
        // se foloseste metoda addUser din Database pentru adaugare
        Database.addUser(user);
        System.out.println("{'status' : 'ok', 'message' : 'User created successfully'}");
    }
}
