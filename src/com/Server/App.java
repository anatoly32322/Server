package com.Server;

import com.AuxiliaryCommands.ReadDB;
import com.Commands.Execute;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e){
            System.err.println("Необходимо скачать и установить драйвер PostgreSQL(https://jdbc.postgresql.org/download.html)");
            System.exit(-1);
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Server is running");
/*        String str = br.readLine();
        String path;
        if (str.equals("1")){
            path = "C:\\Users\\User\\IdeaProjects\\Server\\src\\Saves\\input.csv";
        }
        else {
            String env = "INPUT";
            path = System.getenv(env);
        }
        Execute.path = path;*/
//        ReadDB readDB = new ReadDB();
//        readDB.execute();
        Server server = new Server(5458, br);
        server.run();
    }
}