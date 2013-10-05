package com.diguage.books.concurrencycookbook.chapter4.recipe5;

/**
 * Executor取得所有线程的处理结果示例：结果类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-05
 * Time: 23:47
 */
public class Result {
    private String name;
    private int value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
