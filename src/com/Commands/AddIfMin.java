package com.Commands;

import com.CollectionManager;
import com.Data.Route;

import java.io.IOException;

public class AddIfMin {
    public AddIfMin(){}

    public void execute(Route a) throws IOException {
        CollectionManager collectionManager = new CollectionManager();
        Double dist = a.getDistance();
        if (a.compareTo(collectionManager.getData().getFirst()) > 0) {
            collectionManager.addInCollection(a);
        }
        Save save = new Save();
        save.execute();
    }
}
