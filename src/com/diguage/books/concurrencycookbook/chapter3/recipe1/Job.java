package com.diguage.books.concurrencycookbook.chapter3.recipe1;

/**
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-14
 * Time: 10:32
 */
public class Job implements Runnable {
    private PrintQueue printQueue;

    public Job(PrintQueue printQueue) {
        this.printQueue = printQueue;
    }

    @Override
    public void run() {
        System.out.printf("%s: Going to print a job\n",
                Thread.currentThread().getName());
        printQueue.printJob(new Object());
        System.out.printf("%s: The document has been printed\n",
                Thread.currentThread().getName());

    }
}
