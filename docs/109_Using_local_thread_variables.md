# Java7并发示例集109：本地线程变量的使用

共享数据是并发程序最关键的特性之一。对于无论是继承`Thread`类的对象，还是实现`Runnable`接口的对象，这都是一个非常周重要的方面。

如果创建了一个实现`Runnable`接口的类的对象，并使用该对象启动了一系列的线程，则所有这些线程共享相同的属性。换句话说，如果一个线程修改了一个属性，则其余所有线程都会受此改变的影响。

有时，我们更希望能在线程内单独使用，而不和其他使用同一对象启动的线程共享。Java并发接口提供了一种很清晰的机制来满足此需求，该机制称为本地线程变量。该机制的性能也非常可观。



### 知其然

按照下面所示步骤，完成示例程序。

1. 首先，实现一个有上述问题的程序。创建一个名为`UnsafeTask`的类，并且实现`Runnable`接口。在类中声明一个`java.util.Date`类型的私有属性。代码如下：
```Java
public class UnsafeTask implements Runnable {
    private Date startDate;
```

2. 实现`UnsafeTask`的`run()`方法，该方法实例化`startDate`属性，并将其值输出到控制台上。休眠随机一段时间，然后再次将`startDate`属性的值输出到控制台上。代码如下：
```Java
    @Override
    public void run() {
        startDate = new Date();
        System.out.printf("Starting Thread: %s : %s\n",
                Thread.currentThread().getId(), startDate);

        try {
            TimeUnit.SECONDS.sleep((int) Math.rint(Math.random() * 10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Thread Finished: %s : %s\n",
                Thread.currentThread().getId(), startDate);
    }
```

3. 实现问题程序的主类。创建一个带有`main()`方法的类，`UnsafeMain`。在`main()`方法中，创建一个`UnsafeTask`对象，并使用该对象来创建10个`Thread`对象，来启动10个线程。在每个线程中间，休眠2秒钟。代码如下：
```Java
public class UnsafeMain {
    public static void main(String[] args) {
        UnsafeTask task = new UnsafeTask();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(task);
            thread.start();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
```

4. 从上面的逻辑来看，每个线程都有一个不同的启动时间。但是，根据下面的输出日志来看，出现了好多相同的时间值。如下：
```Java
Starting Thread: 9 : Sun Sep 29 23:31:08 CST 2013
Starting Thread: 10 : Sun Sep 29 23:31:10 CST 2013
Starting Thread: 11 : Sun Sep 29 23:31:12 CST 2013
Starting Thread: 12 : Sun Sep 29 23:31:14 CST 2013
Thread Finished: 9 : Sun Sep 29 23:31:14 CST 2013
Starting Thread: 13 : Sun Sep 29 23:31:16 CST 2013
Thread Finished: 10 : Sun Sep 29 23:31:16 CST 2013
Starting Thread: 14 : Sun Sep 29 23:31:18 CST 2013
Thread Finished: 11 : Sun Sep 29 23:31:18 CST 2013
Starting Thread: 15 : Sun Sep 29 23:31:20 CST 2013
Thread Finished: 12 : Sun Sep 29 23:31:20 CST 2013
Starting Thread: 16 : Sun Sep 29 23:31:22 CST 2013
Starting Thread: 17 : Sun Sep 29 23:31:24 CST 2013
Thread Finished: 17 : Sun Sep 29 23:31:24 CST 2013
Thread Finished: 15 : Sun Sep 29 23:31:24 CST 2013
Thread Finished: 13 : Sun Sep 29 23:31:24 CST 2013
Starting Thread: 18 : Sun Sep 29 23:31:26 CST 2013
Thread Finished: 14 : Sun Sep 29 23:31:26 CST 2013
Thread Finished: 18 : Sun Sep 29 23:31:26 CST 2013
Thread Finished: 16 : Sun Sep 29 23:31:26 CST 2013
```

5. 如前文所示，我们准备使用本地线程变量（the thread-local variables）机制来解决这个问题。

6. 创建一个名为`SafeTask`的类，并且实现`Runnable`接口。代码如下：
```Java
public class SafeTask implements Runnable {
```

7. 声明一个`ThreadLocal<Date>`类型的对象，该对象实例化时，重写了`initialValue()`方法，在该方法中返回实际的日期值。代码如下：
```Java
    private static ThreadLocal<Date> startDate = new
            ThreadLocal<Date>() {
                @Override
                protected Date initialValue() {
                    return new Date();
                }
            };
```

8. 实现`SafeTask`类的`run()`方法。该方法和`UnsafeTask`的`run()`方法一样，只是`startDate`属性的方法方式稍微调整一下。代码如下：
```Java
    @Override
    public void run() {
        System.out.printf("Starting Thread: %s : %s\n",
                Thread.currentThread().getId(), startDate.get());

        try {
            TimeUnit.SECONDS.sleep((int) Math.rint(Math.random() * 10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Thread Finished: %s : %s\n",
                Thread.currentThread().getId(), startDate.get());
    }
```

9. 该安全示例的主类和非安全程序的主类基本相同，只是需要将`UnsafeTask`修改为`SafeTask`即可。具体代码如下：
```Java
public class SafeMain {
    public static void main(String[] args) {
        SafeTask task = new SafeTask();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(task);
            thread.start();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
```

