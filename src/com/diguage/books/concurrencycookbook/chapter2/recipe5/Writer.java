package com.diguage.books.concurrencycookbook.chapter2.recipe5;

import java.util.concurrent.TimeUnit;

/**
 * ReadWriteLock示例：修改产品价格
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-05
 * Time: 11:17
 */
public class Writer implements Runnable {
    private PricesInfo pricesInfo;

    public Writer(PricesInfo pricesInfo) {
        this.pricesInfo = pricesInfo;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            System.out.println("Writer: Attempt to modify the prices.");
            pricesInfo.setPrices(Math.random() * 10, Math.random() * 8);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
