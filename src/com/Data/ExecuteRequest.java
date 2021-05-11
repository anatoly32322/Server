package com.Data;

import com.Commands.Execute;
import com.Data.Report;
import com.Data.ReportState;
import com.Data.Request;
import com.Data.Route;
import com.Exceptions.ExitException;
import com.Server.DataBase;

import java.io.BufferedReader;
import java.io.StringReader;
import java.lang.management.BufferPoolMXBean;

public abstract class ExecuteRequest implements Runnable {
    private static BufferedReader brOfCommands;
    public static StringBuilder answer = new StringBuilder();
    public static ReportState reportState = ReportState.OK;


    public static Report doingRequest(Request request, DataBase dbManager)  {
        System.out.println("Entering the command: " + request.getCommandName());

        StringBuilder fullRequest = new StringBuilder(request.getCommandName() + " " + request.getArgument());
        brOfCommands = new BufferedReader(new StringReader(fullRequest.toString()));

        ReportState stateAnswer = ReportState.OK;
        answer = new StringBuilder();
        try {
            Execute.execute(brOfCommands, request.getObjectArgument() == "null" ? null : (Route)request.getObjectArgument(), dbManager);
            stateAnswer = ReportState.OK;
        } catch (ExitException e) {
            stateAnswer = ReportState.SERVER_DIE;
            answer.append("Server isn't worked now");
            throw e;
        } catch (Exception e) {
            stateAnswer = ReportState.ERROR;
            answer.append(e.getMessage());
        }

        return makeReport(stateAnswer, answer);
    }

    public static Report makeReport(ReportState state, StringBuilder body) {
        Report reportToClient = new Report(state, body.toString(), reportState);

        return reportToClient;
    }
}