10. 运行程序，分析两次输入的不同之处。

> 为了规范类的命名，本文中主类的命名和原文稍有不同。另外，原文程序和文字叙述不一致。应该是一个笔误。


### 知其所以然

下面是安全示例的执行结果。从结果中，可以很容易地看出，每个线程都有一个属于各自线程的`startDate`属性值。程序输入如下：

```java
Starting Thread: 9 : Sun Sep 29 23:52:17 CST 2013
Starting Thread: 10 : Sun Sep 29 23:52:19 CST 2013
Starting Thread: 11 : Sun Sep 29 23:52:21 CST 2013
Thread Finished: 10 : Sun Sep 29 23:52:19 CST 2013
Starting Thread: 12 : Sun Sep 29 23:52:23 CST 2013
Thread Finished: 11 : Sun Sep 29 23:52:21 CST 2013
Starting Thread: 13 : Sun Sep 29 23:52:25 CST 2013
Thread Finished: 9 : Sun Sep 29 23:52:17 CST 2013
Starting Thread: 14 : Sun Sep 29 23:52:27 CST 2013
Starting Thread: 15 : Sun Sep 29 23:52:29 CST 2013
Thread Finished: 13 : Sun Sep 29 23:52:25 CST 2013
Starting Thread: 16 : Sun Sep 29 23:52:31 CST 2013
Thread Finished: 14 : Sun Sep 29 23:52:27 CST 2013
Starting Thread: 17 : Sun Sep 29 23:52:33 CST 2013
Thread Finished: 12 : Sun Sep 29 23:52:23 CST 2013
Thread Finished: 16 : Sun Sep 29 23:52:31 CST 2013
Thread Finished: 15 : Sun Sep 29 23:52:29 CST 2013
Starting Thread: 18 : Sun Sep 29 23:52:35 CST 2013
Thread Finished: 17 : Sun Sep 29 23:52:33 CST 2013
Thread Finished: 18 : Sun Sep 29 23:52:35 CST 2013
```

线程本地变量为每个线程存储了一个属性的副本。可以使用`ThreadLocal`的`get()`方法获取变量的值，使用`set()`方法设置变量的值。如果第一次访问线程本地变量，并且该变量还没有赋值，则调用`initialValue()`方法为每个线程初始化一个值。


### 永无止境

`ThreadLocal`类还提供了`remove()`方法，来删掉调用该方法的线程中存储的本地变量值。

另外，Java并发API还提供了`InheritableThreadLocal`类，让子线程可以接收所有可继承的线程局部变量的初始值，以获得父线程所具有的值。如果线程A有一个线程本地变量，当线程A创建线程B时，则线程B将拥有和线程A一样的线程本地变量。还可以重写`childValue()`，来初始化子线程的线程本地变量。该方法将接受从父线程以参数形式传递过来的线程本地变量的值。


### 拿来主义

本文是从 **《Java 7 Concurrency Cookbook》** （D瓜哥窃译为 **《Java7并发示例集》** ）翻译而来，仅作为学习资料使用。没有授权，不得用于任何商业行为。


### 小有所成

下面是本节示例所包含的所有代码的完整版。

#### UnsafeTask类的完整代码
```Java
package com.diguage.books.concurrencycookbook.chapter1.recipe9;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 不能保证线程安全的例子
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-23
 * Time: 23:58
 */
public class UnsafeTask implements Runnable {
    private Date startDate;

    @Override
    public void run() {
        startDate = new Date();
        System.out.printf("Starting Thread: %s : %s\n",
                Thread.currentThread().getId(), startDate);

        try {
            TimeUnit.SECONDS.sleep((int) Math.rint(Math.random() * 10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Thread Finished: %s : %s\n",
                Thread.currentThread().getId(), startDate);
    }
}
```

#### UnsafeMain类的完整代码
```Java
package com.diguage.books.concurrencycookbook.chapter1.recipe9;

import java.util.concurrent.TimeUnit;

/**
 * 不安全的线程示例
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-24
 * Time: 00:04
 */
public class UnsafeMain {
    public static void main(String[] args) {
        UnsafeTask task = new UnsafeTask();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(task);
            thread.start();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
```

#### SafeTask类的完整代码
```Java
package com.diguage.books.concurrencycookbook.chapter1.recipe9;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 使用线程本地变量保证线程安全
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-29
 * Time: 23:34
 */
public class SafeTask implements Runnable {
    private static ThreadLocal<Date> startDate = new
            ThreadLocal<Date>() {
                @Override
                protected Date initialValue() {
                    return new Date();
                }
            };

    @Override
    public void run() {
        System.out.printf("Starting Thread: %s : %s\n",
                Thread.currentThread().getId(), startDate.get());

        try {
            TimeUnit.SECONDS.sleep((int) Math.rint(Math.random() * 10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Thread Finished: %s : %s\n",
                Thread.currentThread().getId(), startDate.get());
    }
}
```

#### SafeMain类的完整代码
```Java
package com.diguage.books.concurrencycookbook.chapter1.recipe9;

import java.util.concurrent.TimeUnit;

/**
 * 安全的线程示例
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-24
 * Time: 00:04
 */
public class SafeMain {
    public static void main(String[] args) {
        SafeTask task = new SafeTask();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(task);
            thread.start();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
```

