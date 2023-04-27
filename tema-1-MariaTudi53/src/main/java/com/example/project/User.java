package com.example.project;

public class User {
    private String myUsername;
    private String myPassword;

    public User(String myUsername, String myPassword) {
        this.myUsername = myUsername;
        this.myPassword = myPassword;
    }

    public String getMyUsername() {
        return myUsername;
    }
    public void setMy_username(String myUsername) {
        this.myUsername = myUsername;
    }

    public String getMyPassword() {
        return myPassword;
    }

    public void setMyPassword(String myPassword) {
        this.myPassword = myPassword;
    }

    @Override
    public String toString() {
        return "User{" +
                "myUsername='" + myUsername + '\'' +
                ", myPassword='" + myPassword + '\'' +
                '}';
    }
}
