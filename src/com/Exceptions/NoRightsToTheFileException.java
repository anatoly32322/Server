package com.Exceptions;

public class NoRightsToTheFileException extends RuntimeException {
    public NoRightsToTheFileException(){
        super();
    }

    public NoRightsToTheFileException(String message){
        super(message);
    }
}
