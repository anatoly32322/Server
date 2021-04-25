package com.Data;

import java.io.Serializable;

public class Coordinates implements Serializable {
    public Long x; //Поле не может быть null
    public Double y; //Максимальное значение поля: 2, Поле не может быть null
    public Double z;
    public Coordinates(Long x, Double y, Double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString(){
        return String.valueOf(x) + " " + String.valueOf(y) + " " + String.valueOf(z);
    }
}
