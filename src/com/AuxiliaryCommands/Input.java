package com.AuxiliaryCommands;

import com.CollectionManager;
import com.Commands.*;
import com.Data.Route;
import com.Exceptions.WrongInputException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Input {
    CollectionManager collectionManager = new CollectionManager();

    public Input(){}

    public void execute(String PATH){
        try {
            ReadCSV readCSV = new ReadCSV();
            readCSV.execute();
        } catch(IOException e){
            e.printStackTrace();
            System.exit(0);
        }
        Show show = new Show();
        GetRoute getRoute = new GetRoute();
        String line = "";
        Route rt = new Route();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            try {
                while (true) {
                    line = br.readLine();
                    line.replaceAll("\n", "");
                    line.trim();
                    String[] ln = line.split(" ");
                    switch (ln[0]){
                        case "help":
                            Help help = new Help();
                            help.execute();
                            break;
                        case "info":
                            Info info = new Info();
                            info.execute();
                            break;
                        case "show":
                            System.out.println(show.execute(true));
                            break;
                        case "add":
                            rt = getRoute.execute(br);
                            collectionManager.addInCollection(rt);
                            break;
                        case "update":
                            Update update = new Update();
                            rt = getRoute.execute(br);
                            update.execute(Integer.parseInt(ln[1]), rt);
                            break;
                        case "remove_by_id":
                            RemoveByID removeByID = new RemoveByID();
                            removeByID.execute(Integer.parseInt(ln[1]));
                            break;
                        case "clear":
                            collectionManager.clear();
                            break;
                        case "save":
                            Save save = new Save();
                            save.execute();
                            break;
                        case "exit":
                            Exit exit = new Exit();
                            exit.execute();
                            break;
                        case "add_if_max":
                            AddIfMax addIfMax = new AddIfMax();
                            rt = getRoute.execute(br);
                            addIfMax.execute(rt);
                            break;
                        case "add_if_min":
                            AddIfMin addIfMin = new AddIfMin();
                            rt = getRoute.execute(br);
                            addIfMin.execute(rt);
                            break;
                        case "remove_lower":
                            RemoveLower removeLower = new RemoveLower();
                            rt = getRoute.execute(br);
                            removeLower.execute(rt);
                            break;
                        case "min_by_id":
                            MinByID minByID = new MinByID();
                            minByID.execute();
                            break;
                        case "group_counting_by_distance":
                            GroupCountingByDistance groupCountingByDistance = new GroupCountingByDistance();
                            groupCountingByDistance.execute();
                            break;
                        case "count_by_distance":
                            CountByDistance countByDistance = new CountByDistance();
                            countByDistance.execute(Long.parseLong(ln[1]));
                            break;
                        default:
                            throw new WrongInputException("Введена неверная команда. Повторите ввод.");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            } catch (WrongInputException e) {
                e.printStackTrace();
                continue;
            }
        }
    }
}
