# 使用线程工厂创建线程

在面向对象的世界中，工厂模式（The factory pattern）是应用最多的设计模式之一。该模式是创建型模式，其目的是开发一个可以创建其他一个或者多个类的对象的工具。这样，当我们需要创建类的对象时，就可以使用工厂类来代替`new`操作符。

使用工厂模式，将类的创建集中到一起可以带来如下好处：

* 可以轻松改变对象的类型或者对象的创建方式；
* 易于限制对有限资源对象的创建。比如，可以创建一个类型的指定数目个对象；
* 易于生成对象创建的统计信息。

Java提交了线程对象创建的接口，`ThreadFactory`接口。一些Java同步API的高级工具就使用线程工厂类来创建对象。

在本节，我们学习如何通过实现`ThreadFactory`接口来创建`Thread`对象，同时还定制对象的名称，保存对象创建的统计信息。


### 知其然

按照下面所示步骤，实现该示例程序。

1. 创建名为`MyThreadFactory`的类，实现`ThreadFactory`接口。代码如下：
```Java
public class MyThreadFactory implements ThreadFactory {
```

2. 声明三个私有变量：整形变量`counter`，该变量用于存储`Thread`对象的创建个数；字符串类型变量`name`，该变量是每个线程对象名称的基础；`List<String>`变量`stats`，该变量存储对象创建的统计信息。另外，实现类的构造函数，用于实例化这些变量。代码如下：
```Java
    private int counter;
    private String name;
    private List<String> stats;

    public MyThreadFactory(String name) {
        this.counter = 0;
        this.name = name;
        this.stats = new ArrayList<>();
    }
```

3. 实现`newThread`方法，该方法接受一个`Runnable`类型的参数，返回一个包装方法参数的`Thread`对象。这里，我们生成`Thread`对象的名称，创建`Thread`对象，保存统计信息。代码如下：
```Java
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, name + "-Thread_" + counter);
        counter++;
        stats.add(String.format("Created thread %d with name %s on %s\n",
                t.getId(), t.getName(), new Date()));
        return t;
    }
```

4. 实现方法`getStats()`，返回字符串表示所有`Thread`对象的创建信息。代码如下：
```Java
    // 获取线程工厂类的工作状态
    public String getStats() {
        StringBuffer buffer = new StringBuffer();
        Iterator<String> it = stats.iterator();

        while (it.hasNext()) {
            buffer.append(it.next());
            buffer.append("\n");
        }

        return buffer.toString();
    }
```

5. 创建`Task`类，并实现`Runnable`接口。在这里例子中，这个任务不做任何工作，仅仅休眠1秒钟。代码如下：
```Java
public class Task implements Runnable {
    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

6. 创建程序的主类，`Main`类，然后实现`main()`方法。代码如下：
```Java
public class Main {
  public static void main(String[] args) {
```

7. 创建`MyThreadFactory`对象和`Task`对象。代码如下：
```Java
        MyThreadFactory factory = new MyThreadFactory("MyThreadFactory");
        Task task = new Task();
```

8. 使用`MyThreadFactory`对象创建10个`Thread`对象，并且启动它们。代码如下：
```Java
        Thread thread;
        System.out.println("Starting the Threads");
        for (int i = 0; i < 10; i++) {
            thread = factory.newThread(task);
            thread.start();
        }
```

9. 将线程工厂的统计信息打印到控制台上。代码如下：
```Java
        System.out.println("Factory stats:");
        System.out.printf("%s\n", factory.getStats());
```

10. 运行示例，查看结果。

### 知其所以然

`ThreadFactory`接口只有一个方法`newThread()`。该方法接受一个`Runnable`类型的参数，返回一个包装方法参数的`Thread`对象。当实现一个`ThreadFactory`接口时，必须实现重新`newThread()`方法。很多基本的线程工厂类，只有一行：

```Java
return new Thread(r);
```

可以通过重写这个实现来增加更多特性。例如：

* 创建个性化的线程，例如本节例子，定制特殊格式的线程名称，还可以通过继承`Thread`类，来创建我们自己的线程类；
* 保存线程创建的统计信息，比如本例中；
* 限制线程的创建数量；
* 校验线程的创建；
* 发挥想象的各种特性。

使用工厂设计模式是一个良好的编程实践。如果想通过实现`ThreadFactory`接口来集中线程的创建，则必须检查代码确保所有线程的创建都使用了该工厂类。


### 拿来主义

本文是从 **《Java 7 Concurrency Cookbook》** （D瓜哥窃译为 **《Java7并发示例集》** ）翻译而来，仅作为学习资料使用。没有授权，不得用于任何商业行为。


### 小有所成

下面是本节示例所用的代码的完整版。

#### MyThreadFactory类的完整代码
```Java
package com.diguage.books.concurrencycookbook.chapter1.recipe12;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadFactory;

/**
 * 自定义的线程工厂类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-03
 * Time: 19:18
 */
public class MyThreadFactory implements ThreadFactory {
    private int counter;
    private String name;
    private List<String> stats;

    public MyThreadFactory(String name) {
        this.counter = 0;
        this.name = name;
        this.stats = new ArrayList<>();
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, name + "-Thread_" + counter);
        counter++;
        stats.add(String.format("Created thread %d with name %s on %s\n",
                t.getId(), t.getName(), new Date()));
        return t;
    }

    // 获取线程工厂类的工作状态
    public String getStats() {
        StringBuffer buffer = new StringBuffer();
        Iterator<String> it = stats.iterator();

        while (it.hasNext()) {
            buffer.append(it.next());
            buffer.append("\n");
        }

        return buffer.toString();
    }
}
```

#### Task类的完整代码
```Java
package com.diguage.books.concurrencycookbook.chapter1.recipe12;

import java.util.concurrent.TimeUnit;

/**
 * 线程类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-03
 * Time: 19:19
 */
public class Task implements Runnable {
    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

#### Main类的完整代码
```Java
package com.diguage.books.concurrencycookbook.chapter1.recipe12;

/**
 * 线程工厂示例主类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-03
 * Time: 19:34
 */
public class Main {
    public static void main(String[] args) {
        MyThreadFactory factory = new MyThreadFactory("MyThreadFactory");
        Task task = new Task();
        Thread thread;
        for (int i = 0; i < 10; i++) {
            thread = factory.newThread(task);
            thread.start();
        }
        System.out.println("Factory stats:");
        System.out.printf("%s\n", factory.getStats());
    }
}
```