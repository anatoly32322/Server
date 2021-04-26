package com.Server;

import com.AuxiliaryCommands.ReadCSV;
import com.CollectionManager;
import com.Commands.Execute;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Server is running");
        String str = br.readLine();
        String path;
        if (str.equals("1")){
            path = "C:\\Users\\User\\IdeaProjects\\Server\\src\\Saves\\input.csv";
        }
        else {
            String env = "INPUT";
            path = System.getenv(env);
        }
        Execute.path = path;
        ReadCSV readCSV = new ReadCSV();
        readCSV.execute();
        Server server = new Server(5457, br, path);
        server.run();
    }
}