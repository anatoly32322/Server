package com.Commands;

import com.Data.ExecuteRequest;

import java.util.HashMap;
import java.util.Map;

public class Help {
    private HashMap<String, String> manual;

    {
        manual = new HashMap<>();
        manual.put("help", "Справка по доступным командам");
        manual.put("info", "Общая информация о коллекции");
        manual.put("show", "Вывод всех данных, хранящихся в коллекции");
        manual.put("add", "Добавление элемента в коллекцию");
        manual.put("update", "Обновление значения элемента, id которого равен заданному");
        manual.put("remove_by_id", "Удаление элемента коллекции по его id");
        manual.put("clear", "Очистка коллекции");
        manual.put("save", "Сохрание коллекции в файл");
        manual.put("execute_script", "Считать и исполнить скрипт из указанного файла");
        manual.put("exit", "Завершение работы программы");
        manual.put("add_if_max", "Добавить новый элемент в коллекцию, если его значение поля distant превышает значение наибольшего элемента коллекции");
        manual.put("add_if_min", "Добавить новый элемент в коллекцию, если его значение поля distant меньше, чем значение наименьшего элемента коллекции");
        manual.put("remove_lower", "Удалить из коллекции все элементы, меньшие, чем данный");
        manual.put("min_by_id", "Вывести элемень коллекции с наименьшим значением поля id");
        manual.put("group_counting_by_distance", "Группировка элементов коллекции по значению поля distance");
        manual.put("count_by_distance", "Вывод количества элементов коллекции, значение поля distance которых равно данному");
    }

    public Help() {}

    public void execute() {
        String report = "";
        for (Map.Entry<String, String> pair : manual.entrySet()) {
            String key = pair.getKey();
            String value = pair.getValue();
            report += key + " " + value + '\n';
            //System.out.println(key + " " + value);
        }
        ExecuteRequest.answer.append(report);
    }
}
