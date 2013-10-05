package com.diguage.books.concurrencycookbook.chapter3.recipe6;

import java.util.Date;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * 自定义Phaser示例
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-05
 * Time: 15:31
 */
public class Student implements Runnable {
    private Phaser phaser;

    public Student(Phaser phaser) {
        this.phaser = phaser;
    }

    @Override
    public void run() {
        System.out.printf("%s: Has arrived to do the exam. %s\n",
                Thread.currentThread().getName(),
                new Date());
        phaser.arriveAndAwaitAdvance();
        System.out.printf("%s: Is going to do the first exercise. %s\n",
                Thread.currentThread().getName(),
                new Date());
        doExercise1();
        System.out.printf("%s: Has done the first exercise. %s\n",
                Thread.currentThread().getName(),
                new Date());
        phaser.arriveAndAwaitAdvance();
        System.out.printf("%s: Is going to do the second exercise. %s\n",
                Thread.currentThread().getName(),
                new Date());

        doExercise2();
        System.out.printf("%s: Has done the second exercise. %s\n",
                Thread.currentThread().getName(),
                new Date());
        phaser.arriveAndAwaitAdvance();
        System.out.printf("%s: Is going to do the third exercise. %s\n",
                Thread.currentThread().getName(),
                new Date());

        doExercise3();
        System.out.printf("%s: Has finished the exam. %s\n",
                Thread.currentThread().getName(),
                new Date());
        phaser.arriveAndAwaitAdvance();
    }

    private void doExercise1() {
        try {
            Long duration = (long) (Math.random() * 10);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void doExercise2() {
        try {
            Long duration = (long) (Math.random() * 10);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void doExercise3() {
        try {
            Long duration = (long) (Math.random() * 10);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
