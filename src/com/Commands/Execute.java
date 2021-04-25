package com.Commands;

import com.AuxiliaryCommands.GetRoute;
import com.AuxiliaryCommands.GetRouteScript;
import com.CollectionManager;
import com.Data.Request;
import com.Data.Route;
import com.Exceptions.ExitException;
import com.Exceptions.IllegalCommandException;
import com.Exceptions.WrongInputException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ref.Cleaner;

public class Execute {
    public Execute(boolean isFromFile, BufferedReader bufferedReader){
        execute(isFromFile, bufferedReader, null);
    }

    public static Request execute(boolean isFromFile, BufferedReader br, Route route){
        String path = "";
        String line = "";
        Show show = new Show();
        String[] fields = new String[]{"name", "coordinates", "from", "to"};
        GetRouteScript getRouteScript = new GetRouteScript();
        Route rt = new Route();
        CollectionManager collectionManager = new CollectionManager();
        while(true) {
            try {
                path = br.readLine();
                br = new BufferedReader(new FileReader(path));
                while ((line = br.readLine()) != null) {
                    line.replaceAll("\n", "");
                    line.trim();
                    String[] ln = line.split(" ");
                    switch (ln[0]) {
                        case "help":
                            if (ln.length == 1) {
                                Help help = new Help();
                                help.execute();
                            } else {
                                throw new IllegalCommandException("Unknown help_<...> command");
                            }
                            break;

                        case "info":
                            if (ln.length == 1) {
                                Info info = new Info();
                                info.execute();
                            } else {
                                throw new IllegalCommandException("Unknown info_<...> command");
                            }

                        case "show":
                            if (ln.length == 1) {
                                show.execute(true);
                            } else {
                                throw new IllegalCommandException("Unknown show_<...> command");
                            }
                        case "add":
                            if (ln.length == 1){
                                collectionManager.addInCollection(route);
                            } else {
                                throw new IllegalCommandException("Unknown show_<...> command");
                            }

                        case "update":
                            if (ln.length == 2){
                                Update update = new Update();
                                update.execute(Integer.parseInt(ln[1]), route);
                            } else {
                                throw new IllegalCommandException("Unknown show_<...> command");
                            }
                        case "remove_by_id":
                            if (ln.length == 2){
                                RemoveByID removeByID = new RemoveByID();
                                removeByID.execute(Integer.parseInt(ln[1]));
                            } else {
                                throw new IllegalCommandException("Unknown show_<...> command");
                            }
                        case "clear":
                            if (ln.length == 1){
                                collectionManager.clear();
                            } else {
                                throw new IllegalCommandException("Unknown show_<...> command");
                            }
                        case "exit":
                            throw new ExitException();
                        case "add_if_max":
                            if (ln.length == 1){
                                AddIfMax addIfMax = new AddIfMax();
                                addIfMax.execute(route);
                            } else {
                                throw new IllegalCommandException("Unknown show_<...> command");
                            }
                        case "add_if_min":
                            if (ln.length == 1){
                                AddIfMin addIfMin = new AddIfMin();
                                addIfMin.execute(route);
                            } else {
                                throw new IllegalCommandException("Unknown show_<...> command");
                            }
                        case "remove_lower":
                            if (ln.length == 1){
                                RemoveLower removeLower = new RemoveLower();
                                removeLower.execute(route);
                            } else {
                                throw new IllegalCommandException("Unknown show_<...> command");
                            }
                        case "min_by_id":
                            if (ln.length == 1){
                                MinByID minByID = new MinByID();
                                minByID.execute();
                            } else {
                                throw new IllegalCommandException("Unknown show_<...> command");
                            }
                        case "group_counting_by_distance":
                            if (ln.length == 1){
                                GroupCountingByDistance groupCountingByDistance = new GroupCountingByDistance();
                                groupCountingByDistance.execute();
                            } else {
                                throw new IllegalCommandException("Unknown show_<...> command");
                            }
                        case "count_by_distance":
                            if (ln.length == 2){
                                CountByDistance countByDistance = new CountByDistance();
                                countByDistance.execute(Long.parseLong(ln[1]));
                            } else {
                                throw new IllegalCommandException("Unknown show_<...> command");
                            }
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
