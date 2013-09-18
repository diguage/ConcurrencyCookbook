package com.diguage.books.concurrencycookbook.chapter1.recipe3;

/**
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-18
 * Time: 11:53
 */
public class PrimeGenerator extends Thread {

    @Override
    public void run() {
        long number = 1L;
        while (true) {
            if (isPrime(number)) {
                System.out.printf("Number %d \tis Prime.\n", number);
            }

            if (isInterrupted()) {
                System.out.println("The Prime Generator has been Interrupted");
                return;
            }

            number++;
        }
    }

    /**
     * 判断参数是否为素数
     *
     * @param number 需要判断的数字
     * @return
     */
    private boolean isPrime(long number) {
        if (number <= 2) {
            return true;
        }

        for (int i = 2; i < number; i++) {
            if ((number % i) == 0) {
                return false;
            }
        }

        return true;
    }
}
