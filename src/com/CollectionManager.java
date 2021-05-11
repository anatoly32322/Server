package com;

import com.Commands.ShowRoute;
import com.Data.Route;
import com.Server.DataBase;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CollectionManager {
    private static ArrayDeque<Route> data = new ArrayDeque<>();
    private ZonedDateTime date = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("Asia/Tokyo"));
    private Lock lock = new ReentrantLock();

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
        lock.lock();
        try {
            data.addLast(a);
            sort();
        } finally {
            lock.unlock();
        }
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
        lock.lock();
        try {
            data.clear();
        }
        finally {
            lock.unlock();
        }
    }

    public void remove(int id){
        lock.lock();
        try {
            for (Route i : data) {
                if (i.getId() == id) {
                    data.removeFirstOccurrence(i);
                    break;
                }
            }
        }
        finally {
            lock.unlock();
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
