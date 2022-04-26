package com.example.login.exception;

public class RoleNotFoundException extends Exception{
    public RoleNotFoundException() {
        super("Role is not exist");
    }

}
