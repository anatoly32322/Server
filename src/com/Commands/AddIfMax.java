package com.Commands;

import com.CollectionManager;
import com.Data.Route;

public class AddIfMax {
    public AddIfMax(){}

    public void execute(Route a){
        CollectionManager collectionManager = new CollectionManager();
        Double dist = a.getDistance();
        if (a.compareTo(collectionManager.getData().getLast()) < 0) {
            collectionManager.addInCollection(a);
        }
    }
}
