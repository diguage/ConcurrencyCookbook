# Java7并发示例集106：等待线程执行终止

在某些场景下，我们必须等待线程执行完成才能进行下一步工作。例如，某些程序在开始执行之前，需要先初始化一些资源。这时，我们可以启动一个线程专门来做初始化任务，等到线程任务完成后，再去执行其他部分。

为此，`Thread`类为我们提供了`join()`方法。当我们使用线程对象调用此方法时，正在掉调用的线程对象将被推迟到被调用对象执行完成后再开始执行。

在本节，示例程序演示等待初始化方法完成后，再去执行其他任务。


### 知其然

按照下面所示步骤，完成示例程序。

1. 创建一个名为`DataSourcesLoader`的类，并且实现`Runnable`接口。代码如下：
```Java
public class DataSourcesLoader implements Runnable {
```

2. 实现`run()`方法，向控制台打印出一条信息以说明开始执行，然后睡眠4秒钟，再向控制台打印一条信息来说明线程执行结束。代码如下：
```Java
    @Override
    public void run() {
        System.out.printf("Beginning data sources loading: %s\n",
                new Date());
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Data sources loading has finished: %s\n",
                new Date());
    }
```

3. 创建一个名为`NetworkConnectionsLoader`的类，并且实现`Runnable`接口。 实现`run()`方法，该方法代码与`DataSourcesLoader`类的`run()`方法一样，只是这个睡眠6秒钟。

4. 实现示例的主类，并且实现`main()`方法。代码如下：
```Java
public class Main {
    public static void main(String[] args) {
```

5. 创建一个`DataSourcesLoader`对象，以及一个启动它执行的`Thread`对象。代码如下：
```Java
        DataSourcesLoader dsLoader = new DataSourcesLoader();
        Thread thread1 = new Thread(dsLoader, "DataSourcesLoader");
```

6. 创建一个`NetworkConnectionsLoader`对象，以及一个启动它执行的`Thread`对象。代码如下：
```Java
        NetworkConnectionsLoader ncLoader = new NetworkConnectionsLoader();
        Thread thread2 = new Thread(ncLoader, "NetworkConnectionsLoader");
```

7. 调用两个`Thread`对象的`start()`方法。代码如下：
```Java
        thread1.start();
        thread2.start();
```

8. 调用`join()`方法，来等待两个线程完成其任务。这个方法会抛出`InterruptedException`异常，所以要捕获该异常。代码如下：
```Java
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
```

9. 向控制台打印一句话，表明程序执行结束。代码如下：
```Java
        System.out.printf("Main: Configuration has been loaded: %s\n",
                new Date());
```

10. 运行程序，查看执行效果。


### 知其所以然

当运行这个示例程序时，我们可以看到两个线程启动了它们的执行。首先，是`DataSourcesLoader`完成了它的执行；然后，是`NetworkConnectionsLoader`完成了它的执行。这时，主线程继续它的执行，然后向控制台打印出终止信息。


### 永无止境

Java提供了另外两种重载的`join()`方法：

* join(long milliseconds)
* join(long milliseconds, long nanos)

第一种方式，不会直到被调用完成任务，而是等待参数指定的时间后就开始执行；例如，如果`thread1`调用该方法，`thread1.join(1000)`，当`thread1`线程满足如下其中之一的条件就会继续执行：

* `thread2`完成它的执行；
* 1000毫秒过后；

当这两个条件中的其中之一为真时，`join()`方法就会返回，开始继续执行原来的任务。

第二种方式的方法和第一种很类似，只是多了一个纳秒级的时间参数。


### 小有所成

本节所用示例代码的完整版。

#### DataSourcesLoader类的完整代码
```Java
package com.diguage.books.concurrencycookbook.chapter1.recipe6;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-19
 * Time: 09:15
 */
public class DataSourcesLoader implements Runnable {
    @Override
    public void run() {
        System.out.printf("Beginning data sources loading: %s\n",
                new Date());
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Data sources loading has finished: %s\n",
                new Date());
    }
}
```

#### NetworkConnectionsLoader类的完整代码
```Java
package com.diguage.books.concurrencycookbook.chapter1.recipe6;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-19
 * Time: 09:21
 */
public class NetworkConnectionsLoader implements Runnable {
    @Override
    public void run() {
        System.out.printf("Beginning data sources loading: %s\n",
                new Date());
        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Data sources loading has finished: %s\n",
                new Date());
    }
}
```

#### Main类的完整代码
```Java
package com.diguage.books.concurrencycookbook.chapter1.recipe6;

import java.util.Date;

/**
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-19
 * Time: 09:25
 */
public class Main {
    public static void main(String[] args) {
        DataSourcesLoader dsLoader = new DataSourcesLoader();
        Thread thread1 = new Thread(dsLoader, "DataSourcesLoader");

        NetworkConnectionsLoader ncLoader = new NetworkConnectionsLoader();
        Thread thread2 = new Thread(ncLoader, "NetworkConnectionsLoader");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Main: Configuration has been loaded: %s\n",
                new Date());
    }
}
```
