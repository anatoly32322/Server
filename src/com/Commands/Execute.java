package com.Commands;

import com.CollectionManager;
import com.CommandsManager;
import com.Data.Request;
import com.Data.Route;
import com.Exceptions.ExitException;
import com.Exceptions.IllegalCommandException;
import com.Exceptions.WrongInputException;
import com.Server.DataBase;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Execute {
    public static String path;


    public Execute(BufferedReader bufferedReader, String path, DataBase dbManager) throws IOException {
        execute(bufferedReader, null, dbManager);
    }

    public static Request execute(BufferedReader br, Route route, DataBase dbManager) throws IOException {
        String line = "";
        CommandsManager commandsManager = new CommandsManager();
        Show show = new Show();
        String[] fields = new String[]{"name", "coordinates", "from", "to"};
//        GetRouteScript getRouteScript = new GetRouteScript();
        Route rt = new Route();
        CollectionManager collectionManager = new CollectionManager();
        while(true) {
            try {
                while ((line = br.readLine()) != null) {
                    line = line.replaceAll("\n", "");
                    line = line.trim();
                    String[] ln = line.split(" ");
                    System.out.println(ln[0] + ' ' + ln.length);
                    switch (ln[0]) {
                        case "help":
                            if (ln.length == 1) {
                                commandsManager.help();
                            } else {
                                throw new IllegalCommandException("Unknown help_<...> command");
                            }
                            break;

                        case "info":
                            if (ln.length == 1) {
                                commandsManager.info();
                            } else {
                                throw new IllegalCommandException("Unknown info_<...> command");
                            }
                            break;

                        case "show":
                            if (ln.length == 1) {
                                commandsManager.show();
                            } else {
                                throw new IllegalCommandException("Unknown show_<...> command");
                            }
                            break;
                        case "add":
                            if (ln.length == 1){
                                dbManager.addRouteIntoDB(route, dbManager.getUsername());
                                commandsManager.add(route);
                            } else {
                                throw new IllegalCommandException("Unknown show_<...> command");
                            }
                            break;

                        case "update":
                            if (ln.length == 2){
                                dbManager.updateRouteByID(Integer.parseInt(ln[1]), route);
                                commandsManager.updateByID(Integer.parseInt(ln[1]), route);
                            } else {
                                throw new IllegalCommandException("Unknown show_<...> command");
                            }
                            break;
                        case "remove_by_id":
                            if (ln.length == 2){
                                commandsManager.remove_by_id(Integer.parseInt(ln[1]));
                            } else {
                                throw new IllegalCommandException("Unknown show_<...> command");
                            }
                            break;
                        case "clear":
                            if (ln.length == 1){
                                commandsManager.clear();
                            } else {
                                throw new IllegalCommandException("Unknown show_<...> command");
                            }
                            break;
                        case "exit":
                            throw new ExitException();
                        case "add_if_max":
                            if (ln.length == 1){
                                commandsManager.add_if_max(route);
                            } else {
                                throw new IllegalCommandException("Unknown show_<...> command");
                            }
                            break;
                        case "add_if_min":
                            if (ln.length == 1){
                                commandsManager.add_if_min(route);
                            } else {
                                throw new IllegalCommandException("Unknown show_<...> command");
                            }
                            break;
                        case "remove_lower":
                            if (ln.length == 1){
                                commandsManager.remove_lower(route);
                            } else {
                                throw new IllegalCommandException("Unknown show_<...> command");
                            }
                            break;
                        case "min_by_id":
                            if (ln.length == 1){
                                commandsManager.min_by_id();
                            } else {
                                throw new IllegalCommandException("Unknown show_<...> command");
                            }
                            break;
                        case "group_counting_by_distance":
                            if (ln.length == 1){
                                commandsManager.group_counting_by_distance();
                            } else {
                                throw new IllegalCommandException("Unknown show_<...> command");
                            }
                            break;
                        case "count_by_distance":
                            if (ln.length == 2){
                                commandsManager.count_by_distance(Long.parseLong(ln[1]));
                            } else {
                                throw new IllegalCommandException("Unknown show_<...> command");
                            }
                            break;
                        case "execute_script":
//                            ExecuteScript executeScript = new ExecuteScript();
//                            executeScript.execute(br, ln[1]);
                            break;
                        default:
                            throw new WrongInputException("Введена неверная команда.");
                    }
                }
                break;
            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден. Повторите ввод.");
                continue;
            } catch (IOException | WrongInputException e) {
                e.printStackTrace();
                System.exit(0);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return new Request("exit", "");
    }
}
