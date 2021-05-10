package com.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Route extends Object implements Cloneable, Comparable, Serializable {
    private static int count_id = 0;
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private ZonedDateTime creationDate = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("Asia/Tokyo")); //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Location from; //Поле может быть null
    private Location to; //Поле может быть null
    private Double distance; //Поле не может быть null, Значение поля должно быть больше 1

    public Route(String name, Coordinates coordinates, Location from, Location to, Double distance){
        this.name = name;
        this.coordinates = coordinates;
        this.from = from;
        this.to = to;
        this.distance = distance;
        count_id++;
        this.id = count_id;
    }

    public Route(Integer id, String name, Coordinates coordinates, Location from, Location to, Double distance){
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public Route(){
        count_id++;
        id = count_id;
    }

    /**
     * Устанавливает значение поля coordinates
     * @param coordinates Передаваемый объект типа Coordinates
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Устанавливает значение поля distance
     * @param distance Передаваемый объект типа Double
     */
    public void setDistance(Double distance) {
        this.distance = distance;
    }

    /**
     * Устанавливает значение поля from
     * @param from Передаваемый объект типа Location
     */
    public void setFrom(Location from) {
        this.from = from;
    }

    /**
     * Устанавливает значение поля name
     * @param name Передаваемый объект типа String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Устанавливает значение поля to
     * @param to Передаваемый объект типа Location
     */
    public void setTo(Location to) {
        this.to = to;
    }

    /**
     * Устанавливает значение поля creationDate
     * @param creationDate Передаваемый объект типа ZonedDateTime
     */
    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Устанавливает значение поля id
     * @param id Передаваемый объект типа int
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Возвращает значение поля name
     * @return Возвращаемое поле
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает значение поля coordinates
     * @return Возвращаемое поле
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Возвращает значение поля id
     * @return Возвращаемое поле
     */
    public int getId() {
        return id;
    }

    /**
     * Возвращает значение поля from
     * @return Возвращаемое поле
     */
    public Location getFrom() {
        return from;
    }

    /**
     * Возвращает значение поля to
     * @return Возвращаемое поле
     */
    public Location getTo() {
        return to;
    }

    /**
     * Возвращает значение поля distance
     * @return Возвращаемое поле
     */
    public Double getDistance() {
        return distance;
    }

    /**
     * Возвращает значение поля creationDate
     * @return Возвращаемое поле
     */
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Метод очищающий все данные
     */
    public void clear() {
        count_id++;
        id = count_id + 1;
        creationDate = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("Asia/Tokyo"));
    }

    /**
     * Создание копии объекта
     * @return Объект класса Route
     * @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString(){
        return "name : " + name + "\nid : " + String.valueOf(id) + "\ncoordinates : " + String.valueOf(coordinates.x) + ' ' + String.valueOf(coordinates.y) + ' ' + String.valueOf(coordinates.z) + "\ncreationdate : " + creationDate.toString() + "\nfrom : " + from.getName() + "\nto : " + to.getName() + "\ndistance : " + String.valueOf(distance) + '\n';
    }

    /**
     * Возвращает строку в формате CSV
     * @return Строка в формате CSV
     */
    public String toCSV(){
        System.out.println(id);
        System.out.println(name);
        System.out.println(coordinates.toString());
        System.out.println(creationDate.toString());
        System.out.println(from.getName());
        System.out.println(to.getName());
        System.out.println(distance);
        return String.valueOf(id) + "," + name + "," + String.valueOf(coordinates.x) + ' ' + String.valueOf(coordinates.y) + ' ' + String.valueOf(coordinates.z) + "," + creationDate.toString() + "," + from.getName() + "," + to.getName() + "," + String.valueOf(distance);
    }

    @Override
    public int compareTo(Object o){
        Route entry = (Route)o;

        Double result = distance - entry.distance;
        if (result != 0){
            return (int)(result / Math.abs(result));
        }
        return 0;
    }
}
