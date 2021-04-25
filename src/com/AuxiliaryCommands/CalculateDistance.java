package com.AuxiliaryCommands;

import com.Data.Coordinates;

public class CalculateDistance {
    public CalculateDistance(){}

    public Double execute(Coordinates a, Coordinates b){
        Double dist = Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2) + Math.pow(a.z - b.z, 2));
        return dist;
    }
}
