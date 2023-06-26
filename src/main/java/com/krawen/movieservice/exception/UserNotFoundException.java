package com.krawen.movieservice.exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String userName) {
        super("User not found : " + userName);
    }

}
