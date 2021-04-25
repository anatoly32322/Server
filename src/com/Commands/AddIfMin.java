package com.Commands;

import com.CollectionManager;
import com.Data.Route;

public class AddIfMin {
    public AddIfMin(){}

    public void execute(Route a){
        CollectionManager collectionManager = new CollectionManager();
        Double dist = a.getDistance();
        if (a.compareTo(collectionManager.getData().getFirst()) > 0) {
            collectionManager.addInCollection(a);
        }
    }
}
