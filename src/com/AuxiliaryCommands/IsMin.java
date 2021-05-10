package com.AuxiliaryCommands;

import com.CollectionManager;
import com.Data.Route;

public class IsMin {
    private static CollectionManager collectionManager = new CollectionManager();

    public IsMin(){}

    public static Boolean execute(Route route){
        if (route.compareTo(collectionManager.getData().getLast()) > 0) {
            return true;
        }
        return false;
    }
}
