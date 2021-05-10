package com.AuxiliaryCommands;

import com.CollectionManager;
import com.Commands.Execute;
import com.Data.Coordinates;
import com.Data.Location;
import com.Data.Route;
import com.Exceptions.WrongInputException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadDB {
    CollectionManager collectionManager = new CollectionManager();

    public ReadDB(){}

    public void execute() throws IOException {
        String path = Execute.path;
        String[] fields = new String[]{"id", "name", "coordinates", "creationDate", "from", "to", "distance"};
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = "";
            while ((line = br.readLine()) != null) {
                line.replaceAll("\r\n", "");
                Route rt = new Route();
                String[] arr = line.split(",");
                //System.out.println(arr[0] + " " + arr[1] + " " + arr[2] + " " + arr[3] + " " + arr[4] + " " + arr[5] + " " + arr[6]);
//                for (String i : arr){
//                    System.out.println(i);
//                }
                for (int i = 0; i < 7; i++) {
                    switch (i) {
                        case 0:
                            int id = Integer.parseInt(arr[0]);
                            rt.setId(id);
                            break;
                        case 1:
                            rt.setName(arr[1]);
                            break;
                        case 2:
                            String[] coord = arr[2].split(" ");
                            Coordinates coordinates = new Coordinates(Long.parseLong(coord[0]), Double.parseDouble(coord[1]), Double.parseDouble(coord[2]));
                            rt.setCoordinates(coordinates);
                            break;
                        case 4:
                            arr[4] = arr[4].toLowerCase();
                            switch (arr[4]) {
                                case "russia":
                                    rt.setFrom(Location.RUSSIA);
                                    break;
                                case "usa":
                                    rt.setFrom(Location.USA);
                                    break;
                                case "norway":
                                    rt.setFrom(Location.NORWAY);
                                    break;
                                case "italy":
                                    rt.setFrom(Location.ITALY);
                                    break;
                                case "france":
                                    rt.setFrom(Location.FRANCE);
                                    break;
                                default:
                                    throw new WrongInputException("Неправильные данные");
                            }
                            break;
                        case 5:
                            arr[5] = arr[5].toLowerCase();
                            switch (arr[5]) {
                                case "russia":
                                    rt.setTo(Location.RUSSIA);
                                    break;
                                case "usa":
                                    rt.setTo(Location.USA);
                                    break;
                                case "norway":
                                    rt.setTo(Location.NORWAY);
                                    break;
                                case "italy":
                                    rt.setTo(Location.ITALY);
                                    break;
                                case "france":
                                    rt.setTo(Location.FRANCE);
                                    break;
                                default:
                                    throw new WrongInputException("Неправильные данные");
                            }
                            break;
                        case 6:
                            arr[6].trim();
                            Double dist = Double.parseDouble(arr[6]);
                            rt.setDistance(dist);
                            break;
                    }
                }
                collectionManager.addInCollection(rt);
                System.out.println(rt.toString());
            }
        } catch (WrongInputException e){
            e.printStackTrace();
            System.exit(0);
        }
    }
}
