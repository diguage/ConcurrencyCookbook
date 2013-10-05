package com.diguage.books.concurrencycookbook.chapter3.recipe3;

/**
 * CountDownLatch示例
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-05
 * Time: 13:10
 */
public class Main {
    public static void main(String[] args) {
        VideoConference videoConference = new VideoConference(10);
        Thread threadConference = new Thread(videoConference);
        threadConference.start();

        for (int i = 0; i < 10; i++) {
            Participant p = new Participant(videoConference, "Participant " + i);
            Thread t = new Thread(p);
            t.start();
        }
    }
}
