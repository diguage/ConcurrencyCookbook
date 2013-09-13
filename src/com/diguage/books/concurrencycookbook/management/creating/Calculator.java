package com.diguage.books.concurrencycookbook.management.creating;

/**
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-13
 * Time: 21:42
 */
public class Calculator implements Runnable {
    private int number;

    public Calculator(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.printf("%s: %d * %d = %d\n", Thread.currentThread().getName(),
                    number, i, i * number);
        }
    }
}
