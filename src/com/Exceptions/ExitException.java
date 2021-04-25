package com.Exceptions;


public class ExitException extends RuntimeException{
    public ExitException() {
        super("goodbye...");
    }
}
