package com.diguage.books.concurrencycookbook.chapter3.recipe3;

import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch示例
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-05
 * Time: 13:07
 */
public class Participant implements Runnable {
    private VideoConference videoConference;
    private String name;

    public Participant(VideoConference videoConference, String name) {
        this.videoConference = videoConference;
        this.name = name;
    }


    @Override
    public void run() {
        long duration = (long) (Math.random() * 10);
        try {
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        videoConference.arrive(name);
    }
}
