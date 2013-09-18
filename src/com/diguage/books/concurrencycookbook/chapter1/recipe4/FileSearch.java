package com.diguage.books.concurrencycookbook.chapter1.recipe4;

import java.io.File;

/**
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-18
 * Time: 18:21
 */
public class FileSearch implements Runnable {
    private String initPath;
    private String fileName;

    /**
     * 初始化构造函数
     *
     * @param initPath 需要进行查找的目录
     * @param fileName 需要查找的文件名称
     */
    public FileSearch(String initPath, String fileName) {
        this.initPath = initPath;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        File file = new File(initPath);
        if (file.isDirectory()) {
            try {
                directoryProcess(file);
            } catch (InterruptedException e) {
                System.out.printf("%s: The search has been interrupted",
                        Thread.currentThread().getName());
            }
        }
    }

    /**
     * 处理一个目录
     *
     * @param file 需要处理的目录
     * @throws InterruptedException
     */
    private void directoryProcess(File file) throws InterruptedException {
        File[] list = file.listFiles();
        if (null != list) {
            for (int i = 0; i < list.length; i++) {
                if (list[i].isDirectory()) {
                    directoryProcess(list[i]);
                } else {
                    fileProcess(list[i]);
                }

            }
        }
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
    }

    /**
     * 处理的文件
     *
     * @param file 需要处理的文件
     * @throws InterruptedException
     */
    private void fileProcess(File file) throws InterruptedException {
        if (file.getName().equals(fileName)) {
            System.out.printf("%s : %s\n",
                    Thread.currentThread().getName(),
                    file.getAbsolutePath());
        }

        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
    }
}
