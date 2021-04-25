package com.Commands;

import com.CollectionManager;
import com.Data.Route;

import java.lang.reflect.Field;


public class Update {
    public Update() {}

    public void execute(Integer id, Route a) throws NoSuchFieldException, IllegalAccessException {
        CollectionManager collectionManager = new CollectionManager();
        for (Route i : collectionManager.getData()) {
            if (i.getId() == id) {
                Class cl = i.getClass();
                Field[] fields = cl.getDeclaredFields();
                for (Field j : fields) {
                    j.setAccessible(true);
                    j.set(i, a.getClass().getDeclaredField(j.getName()).get(a));
                    j.setAccessible(false);
                }
                break;
            }
        }
    }
}
