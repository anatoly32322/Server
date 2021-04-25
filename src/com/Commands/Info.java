package com.Commands;

import com.CollectionManager;
import com.Data.Route;
import com.Data.WorkWithRequest.ExecuteRequest;

import java.time.ZonedDateTime;
import java.util.ArrayDeque;

public class Info {
    private CollectionManager collectionManager;
    public Info() {}

    public void execute(){
        Class cl = collectionManager.getClass();
        String report = "";
        report += "Название контейнера: " + cl.getName() + '\n';
        report += "Время создания: " + collectionManager.getDate() + '\n';
        report += "Данные хранимые в контейнере:\n" + collectionManager.toString() + '\n';
        ExecuteRequest.answer.append(report);
//        System.out.println("Название контейнера: " + cl.getName());
//        System.out.println("Время создания: " + collectionManager.getDate());
//        System.out.println("Данные хранимые в контейнере:\n" + collectionManager.toString());
    }
}
