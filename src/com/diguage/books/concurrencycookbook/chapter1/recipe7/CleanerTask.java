package com.diguage.books.concurrencycookbook.chapter1.recipe7;

import java.util.Date;
import java.util.Deque;

/**
 * 事件清理
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-19
 * Time: 23:33
 */
public class CleanerTask extends Thread {
    private Deque<Event> deque;

    public CleanerTask(Deque<Event> deque) {
        this.deque = deque;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (true) {
            Date date = new Date();
            clean(date);
        }
    }

    /**
     * 删除事件。
     *
     * @param date
     */
    private void clean(Date date) {
        long difference;
        boolean delete;

        if (deque.size() == 0) {
            return;
        }

        delete = false;
        do {
            Event e = deque.getLast();
            difference = date.getTime() - e.getDate().getTime();
            if (difference > 10000) {
                System.out.printf("Cleaner: %s\n", e.getDate());
                deque.removeLast();
                delete = true;
            }
        } while (difference > 10000);

        if (delete) {
            System.out.printf("Clearner: Size of the queue: %d\n", deque.size());
        }
    }
}
