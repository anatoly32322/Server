package com.Commands;

import com.CollectionManager;
import com.Data.Route;

public class RemoveByID {
    public RemoveByID(){}

    public void execute(int id){
        CollectionManager collectionManager = new CollectionManager();
        for (Route i : collectionManager.getData()) {
            if (i.getId() == id) {
                collectionManager.getData().removeFirstOccurrence(i);
                break;
            }
        }
    }
}
