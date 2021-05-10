package com.AuxiliaryCommands;

import com.CollectionManager;
import com.Data.Route;

public class IsMax {
    private static CollectionManager collectionManager = new CollectionManager();

    public IsMax(){}

    public static Boolean execute(Route route){
        if (route.compareTo(collectionManager.getData().getLast()) < 0) {
            return true;
        }
        return false;
    }
}
