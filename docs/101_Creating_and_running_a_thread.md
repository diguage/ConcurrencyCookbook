# 线程的创建和执行

### 开门见山

在IT圈里，每当我们谈论并发时，必定会说起在一台计算机上同时运行的一系列线程。如果这台电脑上有多个处理器或者是一个多核处理器，那么这时是实实在在的“同时运行”；但是，如果计算机只有一个单核处理器，那么这时的“同时运行”只是表象而已。

所有的现代操作系统全部支持任务的并发执行。你可以边听音乐，边上网看新闻，还不耽误收发电子邮件。我们可以说，这种并发是 **进程级并发** 。在进程内部，我也可以看到有许许多多的并发任务。我们把运行在一个进程里面的并发任务称 **线程**。

和并发相关的另外一个常见概念是 **并行**。并发与并行之间，存在着一些不同，也存在着一些联系。一些程序员（Author，窃译为“程序员”）认为，在一个单核处理器上多线程地执行应用程序就是并发，并且你可以观察到程序员的执行；另外，当你的程序以多线程的形式运行在多个处理器或者是多核处理器上时，就是并行。还有一些程序员认为如果应用程序的线程没有按照预先设定好的顺序执行就是并发；为了简化问题解决方案而是用个线程，并且这些线程是按照一定顺序在执行，那么这是并行。

本章将通过十二个示例来演示如何使用Java7的API来执行一些基本的线程操作。你将可以看到，在Java程序中，如何创建、执行线程，如何控制线程的执行，如何将一组线程作为一个单元来操纵等等。

在本节，我们将学习如何在Java程序中创建线程，以及如何运行。在Java程序中，一切皆为 **Object** ，线程也是如此。创建线程的方式有两种：

1. 继承`Thread`类，并且重写`run()`方法；
2. 创建一个类，实现`Runnable`接口，然后创建一个`Thread`类的对象，然后将实现`Runnable`接口的类的实例作为参数，传递给`Thread`类的实例。

在本节，我们将使用第二种方式，来创建十个线程，并且运行起来。每个线程计算并打印两个十以内的整数之积。

### 知其然

根据下面所述的步骤来实现这里例子：

1. 创建一个名为`Calculator`的类，并且实现`Runnable`接口。代码如下：
```Java
public class Calculator implements Runnable {
```

2. 声明一个私有的整形属性，名称为`number`，实现该类的构造函数来初始化刚刚声明的属性。代码如下：
```Java
    private int number;

    public Calculator(int number) {
        this.number = number;
    }
```

3. 实现`run()`方法，该方法是我们创建的线程执行时运行的程序（instruction），故而该方法用于计算乘法表。具体代码如下：
```Java
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.printf("%s: %d * %d = %d\n",
                    Thread.currentThread().getName(),
                    number, i, i * number);
        }
    }
```

4. 现在，是时候实现示例应用的主类(main class)了。创建名为`Main`的类，在该类中添加`main`方法。代码如下：
```Java
public class Main {
    public static void main(String[] args) {
```

5. 在`main()`方法内部，创建一个遍历十次的for循环，在循环体内，创建一个`Calculator`类的对象`calculator`，创建一个`Thread`类的对象`thread`，将`calculator`作为构造函数的参数，传递给`thread`的初始化语句。最后，调用`thread`对象的`start()`方法。代码如下：
```Java
        for (int i = 0; i < 10; i++) {
            Calculator calculator = new Calculator(i);
            Thread thread = new Thread(calculator);
            thread.start();
        }
```

6. 运行这个程序，看不同线程是如何并发执行的。

### 知其所以然

下面是运行程序时，控制台打印出来的的一段输出，我们可以看到我们创建的所有线程都在并发执行。

```Java
Thread-3: 3 * 5 = 15
Thread-0: 0 * 2 = 0
Thread-3: 3 * 6 = 18
Thread-1: 1 * 6 = 6
Thread-1: 1 * 7 = 7
Thread-3: 3 * 7 = 21
Thread-3: 3 * 8 = 24
Thread-0: 0 * 3 = 0
Thread-0: 0 * 4 = 0
Thread-3: 3 * 9 = 27
Thread-1: 1 * 8 = 8
```
所有的Java程序最少执行一个线程。当我们运行Java程序时，Java虚拟机会运行一个线程，调用含有`main()`方法的程序。

当调用`Thread`对象的`start()`方法时，就会创建另外一个线程。调用多少次`start()`方法，就会创建多少个线程。

当所有线程执行完成后，Java程序会随之终止。（非特殊情况下，是所有非后台（non-daemon）线程执行完成）当启动线程（例如执行`main()`方法的线程）终止后，其余线程会继续执行直到完成计算任务。当其中一个线程调用`System.exit()`，请求Java虚拟机中止程序时，所有线程中止其执行。

调用`Thread`对象的`run()`方法时，不会创建线程；同样，调用实现`Runnable`接口的类`run()`方法时，也不会创建线程。只有调用`Thread`对象的`start()`方法时，才会创建线程。


### 永无止境

正如本节开头所说，还有另外一种创建线程的方法：继承`Thread`类，重写`run()`方法，这样，就可以创建一个`Thread`子类的对象，然后调用该对象的`start()`方法来创建线程。

>因为准备面试，找来一堆Java多线程方面的资料，其中包括这本《Java 7 Concurrency Cookbook》，讲解的非常浅显易懂，非常适合对多线程了解不多，又想认真学习一下的朋友。找了找，没找到中文版，干脆自己动手丰衣足食。所以，计划出一个非官方翻译版，书名暂时定为 **《Java7并发示例集》**。

### 小有所成
原书没有完整代码，不利于查看。所以，D瓜哥加了一个小节，专门展示本节所示的完整版代码。

#### Calculator类的完整代码
```Java
package com.diguage.books.concurrencycookbook.chapter1.recipe1;

/**
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-13
 * Time: 21:42
 */
public class Calculator implements Runnable {
    private int number;

    public Calculator(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.printf("%s: %d * %d = %d\n",
                    Thread.currentThread().getName(),
                    number, i, i * number);
        }
    }
}
```

#### Main类的完整代码
```Java
package com.diguage.books.concurrencycookbook.chapter1.recipe1;

/**
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-13
 * Time: 19:46
 */
public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Calculator calculator = new Calculator(i);
            Thread thread = new Thread(calculator);
            thread.start();
        }
    }
}
```