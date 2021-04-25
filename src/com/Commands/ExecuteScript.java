package com.Commands;

import com.AuxiliaryCommands.GetRouteScript;
import com.CollectionManager;
import com.Data.Route;
import com.Exceptions.WrongInputException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ExecuteScript {
    public ExecuteScript(){}

    public void execute(BufferedReader br, String PATH){
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
                            Help help = new Help();
                            help.execute();
                            break;
                        case "info":
                            Info info = new Info();
                            info.execute(collectionManager.getData(), collectionManager.getDate(), show.execute(collectionManager.getData()));
                            break;
                        case "show":
                            System.out.println(show.execute(collectionManager.getData()));
                            break;
                        case "add":
                            rt = getRouteScript.execute(br);
                            collectionManager.addInCollection(rt);
                            break;
                        case "update":
                            rt = getRouteScript.execute(br);
                            Update update = new Update();
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
                            save.execute(PATH);
                            break;
                        case "exit":
                            Exit exit = new Exit();
                            exit.execute();
                            break;
                        case "add_if_max":
                            AddIfMax addIfMax = new AddIfMax();
                            rt = getRouteScript.execute(br);
                            addIfMax.execute(rt);
                            break;
                        case "add_if_min":
                            AddIfMin addIfMin = new AddIfMin();
                            rt = getRouteScript.execute(br);
                            addIfMin.execute(rt);
                            break;
                        case "remove_lower":
                            RemoveLower removeLower = new RemoveLower();
                            rt = getRouteScript.execute(br);
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
                        case "execute_script":
                            ExecuteScript executeScript = new ExecuteScript();
                            executeScript.execute(br, PATH);
                            break;
                        default:
                            throw new WrongInputException("Введена неверная команда.");
                    }
                }
                break;
            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден. Повторите ввод.");
                continue;
            } catch (IOException | IllegalAccessException | NoSuchFieldException | WrongInputException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }
}
