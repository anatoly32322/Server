package com.Data;

public enum Location {
    RUSSIA (new Coordinates((long)1, 2.0, 3.0), "Russia"),
    USA (new Coordinates((long)2, 3.0, 10.0), "USA"),
    NORWAY (new Coordinates((long)50, 64.6, 102.2), "Norway"),
    FRANCE (new Coordinates((long)104, 85.3, 99.0), "France"),
    ITALY (new Coordinates((long)545, 92.7, 87.1), "Italy"),
    NULL (new Coordinates((long)0, 0.0, 0.0), "null");

    private Coordinates coordinates;
    private String title;
    private String name; //Длина строки не должна быть больше 304, Поле может быть null

    Location (Coordinates coordinates, String name){
        this.name = name;
        this.coordinates = coordinates;
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
     * Этот метод изменяет поля x, y, z класса
     * @param x Параметр x
     * @param y Параметр y
     * @param z Параметр z
     */
    public void changeCoordinates(Long x, Double y, Double z){
        coordinates.x = x;
        coordinates.y = y;
        coordinates.z = z;
    }
}
