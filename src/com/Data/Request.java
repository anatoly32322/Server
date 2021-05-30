package com.Data;


import java.io.Serializable;

public class Request implements Serializable {
    private String commandName;
    private String argument;
    private Serializable objectArgument;
    private String username;

    public Request(String commandName, String argument, String username, Serializable objectArgument){
        this.commandName = commandName;
        this.argument = argument;
        this.objectArgument = objectArgument;
        this.username = username;
    }

    public Request(String commandName, String argument, String username) {
        this.commandName = commandName;
        this.argument = argument;
        this.objectArgument = null;
        this.username = username;
    }

    public Request() {
        this.commandName = "";
        this.argument = "";
        this.objectArgument = null;
    }

    public Serializable getObjectArgument() {
        return objectArgument;
    }

    public String getArgument() {
        return argument;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getUsername() {
        return username;
    }

    public boolean isEmpty() {
        return commandName.isEmpty() && argument.isEmpty() && objectArgument == null;
    }
}

