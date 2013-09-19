# Java7并发示例集105：线程睡眠与恢复

有时，我们需要在指定的时间点中断正在执行的线程。比如，每分钟检查一次传感器状态的线程，其余时间，线程不需要做任何事情。在此期间，线程不需要使用计算机的任何资源。过了这段时间之后，并且当Java虚拟机调度了该线程，则该线程继续执行。为此，你可以使用`Thread`类的`sleeep()`方法。该方法以睡眠的方式来推迟线程的执行，而且整数类型的参数则指明睡眠的毫秒数。当调用`sleep()`方法，睡眠时间结束后，Java虚拟机分配给线程CPU运行时间，线程就会继续执行。

另一种是用`sleep()`方法的方式是通过枚举类型`TimeUnit`的元素。该方式使用`Thread`的`sleep()`方法来使得当前线程进行睡眠，它可以接受指定单位的时间作为参数，并将这些其转换成对应的毫秒数。

在本节，我们将开发一个程序，使用`sleep()`方法来实现每秒钟打印一次当前时间。


### 知其然

按照下面所示步骤，来实现本节示例。

1. 创建一个名为`FileClock`的类，并且实现`Runnable`接口。代码如下：
```Java
public class FileClock implements Runnable {
```

2. 实现`run()`方法。代码如下：
```Java
    @Override
    public void run() {
```

3. 写一个遍历十次的循环，在每次迭代中，创建一个`Date`对象，并将其打印到控制台。然后，通过`TimeUtil`的`SECONDS`属性调用`sleep()`方法，来延迟一秒钟执行线程。以为`sleep()`方法会抛出`InterruptedException`异常。所以，我们需要多写几行代码，用来捕获异常。当线程可能中断是，释放或者关闭在线程中使用的资源，总是最佳实践。代码如下：
```Java
        for (int i = 0; i < 10; i++) {
            System.out.printf("%s\n", new Date());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.printf("The FileClock has been interrupted.\n");
            }
        }
```

4. 我们已经有了实现好的线程类。现在，我们来实现主类。创建一个名为`FileMain`的类，并且实现`main()`方法。代码如下：
```Java
public class FileMain {
    public static void main(String[] args) {
```

5. 创建一个`FileClock`对象，再创建一个线程用于执行任务。然后，启动线程。代码如下：
```Java
        FileClock clock = new FileClock();
        Thread thread = new Thread(clock);
        thread.start();
```

6. 在主线程中，通过`TimeUtil`的`SECONDS`属性调用`sleep()`方法，来等待五秒钟。代码如下：
```Java
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
```

7. 中断`FileClock`线程。代码如下：
```Java
        thread.interrupt();
```

8. 执行示例，查看执行效果。


### 知其所以然

当执行这个程序时，会发现，程序如何每秒钟打印一次日期对象，以及线程被中断的情况。

当调用`sleep()`方法时，线程将离开CPU，并停止执行一段时间。在这段时间内，线程不需要CPU了，所以CPU可以执行其他任务。

当睡眠中的线程被中断时，会立即抛出一个`InterruptedException`，而不是等到睡眠结束。


### 永无止境

Java并发API中，还有另外一个方法可以让线程让出CPU。这就是`yield()`方法，调用该方法就是想Java虚拟机发送消息说明线程可以让出CPU给其他线程。Java虚拟机并不保证响应这个请求。一般情况下，该方法仅仅在调试程序时使用。


### 小有所成

本节所用的示例代码的完整版。

#### FileClock类的完整代码
```Java
package com.diguage.books.concurrencycookbook.chapter1.recipe5;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 每秒向控制台打印出当前日期和时间。
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-18
 * Time: 23:11
 */
public class FileClock implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.printf("%s\n", new Date());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.printf("The FileClock has been interrupted.\n");
            }
        }
    }
}
```

#### FileMain类的完整代码
```Java
package com.diguage.books.concurrencycookbook.chapter1.recipe5;

import java.util.concurrent.TimeUnit;

/**
 * 演示线程睡眠和恢复
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-19
 * Time: 00:29
 */
public class FileMain {
    public static void main(String[] args) {
        FileClock clock = new FileClock();
        Thread thread = new Thread(clock);
        thread.start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread.interrupt();
    }
}
```
