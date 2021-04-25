package com.Commands;

import com.CollectionManager;
import com.Data.Route;

import java.util.ArrayDeque;

public class RemoveLower {
    public RemoveLower(){}

    public void execute(Route a){
        CollectionManager collectionManager = new CollectionManager();
        Double dist = a.getDistance();
        ArrayDeque<Route> deq = new ArrayDeque<Route>();
        for (Route i : collectionManager.getData()) {
            if (i.getDistance() >= dist) {
                deq.addLast(i);
            }
        }
        collectionManager.setData(deq.clone());
    }
}
