# 获取和设置线程信息

`Thread`类包含几个属性，这些属性所表示的信息能帮助我们识别线程、观察其状态、控制其优先级等。这些线程包括如下几种：

* **ID：** 该属性表示每个线程的唯一标识；
* **Name：** 该属性存储每个线程的名称；
* **Priority：** 该属性存储每个`Thread`对象的优先级。线程优先级分1到10十个级别，1表示最低优先级，10表示最高优先级。并不推荐修改线程的优先级，但是如果确实有这方面的需求，也可以尝试一下。
* **Status:** 该属性存储线程的状态。线程共有六种不同的状态：新建（new）、运行（runnable）、阻塞（blocked）、等待（waiting）、限时等待（time waiting）或者终止（terminated）。线程的状态必定是其中一种。

在本小节，我们将开发一个程序，程序中新建十个线程，并且设定每个线程的名称和优先级。然后执行线程，观察线程的状态信息，直到线程执行结束。再说明一点，这些线程还是计算一个数的乘法表。


### 知其然

按照下面所示步骤，来实现该示例：

1. 创建一个名为` Calculator`的类，实现`Runnable`接口。代码如下：
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

4. 现在，我们来实现示例应用的主类(main class)。创建名为`Main`的类，在该类中添加`main`方法。代码如下：
```Java
public class Main {
    public static void main(String[] args) {
```

5. 创建两个包含十个元素数组，一个是`Thread`类型的，一个是`Thread.State`类型，然后全部初始化。这两个数组，一个用于存储我们将以执行的线程，另外一个存储这些线程的状态。代码如下：
```Java
        Thread[] threads = new Thread[10];
        Thread.State[] status = new Thread.State[threads.length];
```

6. 创建十个`Calculator`对象，并且使用不同的数来初始化每个对象。使用这些`Calculator`对象创建十个`Thread`对象，存储到上面的创建数组中。同时，设置这些线程的优先级，五个设置成最高优先级；五个设置成最低优先级。代码如下：
```Java
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Calculator(i));
            if ((i % 2) == 0) {
                threads[i].setPriority(Thread.MAX_PRIORITY);
            } else {
                threads[i].setPriority(Thread.MIN_PRIORITY);
            }
            threads[i].setName("Thread-" + i);
        }
```

7. 创建一个`PrintWriter`对象，用于将线程状态的变换记录到文件中。代码如下：
```Java
        try (FileWriter file = new FileWriter("D:\\thread.log");
             PrintWriter pw = new PrintWriter(file)) {
```
> 这里使用了Java7的语法，所以请将JDK升级到第七版，把编译工具设置成Java7。否则会报语法错误。

8. 将所有线程的状态写到文件中。现在，现在的状态应该是`新建（NEW）`。代码如下：
```Java
            for (int i = 0; i < threads.length; i++) {
                Thread thread = threads[i];
                pw.println("Main: Status of Thread " + i +
                        " : " + threads[i].getState());
                status[i] = threads[i].getState();
            }
```

9. 启动所有线程。代码入下：
```Java
            for (int i = 0; i < threads.length; i++) {
                threads[i].start();
            }
```

10. 另外一方面，我们一直监控线程，直到线程执行结束。如果我们检测到线程的状态有所改变，则立即将线程状态写入到文件中。代码如下：
```Java
            boolean finish = false;

            while (!finish) {
                for (int i = 0; i < threads.length; i++) {
                    if (threads[i].getState() != status[i]) {
                        writeThreadInfo(pw, threads[i], status[i]);
                        status[i] = threads[i].getState();
                    }
                }
                finish = true;
                for (int i = 0; i < threads.length; i++) {
                    finish = finish
                            && (threads[i].getState() == Thread.State.TERMINATED);
                }
            }
```

11. 实现`writeThreadInfo`方法，该方法将线程的ID、名称、优先级、旧的状态、新的状态写入到文件中。代码如下：
```Java
    /**
     * 将一个线程的状态输出到一个文件中。
     *
     * @param pw     PrintWriter对象
     * @param thread 需要输出状态的线程对象
     * @param state  线程的旧状态
     */
    private static void writeThreadInfo(PrintWriter pw,
                                        Thread thread, Thread.State state) {
        pw.printf("Main : Id %d = %s\n", thread.getId(), thread.getName());
        pw.printf("Main : Priority: %d\n", thread.getPriority());
        pw.printf("Main : Old State: %s\n", state);
        pw.printf("Main : New State: %s\n", thread.getState());
        pw.printf("Main : ********************************\n");
    }
```

12. 运行该示例，然后打开`thread.log`文件，查看所有线程的演化过程。


### 知其所以然

