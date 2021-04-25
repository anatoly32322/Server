package com.Commands;

import com.CollectionManager;
import com.Data.Route;
import com.Data.WorkWithRequest.ExecuteRequest;

import java.time.ZonedDateTime;
import java.util.ArrayDeque;

public class Info {
    private CollectionManager collectionManager = new CollectionManager();
    public Info() {}

    public void execute(){
        System.out.println(3);
        Class cl = collectionManager.getData().getClass();
        System.out.println(4);
        String report = "Название контейнера: " + cl.getName() + '\n' + "Время создания: " + collectionManager.getDate() + '\n' + "Данные хранимые в контейнере:\n" + collectionManager.toString() + '\n';
        System.out.println(report);
        ExecuteRequest.answer.append(report);
//        System.out.println("Название контейнера: " + cl.getName());
//        System.out.println("Время создания: " + collectionManager.getDate());
//        System.out.println("Данные хранимые в контейнере:\n" + collectionManager.toString());
    }
}
