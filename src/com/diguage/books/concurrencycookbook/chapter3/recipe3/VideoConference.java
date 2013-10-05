package com.diguage.books.concurrencycookbook.chapter3.recipe3;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch示例
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-05
 * Time: 13:01
 */
public class VideoConference implements Runnable {
    private CountDownLatch controller;

    public VideoConference(int number) {
        this.controller = new CountDownLatch(number);
    }

    public void arrive(String name) {
        System.out.printf("%s has arrived.", name);
        controller.countDown();
        System.out.printf("VideoConference: Waiting for %d participants.\n",
                controller.getCount());
    }

    @Override
    public void run() {
        System.out.printf("VideoConference: Initialization: %d participants.\n",
                controller.getCount());
        try {
            controller.await();
            System.out.println("VideoConference: All the participants have come\n");
            System.out.println("VideoConference: Let's start...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