下面是`thread.log`文件的内容片段。从文件内容可以看出，高优先级的线程大致比低优先级的线程较早完成执行。另外，也可以看到每个线程的状态演化过程。

```Java
Main : ********************************
Main : Id 11 = Thread-2
Main : Priority: 10
Main : Old State: BLOCKED
Main : New State: TERMINATED
Main : ********************************
Main : Id 13 = Thread-4
Main : Priority: 10
Main : Old State: BLOCKED
Main : New State: TERMINATED
Main : ********************************
Main : Id 14 = Thread-5
Main : Priority: 1
Main : Old State: BLOCKED
Main : New State: TERMINATED
Main : ********************************
```

下面是控制台的输出片段。输出的是每个线程计算的乘法表，以及所有的线程计算过程。同时，从这里可以更细粒度地看到每个线程的演化过程。

```Java
Thread-8: 8 * 2 = 16
Thread-8: 8 * 3 = 24
Thread-8: 8 * 4 = 32
Thread-6: 6 * 0 = 0
Thread-6: 6 * 1 = 6
Thread-6: 6 * 2 = 12
Thread-6: 6 * 3 = 18
Thread-6: 6 * 4 = 24
Thread-6: 6 * 5 = 30
Thread-6: 6 * 6 = 36
Thread-6: 6 * 7 = 42
Thread-6: 6 * 8 = 48
Thread-6: 6 * 9 = 54
Thread-5: 5 * 0 = 0
Thread-5: 5 * 1 = 5
Thread-5: 5 * 2 = 10
Thread-5: 5 * 3 = 15
Thread-5: 5 * 4 = 20
```

`Thread`类有可以存储线程信息所需的所有属性。Java虚拟机使用线程优先级来每个时刻调度一个线程来使用CPU，并且根据线程的情况来设置其每个线程的状态。

如果没有设置线程的名称，Java虚拟机会使用这种格式来时分配一个名称，`Thread-XX`，其中`XX`是一个数字。我们不能修改线程的ID以及线程的状态。`Thread`类也没有实现`setId()`和`setStatus()`方法，以允许做出这些修改。


### 永无止境

在本节，我们学习了如何使用`Thread`对象来访问线程信息。其实，`Runnable`的实现类也运行我们访问这些信息。`Thread`类的静态方法`currentThread()`可以获取正在执行的`Runnable`实现类的对象，进而访问线程的信息。

需要注意的是，如果尝试设置1到10以外的优先级，`setPriority()`会抛出名为`IllegalArgumentException`的异常，

### 小有所成

#### Calculator类的完整代码
```Java
package com.diguage.books.concurrencycookbook.chapter1.recipe2;

/**
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-13
 * Time: 19:49
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
package com.diguage.books.concurrencycookbook.chapter1.recipe2;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-13
 * Time: 19:51
 */
public class Main {
    public static void main(String[] args) {
        Thread[] threads = new Thread[10];
        Thread.State[] status = new Thread.State[threads.length];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Calculator(i));
            if ((i % 2) == 0) {
                threads[i].setPriority(Thread.MAX_PRIORITY);
            } else {
                threads[i].setPriority(Thread.MIN_PRIORITY);
            }
            threads[i].setName("Thread-" + i);
        }

        try (FileWriter file = new FileWriter("D:\\thread.log");
             PrintWriter pw = new PrintWriter(file)) {
            for (int i = 0; i < threads.length; i++) {
                Thread thread = threads[i];
                pw.println("Main: Status of Thread " + i +
                        " : " + threads[i].getState());
                status[i] = threads[i].getState();
            }

            for (int i = 0; i < threads.length; i++) {
                threads[i].start();
            }

            boolean finish = false;

            while (!finish) {
                for (int i = 0; i < threads.length; i++) {
                    if (threads[i].getState() != status[i]) {
                        writeThreadInfo(pw, threads[i], status[i]);
                        status[i] = threads[i].getState();
                    }
                }
                finish = true;
                for (int i = 0; i < threads.length; i++) {
                    finish = finish
                            && (threads[i].getState() == Thread.State.TERMINATED);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将一个线程的状态输出到一个文件中。
     *
     * @param pw     PrintWriter对象
     * @param thread 需要输出状态的线程对象
     * @param state  线程的旧状态
     */
    private static void writeThreadInfo(PrintWriter pw,
                                        Thread thread, Thread.State state) {
        pw.printf("Main : Id %d = %s\n", thread.getId(), thread.getName());
        pw.printf("Main : Priority: %d\n", thread.getPriority());
        pw.printf("Main : Old State: %s\n", state);
        pw.printf("Main : New State: %s\n", thread.getState());
        pw.printf("Main : ********************************\n");
    }
}
```