package com.diguage.books.concurrencycookbook.chapter3.recipe4;

/**
 * CyclicBarrier示例
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-05
 * Time: 13:22
 */
public class Results {
    private int[] data;

    public Results(int size) {
        this.data = new int[size];
    }

    public void setData(int position, int value) {
        data[position] = value;
    }

    public int[] getData() {
        return data;
    }
}
