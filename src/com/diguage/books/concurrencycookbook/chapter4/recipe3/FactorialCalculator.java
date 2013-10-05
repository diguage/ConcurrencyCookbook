package com.diguage.books.concurrencycookbook.chapter4.recipe3;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 带返回值的Executor示例：阶乘计算器
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-05
 * Time: 22:53
 */
public class FactorialCalculator implements Callable<Integer> {
    private Integer number;

    public FactorialCalculator(Integer number) {
        this.number = number;
    }

    @Override
    public Integer call() throws Exception {
        int result = 1;
        if ((number == 0) || (number == 1)) {
            result = 1;
        } else {
            for (int i = 2; i <= number; i++) {
                result *= i;
                TimeUnit.MILLISECONDS.sleep(20);
            }
        }
        System.out.printf("%s: %d\n",
                Thread.currentThread().getName(),
                result);
        return result;
    }
}
