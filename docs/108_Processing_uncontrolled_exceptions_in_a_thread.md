# Java7并发示例集108：处理线程的受检异常

Java语言中，把异常分为两类：

* **受检异常：** 这类异常必须在`throws`子句中被显式抛出或者在方法内被捕获。例如，`IOException`异常或`ClassNotFoundException`异常。
* **非受检异常：** 这类异常不需要显式抛出或捕获。例如，`NumberFormatException`异常。

当一个受检异常在`Thread`对象的`run()`方法中被抛出时，我们必须捕获并处理它，因为`run()`方法不能抛出异常。而一个非受检异常在`Thread`对象的`run()`方法中被抛出时，默认的行为是在控制台打印出堆栈跟踪信息然后退出程序。

幸运的是，Java为我们提供了一种机制，专门用于处理由`Thread`对象抛出的非受检异常，以避免程序的退出。

在本节，我们用示例来演示这种机制。


### 知其然

按照下面所示步骤来实现我们的示例。

1. 首先，我们需要实现一个用于处理非受检异常的类。这个类必须实现`UncaughtExceptionHandler`类，实现在该接口中声明的`uncaughtException()`方法。在本例中，该类名为`ExceptionHandler`，`uncaughtException()`方法将异常以及抛出异常的线程信息打印出来。代码如下：
```Java
/**
 * 非受检异常处理类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-22
 * Time: 23:11
 */
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.printf("An exception has been captured\\n");
        System.out.printf("Thread: %s\n", t.getId());
        System.out.printf("Exception: %s: %s\n", e.getClass().getName(),
                e.getMessage());
        System.out.printf("Stack Trace: \n");
        e.printStackTrace(System.out);
        System.out.printf("Thread status: %s\n", t.getState());
    }
}
```

2. 实现一个可以抛出非受检异常的类，称为`Task`，实现`Runnable`接口，实现`run()`方法，特意编码一段可以产生非受检异常的代码，例如，将字符串转换成数字。代码如下：
```Java
/**
 * 异常生成类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-22
 * Time: 23:18
 */
public class Task implements Runnable {
    @Override
    public void run() {
        int numero = Integer.parseInt("diguage.com");
    }
}
```

3. 创建程序的主类，`Main`类，然后实现`main()`方法。代码如下：
```Java
public class Main {
    public static void main(String[] args) {
```

4. 创建`Task`对象，并且创建一个`Thread`对象来执行之。使用`setUncaughtExceptionHandler() `方法设置非受检异常的处理类。然后，启动线程。代码如下：
```Java
        Task task = new Task();
        Thread thread = new Thread(task);
        thread.setUncaughtExceptionHandler(new ExceptionHandler());
        thread.start();
```

5. 运行示例，查看结果。


### 知其所以然

从下面的输出片段可以看出异常执行的结果。异常被抛出，然后被处理类捕获并将异常信息打印到了控制台。

```Java
An exception has been captured
Thread: 9
Exception: java.lang.NumberFormatException: For input string: "diguage.com"
Stack Trace:
java.lang.NumberFormatException: For input string: "diguage.com"
	at java.lang.NumberFormatException.forInputString(NumberFormatException.java:65)
	at java.lang.Integer.parseInt(Integer.java:492)
	at java.lang.Integer.parseInt(Integer.java:527)
	at com.diguage.books.concurrencycookbook.chapter1.recipe8.Task.run(Task.java:13)
	at java.lang.Thread.run(Thread.java:722)
Thread status: RUNNABLE

Process finished with exit code 0
```

当一个线程抛出一个异常，并且该异常（这里特指非受检异常）没有捕获时，Java虚拟机会检查是否通过相应方法设置非受检异常处理类，如果以已经设置过，则调用`uncaughtException()`方法，并将线程和异常作为参数传递给方法。

如果没有设置处理类，Java虚拟机就会在控制台将堆栈跟踪信息打印出来，然后退出程序。


### 永无止境

`Thread`类还有一个和非受检异常处理相关的方法。这就是静态方法`setDefaultUncaughtExceptionHandler()`，该方法可以设置程序中所有线程的非受检异常的处理类。

当线程中抛出一个未捕获的异常时，Java虚拟机会从三个地方寻找异常处理类：

首先，从线程对象中查找异常处理类，这就是我们本节所学内容。如不存在，则从线程所在的线程组（ThreadGroup）中查找异常处理类。关于这部分内容，以后会专门讲解。如果还是不存在，则查找上面刚刚提到的程序默认异常处理类。

如果上面提到的异常处理都不存在，则Java虚拟机将异常的堆栈跟踪信息打印到控制台，然后退出程序。


### 拿来主义

本文是从 **《Java 7 Concurrency Cookbook》** （D瓜哥窃译为 **《Java7并发示例集》** ）翻译而来，仅作为学习资料使用。没有授权，不得用于任何商业行为。


### 小有所成

#### ExceptionHandler类的完整代码
```Java
package com.diguage.books.concurrencycookbook.chapter1.recipe8;

/**
 * 非受检异常处理类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-22
 * Time: 23:11
 */
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.printf("An exception has been captured\n");
        System.out.printf("Thread: %s\n", t.getId());
        System.out.printf("Exception: %s: %s\n", e.getClass().getName(),
                e.getMessage());
        System.out.printf("Stack Trace: \n");
        e.printStackTrace(System.out);
        System.out.printf("Thread status: %s\n", t.getState());
    }
}
```

#### Task类的完整代码
```Java
package com.diguage.books.concurrencycookbook.chapter1.recipe8;

/**
 * 异常生成类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-22
 * Time: 23:18
 */
public class Task implements Runnable {
    @Override
    public void run() {
        int numero = Integer.parseInt("diguage.com");
    }
}
```

#### Main类的完整代码
```Java
package com.diguage.books.concurrencycookbook.chapter1.recipe8;

/**
 * 示例的主类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-22
 * Time: 23:20
 */
public class Main {
    public static void main(String[] args) {
        Task task = new Task();
        Thread thread = new Thread(task);
        thread.setUncaughtExceptionHandler(new ExceptionHandler());
        thread.start();
    }
}
```
