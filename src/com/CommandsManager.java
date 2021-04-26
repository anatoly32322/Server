package com;

import com.AuxiliaryCommands.*;
import com.Commands.*;
import com.Data.Coordinates;
import com.Data.Route;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Это класс управляющий коллекцией
 */
public class CommandsManager {
    /**
     * Коллекция
     */
    private CollectionManager collectionManager = new CollectionManager();

    /**
     * Путь до файла
     */
    private String path;

    public CommandsManager(String path) {
        this.path = path;
    }

    /**
     * Этот метод возвращает справку обо всех методах данного класса
     */
    private void help() {
        Help hl = new Help();
        hl.execute();
    }

    /**
     * Этот метод возвращает информацию о коллекции: название, время создания и т.д.
     * @throws IllegalAccessException
     */
    private void info() throws IllegalAccessException {
        Info info = new Info();
        info.execute();
    }

    /**
     * Этот метод возвращает значение всех данных, хранящихся в коллекции
     * @return
     * @throws IllegalAccessException
     */
    private String show() throws IllegalAccessException {
        Show show = new Show();
        return show.execute(true);
    }

    /**
     * Этот метод обновляет значение элемента коллекции, id которого равен данному
     * @param id Передаваемый id
     * @param a Передаваемый объект класса Route
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private void updateByID(int id, Route a) throws NoSuchFieldException, IllegalAccessException, IOException {
        Update update = new Update();
        update.execute(id, a);
    }

    /**
     * Этот метод удаляет элемент из коллекции по его id
     * @param id Передаваемый id
     */
    private void remove_by_id(int id) throws IOException {
        RemoveByID removeByID = new RemoveByID();
        removeByID.execute(id);
    }

    /**
     * Этот метод сохраняет все элементы коллекции в файл
     * @throws IOException
     */
    private void save() throws IOException {
        Save save = new Save();
        save.execute();
    }



    /**
     * Этот метод завершает программу
     */
    private void exit() {
        System.exit(0);
    }

    /**
     * Этот метод добавляет элемент в коллекцию, если его значение поля distance превышает наибольшее значение элемента коллекции
     * @param a Передаваемый объект класса Route
     */
    private void add_if_max(Route a) throws IOException {
        AddIfMax addIfMax = new AddIfMax();
        addIfMax.execute(a);
    }

    public void clear(){
        collectionManager.clear();
    }
    /**
     * Этот метод добавляет элемент в коллекцию, если его значение поля distance меньше, чем наименьшее значение элемента коллекции
     * @param a Передаваемый объект класса Route
     */
    private void add_if_min(Route a) throws IOException {
        AddIfMin addIfMin = new AddIfMin();
        addIfMin.execute(a);
    }

    /**
     * Этот метод удаляет элементы из коллекции, меньшие, чем заданный
     * @param a Передаваемый объект класса Route
     */
    private void remove_lower(Route a) throws IOException {
        RemoveLower removeLower = new RemoveLower();
        removeLower.execute(a);
    }

    /**
     * Этот метод выводит значение элемента коллекции с нименьшим значением поля id
     * @throws IllegalAccessException
     */
    private void min_by_id() throws IllegalAccessException {
        MinByID minByID = new MinByID();
        minByID.execute();
    }

    /**
     * Этот метод группирует элементы коллекции по значению поля distance
     */
    private void group_counting_by_distance() {
        GroupCountingByDistance groupCountingByDistance = new GroupCountingByDistance();
        groupCountingByDistance.execute();
    }

    /**
     * Этот метод выводит количество элементов, значение поля distance которых равно заданному
     * @param dist Передаваемое значение поля distance
     */
    private void count_by_distance(long dist) {
        CountByDistance countByDistance = new CountByDistance();
        countByDistance.execute(dist);
    }


    /**
     * Вычисление расстояния между двумя точками пространства
     * @param a Координаты первой точки
     * @param b Координаты второй точки
     * @return Возвращает значение типа Double
     */
    private Double calculateDistance(Coordinates a, Coordinates b) {
        CalculateDistance calculateDistance = new CalculateDistance();
        return calculateDistance.execute(a, b);
    }

    /**
     * Считывает данные с входного потока и обрабатывает их, преобразуя в объект типа Route
     * @param br Пермененная считывающая поток входных данных
     * @return Возвращает объект типа Route
     */
    private Route getRoute(BufferedReader br) {
        GetRoute getRoute = new GetRoute();
        return getRoute.execute(br);
    }

    private Route getRouteScript(BufferedReader br) {
        GetRouteScript getRouteScript = new GetRouteScript();
        return getRouteScript.execute(br);
    }

    /**
     * Обрабатывает файл формата CSV
     */
    private void readCSVFile(String path) throws IOException {
        ReadCSV readCSV = new ReadCSV();
        readCSV.execute();
    }

    public void input(String path)  {
        this.path = path;
        Input input = new Input();
        input.execute(path);
    }
}
