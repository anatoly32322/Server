package com.Commands;

import com.CollectionManager;
import com.Data.Route;
import com.Data.WorkWithRequest.ExecuteRequest;

import java.util.ArrayDeque;

public class Show {
    CollectionManager collectionManager = new CollectionManager();
    public Show() {}

    public String execute(Boolean isForRequest){
        String text = collectionManager.toString();
        if (isForRequest) {
            ExecuteRequest.answer.append(text);
            return text;
        }
        else {
            return text;
        }
    }
}
