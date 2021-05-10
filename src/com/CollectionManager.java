package com;

import com.Commands.ShowRoute;
import com.Data.Route;
import com.Server.DataBase;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayDeque;
import java.util.Arrays;

public class CollectionManager {
    private static ArrayDeque<Route> data = new ArrayDeque<>();
    private ZonedDateTime date = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("Asia/Tokyo"));

    public CollectionManager(){}

    public ArrayDeque<Route> getData() {
        return data;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setData(ArrayDeque<Route> data) {
        this.data = data;
    }

    public void addInCollection(Route a){
        data.addLast(a);
        sort();
    }

    private void sort() {
        Route[] arr = new Route[data.size()];
        Integer cnt = 0;
        for (Route i : data) {
            arr[cnt] = i;
            cnt++;
        }
        Arrays.sort(arr);
        data.clear();
        for (Route i : arr) {
            data.addLast(i);
        }
    }

    public void clear(){
        data.clear();
    }

    public void remove(int id){
        for (Route i : data) {
            if (i.getId() == id) {
                data.removeFirstOccurrence(i);
                break;
            }
        }
    }

    @Override
    public String toString(){
        String s = "";
        ShowRoute showRoute = new ShowRoute();
        for (Route i : data){
            s += showRoute.execute(i) + "\n";
        }
        return s;
    }
}
