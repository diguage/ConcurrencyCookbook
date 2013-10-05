package com.diguage.books.concurrencycookbook.chapter3.recipe4;

/**
 * CyclicBarrier示例
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-05
 * Time: 13:48
 */
public class Grouper implements Runnable {
    private Results results;

    public Grouper(Results results) {
        this.results = results;
    }

    @Override
    public void run() {
        int finalResult = 0;
        System.out.println("Grouper: Processing results...");
        int[] data = results.getData();
        for (int i : data) {
            finalResult += i;
        }
        System.out.printf("Grouper: Total result: %d.\n", finalResult);

    }
}
