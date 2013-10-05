package com.diguage.books.concurrencycookbook.chapter2.recipe5;

/**
 * ReadWriteLock示例：读取价格信息
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-05
 * Time: 11:14
 */
public class Reader implements Runnable {
    private PricesInfo pricesInfo;

    public Reader(PricesInfo pricesInfo) {
        this.pricesInfo = pricesInfo;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.printf("%s: Price 1: %f\n",
                    Thread.currentThread().getName(),
                    pricesInfo.getPrice1());
            System.out.printf("%s: Price 2: %f\n",
                    Thread.currentThread().getName(),
                    pricesInfo.getPrice2());
        }
    }
}
