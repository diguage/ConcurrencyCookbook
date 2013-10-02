# 线程组

对线程分组是Java并发API提供的一个有趣功能。我们可以将一组线程看成一个独立单元，并且可以随意操纵线程组中的线程对象。比如，可以控制一组线程来运行同样的任务，无需关心有多少线程还在运行，还可以使用一次中断调用中断所有线程的执行。

Java提供了`ThreadGroup`类来控制一个线程组。一个线程组可以通过线程对象来创建，也可以由其他线程组来创建，生成一个树形结构的线程。

> 根据[《Effective Java》](http://book.douban.com/subject/3360807/)的说明，不再建议使用`ThreadGroup`。建议使用`Executor`。
>
> ——D瓜哥特此说明。

在本节，我们就使用`ThreadGroup`来开发一个简单的示例。我们将创建十个休眠时间不等的线程（比如模拟搜索），当其中一个完成时，中断其余线程。


### 知其然

按照下面所示步骤，完成示例代码。

1. 创建一个名为`Result`的类，用于存储第一个完成任务的线程的名字。声明一个`String`类型的私有变量，`name`，同时生成Setter/Getter方法。代码如下：
```Java
public class Result {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

2. 创建一个名为`SearchTask`的类，并实现`Runnable`接口。代码如下：
```Java
public class SearchTask implements Runnable {
```

3. 声明一个`Result`类型的私有变量，并通过构造函数来实例化该变量。代码如下：
```Java
    private Result result;

    public SearchTask(Result result) {
        this.result = result;
    }
```

4. 实现`run()`方法，在其中调用`doTask()`方法，来等待完成或被中断。该方法还向控制台打印信息来显示线程的开始、结束或者中断。代码如下：
```Java
    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.printf("Thread %s: Start\n", name);
        try {
            doTask();
            result.setName(name);
        } catch (InterruptedException e) {
            System.out.printf("Thread %s: Interrupted\n", name);
            return;
        }
        System.out.printf("Thread %s: End\n", name);
    }
```

5. 实现`doTask()`方法，该方法将创建一个`Random`对象，然后使用该对象生成一个随机数，来调节线程休眠的时间。代码如下：
```Java
    // 模拟搜索
    private void doTask() throws InterruptedException {
        Random random = new Random(new Date().getTime());
        int value = (int) (random.nextDouble() * 100);
        System.out.printf("Thread %s: %d\n",
                Thread.currentThread().getName(), value);
        TimeUnit.SECONDS.sleep(value);
    }
```

6. 创建示例程序的主类，`Main`，并实现`main()`方法。代码如下：
```Java
public class Main {
    public static void main(String[] args) {
```

7. 创建一个名称为`Searcher`的`ThreadGroup`对象。代码如下：
```Java
        ThreadGroup threadGroup = new ThreadGroup("Searcher");
```

8. 然后，创建一个`Result`对象和`SearchTask`对象。代码如下：
```Java
        Result result = new Result();
        SearchTask searchTask = new SearchTask(result);
```

9. `SearchTask`对象使用创建十个`Thread`对象，并且创建`Thread`对象时，将`ThreadGroup`对象作为第一个参数，传递给`Thread`类的构造函数。代码如下：
```Java
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(threadGroup, searchTask);
            thread.start();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
```

10. 使用`list()`方法将`ThreadGroup`对象的信息打印出来。代码如下：
```Java
        System.out.printf("Number of Threads: %d\n", threadGroup.activeCount());
        System.out.println("Information about the Thread Group");
        threadGroup.list();
```

11. 使用`activeCount()`和`enumerate()`来获取`ThreadGroup`对象中的活跃线程数并将其复制到一个线程数组中。使用`get*()`方法，获取线程的名称和状态。代码如下：
```Java
        Thread[] threads = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(threads);
        for (int i = 0; i < threadGroup.activeCount(); i++) {
            System.out.printf("Thread %s: %s\n", threads[i].getName(),
                    threads[i].getState());
        }
```

12. 调用`waitFinish()`方法，等待`ThreadGroup`对象中的其中一个线程完成任务。稍后实现该方法。代码如下：
```Java
        waitFinish(threadGroup);
```

13. 使用`interrupt()`方法，中断线程组中其他线程。代码如下：
```Java
        threadGroup.interrupt();
```

14. 实现`waitFinish()`方法，使用`activeCount()`方法控制线程的执行结果。代码如下：
```Java
    // 等待任务完成
    private static void waitFinish(ThreadGroup threadGroup) {
        while (threadGroup.activeCount() > 9) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
```

15. 运行程序，查看执行效果。


### 知其所以然

下面是程序执行的结果。你将看到`list()`方法的输出，各个线程的状态等。

```Java
Thread Thread-0: Start
Thread Thread-0: 52
Thread Thread-1: Start
Thread Thread-1: 41
Thread Thread-2: Start
Thread Thread-2: 69
Thread Thread-3: Start
Thread Thread-3: 60
Thread Thread-4: Start
Thread Thread-4: 88
Number of Threads: 5
Information about the Thread Group
java.lang.ThreadGroup[name=Searcher,maxpri=10]
    Thread[Thread-0,5,Searcher]
    Thread[Thread-1,5,Searcher]
    Thread[Thread-2,5,Searcher]
    Thread[Thread-3,5,Searcher]
    Thread[Thread-4,5,Searcher]
Thread Thread-0: TIMED_WAITING
Thread Thread-1: TIMED_WAITING
Thread Thread-2: TIMED_WAITING
Thread Thread-3: TIMED_WAITING
Thread Thread-4: TIMED_WAITING
Thread Thread-1: Interrupted
Thread Thread-4: Interrupted
Thread Thread-2: Interrupted
Thread Thread-0: Interrupted
Thread Thread-3: Interrupted
```

`ThreadGroup`类保存着众多`Thread`对象以及关联的`ThreadGroup`对象。可以通过调用该类的方法，访问线程的信息，还可以对其进行各种操作，比如中断等。


### 永无止境

`ThreadGroup`类还有好多方法。请翻阅API文档，查看完整的方法说明。


### 拿来主义

本文是从 **《Java 7 Concurrency Cookbook》** （D瓜哥窃译为 **《Java7并发示例集》** ）翻译而来，仅作为学习资料使用。没有授权，不得用于任何商业行为。


### 小有所成

下面是本节示例所用的代码的完整版。

#### Result类的完整代码
```Java
package com.diguage.books.concurrencycookbook.chapter1.recipe10;

/**
 * 存储查询结果
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-30
 * Time: 00:45
 */
public class Result {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

#### SearchTask类的完整代码
```Java
package com.diguage.books.concurrencycookbook.chapter1.recipe10;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 模拟搜索类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-02
 * Time: 22:38
 */
public class SearchTask implements Runnable {
    private Result result;

    public SearchTask(Result result) {
        this.result = result;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.printf("Thread %s: Start\n", name);
        try {
            doTask();
            result.setName(name);
        } catch (InterruptedException e) {
            System.out.printf("Thread %s: Interrupted\n", name);
            return;
        }
        System.out.printf("Thread %s: End\n", name);
    }

    // 模拟搜索
    private void doTask() throws InterruptedException {
        Random random = new Random(new Date().getTime());
        int value = (int) (random.nextDouble() * 100);
        System.out.printf("Thread %s: %d\n",
                Thread.currentThread().getName(), value);
        TimeUnit.SECONDS.sleep(value);
    }
}
```

#### Main类的完整代码
```Java
package com.diguage.books.concurrencycookbook.chapter1.recipe10;

import java.util.concurrent.TimeUnit;

/**
 * 线程组示例主类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-02
 * Time: 22:45
 */
public class Main {
    public static void main(String[] args) {
        ThreadGroup threadGroup = new ThreadGroup("Searcher");

        Result result = new Result();
        SearchTask searchTask = new SearchTask(result);

        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(threadGroup, searchTask);
            thread.start();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.printf("Number of Threads: %d\n", threadGroup.activeCount());
        System.out.println("Information about the Thread Group");
        threadGroup.list();

        Thread[] threads = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(threads);
        for (int i = 0; i < threadGroup.activeCount(); i++) {
            System.out.printf("Thread %s: %s\n", threads[i].getName(),
                    threads[i].getState());
        }

        waitFinish(threadGroup);

        threadGroup.interrupt();
    }

    // 等待任务完成
    private static void waitFinish(ThreadGroup threadGroup) {
        while (threadGroup.activeCount() > 9) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
```
