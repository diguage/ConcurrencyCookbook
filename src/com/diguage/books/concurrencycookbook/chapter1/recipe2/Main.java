package com.diguage.books.concurrencycookbook.chapter1.recipe2;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-13
 * Time: 19:51
 */
public class Main {
    public static void main(String[] args) {
        Thread[] threads = new Thread[10];
        Thread.State[] status = new Thread.State[threads.length];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Calculator(i));
            if ((i % 2) == 0) {
                threads[i].setPriority(Thread.MAX_PRIORITY);
            } else {
                threads[i].setPriority(Thread.MIN_PRIORITY);
            }
            threads[i].setName("Thread-" + i);
        }

        try (FileWriter file = new FileWriter("D:\\thread.log");
             PrintWriter pw = new PrintWriter(file)) {
            for (int i = 0; i < threads.length; i++) {
                Thread thread = threads[i];
                pw.println("Main: Status of Thread " + i +
                        " : " + threads[i].getState());
                status[i] = threads[i].getState();
            }

            for (int i = 0; i < threads.length; i++) {
                threads[i].start();
            }

            boolean finish = false;

            while (!finish) {
                for (int i = 0; i < threads.length; i++) {
                    if (threads[i].getState() != status[i]) {
                        writeThreadInfo(pw, threads[i], status[i]);
                        status[i] = threads[i].getState();
                    }
                }
                finish = true;
                for (int i = 0; i < threads.length; i++) {
                    finish = finish
                            && (threads[i].getState() == Thread.State.TERMINATED);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将一个线程的状态输出到一个文件中。
     *
     * @param pw     PrintWriter对象
     * @param thread 需要输出状态的线程对象
     * @param state  线程的旧状态
     */
    private static void writeThreadInfo(PrintWriter pw, Thread thread, Thread.State state) {
        pw.printf("Main : Id %d = %s\n", thread.getId(), thread.getName());
        pw.printf("Main : Priority: %d\n", thread.getPriority());
        pw.printf("Main : Old State: %s\n", state);
        pw.printf("Main : New State: %s\n", thread.getState());
        pw.printf("Main : ********************************\n");
    }
}
