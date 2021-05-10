package com.Commands;

import com.CollectionManager;
import com.Data.Route;
import com.Data.ExecuteRequest;

public class GroupCountingByDistance {
    CollectionManager collectionManager = new CollectionManager();
    public GroupCountingByDistance(){}

    public void execute(){
        String text = "";
        int count = 0;
        Double dist = collectionManager.getData().getFirst().getDistance();
        for (Route i : collectionManager.getData()) {
            if (i.getDistance().equals(dist)) {
                count++;
            } else {
                text += "Distance = " + dist + " : " + count + '\n';
                count = 1;
                dist = i.getDistance();
            }
        }
        text += "Distance = " + dist + " : " + count + '\n';
        ExecuteRequest.answer.append(text);
//        System.out.println(text);
    }
}
