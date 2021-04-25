package com.Commands;

import com.CollectionManager;
import com.Data.Route;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Save {
    public Save(){}

    public void execute(String path) throws IOException {
        CollectionManager collectionManager = new CollectionManager();
        try {
            FileOutputStream out = new FileOutputStream(path);
            String text = "";
            for (Route rt : collectionManager.getData()) {
                System.out.println(rt.toCSV());
                text = rt.toCSV() + '\n';
                byte[] buffer = text.getBytes();
                for (byte i : buffer) {
                    out.write(i);
                }
            }
            out.close();
            return;
        } catch (FileNotFoundException e){
            System.out.println("Нету доступа к записи в файл.");

        }
    }
}
