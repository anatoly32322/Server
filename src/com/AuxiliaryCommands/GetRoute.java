package com.AuxiliaryCommands;

import com.Data.Coordinates;
import com.Data.Location;
import com.Data.Route;
import com.Exceptions.WrongInputException;

import java.io.BufferedReader;
import java.io.IOException;

public class GetRoute {
    public GetRoute(){}

    public Route execute(BufferedReader br){
        String line = "";
        String[] fields = new String[]{"name", "coordinates", "from", "to"};
        Route rt = new Route();
        try {
            for (String field : fields) {
                if (field.equals("name")) {
                    System.out.print("Введите значение поля name: ");
                    System.out.print('\n');
                    while (true){
                        try {
                            line = br.readLine();
                            if (line.length() == 0){
                                throw new WrongInputException("Поле name должно быть заполнено. Повторите попытку");
                            }
                            rt.setName(line);
                            break;
                        } catch (WrongInputException e){
                            e.printStackTrace();
                        }
                    }
                } else if (field.equals("coordinates")) {
                    System.out.print("Введите значение поля coordinates по следующему шаблону: x y z, - где x - Integer; y - Double; z - Double: ");
                    System.out.print('\n');
                    while (true){
                        try {
                            line = br.readLine();
                            String[] xyz = line.split(" ");
                            if (xyz.length != 3){
                                throw new WrongInputException("Неверный формат ввода. Повторите попытку");
                            }
                            Long x = Long.parseLong(xyz[0]);
                            Double y = Double.parseDouble(xyz[1]);
                            Double z = Double.parseDouble(xyz[2]);
                            rt.setCoordinates(new Coordinates(x, y, z));
                            break;
                        } catch (WrongInputException e){
                            e.printStackTrace();
                        }
                    }
                } else if (field.equals("from")) {
                    System.out.print("Введите значение поля from. Оно может иметь следующие значения: " + java.util.Arrays.asList(Location.values()));
                    System.out.print('\n');
                    while (true) {
                        try {
                            line = br.readLine();
                            line = line.toLowerCase();
                            switch (line) {
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
                                    if (line.length() != 0){
                                        throw new WrongInputException("Неверно введены данные. Повторите попытку");
                                    }
                                    rt.setFrom(Location.NULL);
                            }
                            break;
                        } catch (WrongInputException e){
                            e.printStackTrace();
                        }
                    }
                } else if (field.equals("to")) {
                    System.out.print("Введите значение поля to. Оно может иметь следующие значения: " + java.util.Arrays.asList(Location.values()));
                    System.out.print('\n');
                    while (true) {
                        try {
                            line = br.readLine();
                            line = line.toLowerCase();
                            switch (line) {
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
                                default:
                                    if (line.length() != 0){
                                        throw new WrongInputException("Неверно введены данные. Повторите попытку");
                                    }
                                    rt.setTo(Location.NULL);
                            }
                            break;
                        } catch (WrongInputException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
            CalculateDistance calculateDistance = new CalculateDistance();
            rt.setDistance(calculateDistance.execute(rt.getCoordinates(), rt.getTo().getCoordinates()));
            System.out.println(rt.getDistance());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rt;
    }
}
