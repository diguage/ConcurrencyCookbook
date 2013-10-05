package com.diguage.books.concurrencycookbook.chapter3.recipe6;

/**
 * 自定义Phaser示例
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-05
 * Time: 15:40
 */
public class Main {
    public static void main(String[] args) {
        MyPhaser phaser = new MyPhaser();
        Student[] students = new Student[5];
        for (int i = 0; i < students.length; i++) {
            students[i] = new Student(phaser);
            phaser.register();
        }

        Thread[] threads = new Thread[students.length];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(students[i], "Student " + i);
            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        System.out.printf("ain: The phaser has finished: %s.\n",
                phaser.isTerminated());
    }
}
