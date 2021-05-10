package com.Commands;

import com.CollectionManager;
import com.Data.ExecuteRequest;
import com.Data.Response;
import com.Data.Route;

import java.io.IOException;
import java.util.ArrayDeque;

public class RemoveByID {
    public RemoveByID(){}

    public void execute(int id) throws IOException {
        CollectionManager collectionManager = new CollectionManager();
        ArrayDeque<Route> data = collectionManager.getData();
        if (!data.remove(data.stream().filter(x -> x.getId() == id).findFirst().orElse(null))){
            ExecuteRequest.answer.append("Элемент с данным id не найден");
        }
        else{
            collectionManager.remove(id);
            ExecuteRequest.answer.append("Элемент успешно удалён!");
        }
        Save save = new Save();
        save.execute();
    }
}
