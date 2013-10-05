package com.diguage.books.concurrencycookbook.chapter2.recipe7;

/**
 * 条件锁示例：模拟文件类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-05
 * Time: 11:35
 */
public class FileMock {
    private String[] content;
    private int index;

    public FileMock(int size, int length) {
        this.content = new String[size];
        for (int i = 0; i < size; i++) {
            StringBuilder buffer = new StringBuilder(length);
            for (int j = 0; j < length; j++) {
                int indice = (int) (Math.random() * 255);
                buffer.append(indice);
            }
            content[i] = buffer.toString();
        }
        this.index = 0;
    }

    // 检查是否到达文件尾部
    public boolean hasMoreLines() {
        return index < content.length;
    }

    // 获取当前行的内容
    public String getLine() {
        if (this.hasMoreLines()) {
            System.out.println("Mock: " + (content.length - index));
            return content[index++];
        }
        return null;
    }
}
