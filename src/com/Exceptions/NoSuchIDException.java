package com.Exceptions;

public class NoSuchIDException extends RuntimeException {
    public NoSuchIDException(){
        super();
    }

    public NoSuchIDException(String message){
        super(message);
    }
}
