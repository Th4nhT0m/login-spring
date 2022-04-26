package com.example.login.exception;

public class UserNotFoundException extends Exception{
    public UserNotFoundException() {
        super("User is not exist");
    }
}
