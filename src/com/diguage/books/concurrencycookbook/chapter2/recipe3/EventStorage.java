package com.diguage.books.concurrencycookbook.chapter2.recipe3;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * User: D瓜哥，http://www.diguage.com/
 * Date: 13-9-13
 * Time: 下午12:55
 */
public class EventStorage {
    private int maxSize;
    private List<Date> storage;

    public EventStorage() {
        maxSize = 10;
        storage = new LinkedList<Date>();
    }

    public synchronized void set() {
        while (storage.size() == maxSize) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ((LinkedList) storage).offer(new Date());
        System.out.printf("Set: %d", storage.size());
        notifyAll();
    }

    public synchronized void get() {
        while (0 == storage.size()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("Get: %d %s", storage.size(), ((LinkedList<Date>) storage).poll());
        notifyAll();
    }
}
