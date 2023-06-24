package com.krawen.movieservice.exception;

public class UserNameExistException extends Exception {

    public UserNameExistException(String userName) {
        super("This username is already in use : " + userName);
    }

}
