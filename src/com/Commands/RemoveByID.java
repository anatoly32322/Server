package com.Commands;

import com.CollectionManager;
import com.Data.Route;

import java.io.IOException;

public class RemoveByID {
    public RemoveByID(){}

    public void execute(int id) throws IOException {
        CollectionManager collectionManager = new CollectionManager();
        for (Route i : collectionManager.getData()) {
            if (i.getId() == id) {
                collectionManager.getData().removeFirstOccurrence(i);
                break;
            }
        }
        Save save = new Save();
        save.execute();
    }
}
