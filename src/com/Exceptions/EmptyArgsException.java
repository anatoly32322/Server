package com.Exceptions;

public class EmptyArgsException extends RuntimeException {
    public EmptyArgsException(){
        super();
    }

    public EmptyArgsException(String message){
        super(message);
    }
}
