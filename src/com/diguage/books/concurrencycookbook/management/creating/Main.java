package com.diguage.books.concurrencycookbook.management.creating;

/**
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-13
 * Time: 19:46
 */
public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Calculator calculator = new Calculator(i);
            Thread thread = new Thread(calculator);
            thread.start();
        }
    }
}
