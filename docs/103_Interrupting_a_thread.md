# Java7并发示例集103：线程中断

一个多线程的Java程序，直到所有线程执行完成，整个程序才会退出。（需要注意的是，是所有非后台线程（non-daemon thread）执行完成；如果一个线程执行了`System.exit()`方法，程序也会退出。）有时，你想中止一个线程的执行，例如你想退出程序，或者你想取消一个正在执行的任务等。

Java提供了中断机制，可以让我们显式地中断我们想中止执行的线程。中断机制的一个特征就是我们可以检查线程是否已经被中断，进而决定是否响应中止请求。线程也可以忽略中止请求，继续执行。

在本节，我们所开发的示例程序将会创建一个线程，五秒钟后，利用中断机制强制中止这个线程。


### 知其然

按照下面步骤所示，完成示例程序。

1. 创建一个名为`PrimeGenerator`的类，并且继承`Thread`类。代码如下：
```Java
public class PrimeGenerator extends Thread {
```

2. 重写`run()`方法，在方法中添加一个无限循环，在循环内，通过计算来检查从1开始的连续正整数是否为素数。如果是，则打印到控制台。代码如下：
```Java
    @Override
    public void run() {
        long number = 1L;
        while (true) {
            if (isPrime(number)) {
                System.out.printf("Number %d \tis Prime.", number);
            }
```

3. 在处理一个数字之后，通过调用`isInterrupted()`方法来检查线程是否被中断。如果该方法返回`true`，则向控制台打印一句话，然后中止线程执行。代码如下：
```Java
            if (isInterrupted()) {
                System.out.println("The Prime Generator has been Interrupted");
                return;
            }

            number++;
        }
    }
```

4. 实现`isPrime()`方法，该方法用于判断参数是否为素数，如果是则返回`true`，否则返回`false`。代码如下：
```Java
    /**
     * 判断参数是否为素数
     *
     * @param number 需要判断的数字
     * @return
     */
    private boolean isPrime(long number) {
        if (number <= 2) {
            return true;
        }

        for (int i = 2; i < number; i++) {
            if ((number % i) == 0) {
                return false;
            }
        }

        return true;
    }
```

5. 现在，实现示例程序的主类，`Main`类，同时实现`main()`方法。代码如下：
```Java
public class Main {
    public static void main(String[] args) {
```

6. 创建一个`PrimeGenerator`对象，并且启动该线程。代码如下：
```Java
        Thread task = new PrimeGenerator();
        task.start();
```

7. 等待五秒钟，然后中止该线程。代码如下：
```Java
        try {
            TimeUnit.SECONDS.sleep(5L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        task.interrupt();
```

8. 运行该示例，查看结果。

### 知其所以然

下面的是示例程序执行的打印片段。我们从打印出的字符可以看出`PrimeGenerator`线程是如何打印输出信息以及当检测到线程被中断时，如何中止其执行的。

```Java
Number 43063 	is Prime.
Number 43067 	is Prime.
Number 43093 	is Prime.
Number 43103 	is Prime.
Number 43117 	is Prime.
The Prime Generator has been Interrupted
```

`Thread`有一个布尔型的熟悉，来表明线程是否被中断。当调用`interrupt()`方法时，就是将其设置为`true`。而`isInterrupted()`方法则是返回该属性的当前值。


### 永无止境

`Thread`还有一个可以检查线程是否中断的方法：即静态方法`interrupted()`，可以检查当前正在执行的线程是否被中断。

> `isInterrupted()`方法和`interrupted()`方法有非常大的不同。前者不会改变线程是否中断的属性值；而后者则可以将其值设置为`false`。`interrupted()`是一个静态方法；平时开发推荐使用`isInterrupted()`方法。

正如前面所述，线程可以忽略中断请求而继续执行。但是，这并不是我们想要的结果。

### 小有所成

示例程序所用的所有代码的完整版本。

#### PrimeGenerator类的完整代码
```Java
package com.diguage.books.concurrencycookbook.chapter1.recipe3;

/**
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-18
 * Time: 11:53
 */
public class PrimeGenerator extends Thread {

    @Override
    public void run() {
        long number = 1L;
        while (true) {
            if (isPrime(number)) {
                System.out.printf("Number %d \tis Prime.\n", number);
            }

            if (isInterrupted()) {
                System.out.println("The Prime Generator has been Interrupted");
                return;
            }

            number++;
        }
    }

    /**
     * 判断参数是否为素数
     *
     * @param number 需要判断的数字
     * @return
     */
    private boolean isPrime(long number) {
        if (number <= 2) {
            return true;
        }

        for (int i = 2; i < number; i++) {
            if ((number % i) == 0) {
                return false;
            }
        }

        return true;
    }
}
```

#### Main类的完整代码
```Java
package com.diguage.books.concurrencycookbook.chapter1.recipe3;

import java.util.concurrent.TimeUnit;

/**
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-18
 * Time: 12:33
 */
public class Main {
    public static void main(String[] args) {
        Thread task = new PrimeGenerator();
        task.start();

        try {
            TimeUnit.SECONDS.sleep(5L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        task.interrupt();
    }
}
```