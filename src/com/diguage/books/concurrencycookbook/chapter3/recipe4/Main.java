package com.diguage.books.concurrencycookbook.chapter3.recipe4;

import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier示例：主类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-05
 * Time: 13:50
 */
public class Main {
    public static void main(String[] args) {
        final int ROWS = 10000;
        final int NUMBERS = 1000;
        final int SEARCH = 5;
        final int PARTICIPANTS = 5;
        final int LINES_PARTICIPANT = 2000;
        MatrixMock mock = new MatrixMock(ROWS, NUMBERS, SEARCH);

        Results results = new Results(ROWS);
        Grouper grouper = new Grouper(results);
        CyclicBarrier barrier = new CyclicBarrier(PARTICIPANTS, grouper);
        Searcher[] searcher = new Searcher[PARTICIPANTS];
        for (int i = 0; i < PARTICIPANTS; i++) {
            searcher[i] = new Searcher(i * PARTICIPANTS,
                    (i * LINES_PARTICIPANT) + LINES_PARTICIPANT,
                    mock, results, 5, barrier);

            Thread thread = new Thread(searcher[i]);
            thread.start();
        }
    }
}
