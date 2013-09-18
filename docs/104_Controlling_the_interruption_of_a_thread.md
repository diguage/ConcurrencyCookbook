# Java7并发示例集104：可控的线程中断

在上一节[“线程中断”](./103_Interrupting_a_thread.md)中，我们讲解了如何中断一个正在执行的线程以及为了中断线程，我们必须对`Thread`动点什么手脚。一般情况下，我们可以使用上一节介绍的中断机制。但是，如果线程实现了一个分配到多个方法中的复杂算法，或者方法调用中有一个递归调用，我们应该使用更好的方式来控制线程的中断。为此，Java提供了`InterruptedException`异常。当检测到中断请求时，可以抛出此异常，并且在`run()`方法中捕获。

在本节，我们将使用一个线程查找指定目录及其子目录下文件来演示通过使用`InterruptedException`异常控制线程中断。


### 知其然

按照下面所示步骤，实现示例程序。

1. 创建一个名为`FileSearch`的类，并且实现`Runnable`接口。代码如下：
```Java
public class FileSearch implements Runnable {
```

2. 声明两个变量，一个用于需要查找的文件名，一个用于初始化查找的目录；实现类的构造函数，并用构造函数的参数初始化刚刚声明的两个变量。代码如下：
```Java
    private String initPath;
    private String fileName;

    public FileSearch(String initPath, String fileName) {
        this.initPath = initPath;
        this.fileName = fileName;
    }
```

3. 实现`run()`方法，该方法检查`fileName`是否一个路径名称。如果是，则调用`directoryProcess()`方法进行处理。`directoryProcess()`方法会抛出`InterruptedException`异常，所以我们需要捕获该异常。代码如下：
```Java
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
```
> 原文中，提到的方法名称为`processDirectory()`。但是，根据下文的程序，属于笔误。故改正。

4. 实现`directoryProcess()`方法。该方法读取指定目录下的所有文件以及子目录再进行处理。对于每一个目录，该方法进行一个递归调用，来处理参数指定的目录。对于每一个文件，该方法会调用`fileProcess()`方法。在处理完所有的目录以及文件后，该方法会检查线程是否被中断，这是抛出一个`InterruptedException`异常。代码如下：
```Java
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
```

5. 实现`fileProcess()`方法，该方法会比较正在处理的文件和需要查找的文件名。如果文件名称相等，则在控制台打印出一条信息。然后，线程检查是否被中断，如果是，则抛出`InterruptedException`异常。代码如下：
```Java
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
```

6. 现在，来实现示例的主类，并且实现`main()`方法。代码如下：
```Java
public class Main {
    public static void main(String[] args) {
```

7. 创建并初始化`FileSearch`对象，然后创建一个`Thread`对象，来执行该任务。然后，启动该线程。代码如下：
```Java
        FileSearch fileSearch = new FileSearch("C:\\", "autoexec.bat");
        Thread thread = new Thread(fileSearch);
        thread.start();
```

8. 等待十秒钟，然后中断线程。代码如下：
```Java
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread.interrupt();
```

9. 执行该示例，查看结果。代码如下：


### 知其所以然

下面是线程执行的结果。从输出中可以看出，当`FileSearch`检测到被中断后，如何中止线程执行的。

```Java
Thread-0 : C:\autoexec.bat
Thread-0: The search has been interrupted
```

本示例中，我们使用Java的异常来控制线程的中断。当你运行示例时，程序会检测指定目录及其子目录是否包含目标文件。例如，如果输入`\b\c\d`，程序将会递归调用三次`directoryProcess()`方法。当线程检测到其被中断，则会抛出`InterruptedException`异常，无论执行多少次递归调用，程序都会开始执行`run()`方法。


### 永无止境

`InterruptedException`异常一般由Java并发API，例如`sleep()`方法，抛出。


### 小有所成

#### FileSearch类的完整代码
```Java
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
```

#### Main类的完整代码
```Java
package com.diguage.books.concurrencycookbook.chapter1.recipe4;

import java.util.concurrent.TimeUnit;

/**
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-18
 * Time: 19:28
 */
public class Main {
    public static void main(String[] args) {
        FileSearch fileSearch = new FileSearch("C:\\", "autoexec.bat");
        Thread thread = new Thread(fileSearch);
        thread.start();

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread.interrupt();
    }
}
```
