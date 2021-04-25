package com.Server;

import com.CollectionManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class App {
    public static void main(String[] args) {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Server is running");

        Server server = new Server(5457, br);
        server.run();
    }
}