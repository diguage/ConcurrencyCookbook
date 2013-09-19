# Java7并发示例集107：守护线程的创建和运行

Java有一种特殊线程，守护线程，这种线程优先级特别低，只有在同一程序中的其他线程不执行时才会执行。

由于守护线程拥有这些特性，所以，一般用为为程序中的普通线程（也称为用户线程）提供服务。它们一般会有一个无限循环，或用于等待请求服务，或用于执行任务等。它们不可以做任何重要的工作，因为我们不确定他们什么时才能分配到CPU运行时间，而且当没有其他线程执行时，它们就会自动终止。这类线程的一个典型应用就是Java的垃圾回收。

在本节示例中，我们将创建两个线程，一个是普通线程，向队列中写入事件；另外一个是守护线程，清除队列中的事件，删除存在时间超过10秒的事件。


### 知其然

按照如下步骤，实现示例程序。

1. 创建`Event`类，该类仅仅用于保存程序执行所需的事件信息。声明两个属性，一个是`java.util.Date`类型的的`date`熟悉，另外一个是`String`类型的`event`属性；然后生成这两个属性的读写方法。代码如下：
```Java
public class Event {
    private Date date;
    private String event;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
```

2. 创建一个名为`WriterTask`的类，并且实现`Runnable`接口。代码如下：
```Java
public class WriterTask implements Runnable {
```

3. 声明一个用来存储事件的队列属性，实现类的构造函数，并且利用其参数来初始化队列属性。代码如下：
```Java
    private Deque<Event> deque;

    public WriterTask(Deque<Event> deque) {
        this.deque = deque;
    }
```

4. 实现该任务的`run()`方法，方法中含有一个遍历100次的循环。在每次遍历中，创建一个新的`Event`对象，然后保存到队列中，再睡眠1秒钟。代码如下：
```Java
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            Event event = new Event();
            event.setDate(new Date());
            event.setEvent(String.format("The thread %s has generated an event",
                    Thread.currentThread().getId()));
            deque.addFirst(event);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
```

5. 创建一个名为`CleanerTask`的类，并继承`Thread`类。代码如下：
```Java
public class CleanerTask extends Thread {
```

6. 声明一个用来存储事件的队列属性，实现类的构造函数，并且利用其参数来初始化队列属性。在构造方法中，通过调用`setDaemon()`方法，将该线程设置为守护线程。代码如下：
```Java
    private Deque<Event> deque;

    public CleanerTask(Deque<Event> deque) {
        this.deque = deque;
        setDaemon(true);
    }
```

7. 实现`run()`方法，方法体内有一个无限循环，用于获取当前时间，然后调用`clearn()`方法。代码如下：
```Java
    @Override
    public void run() {
        while (true) {
            Date date = new Date();
            clean(date);
        }
    }
```

8. 实现`clean()`方法，在该方法内，获取最后面的一个时间，然后检查时间时间和当前时间的时间差，如果在10秒钟之前创建的，则删除当前事件，再检查下一个事件。如果有事件被删除，则显示打印出被删除事件的信息，然后还将打印出队列的最新长度，这样就可以观察到程序的执行进展。代码如下：
```Java
    private void clean(Date date) {
        long difference;
        boolean delete;

        if (deque.size() == 0) {
            return;
        }

        delete = false;
        do {
            Event e = deque.getLast();
            difference = date.getTime() - e.getDate().getTime();
            if (difference > 10000) {
                System.out.printf("Cleaner: %s\n", e.getDate());
                deque.removeLast();
                delete = true;
            }
        } while (difference > 10000);

        if (delete) {
            System.out.printf("Clearner: Size of the queue: %d\n", deque.size());
        }
    }
```

9. 创建程序的主类，`Main`类，然后实现`main()`方法。代码如下：
```Java
public class Main {
    public static void main(String[] args) {
```

10. 使用`Deque`类创建存储事件的队列。代码如下：
```Java
        Deque<Event> deque = new ArrayDeque<>();
```

11. 创建并启动三个`WriterTask`线程和一个`CleanerTask`线程。代码如下：
```Java
        Deque<Event> deque = new ArrayDeque<>();
        WriterTask writer = new WriterTask(deque);
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(writer);
            thread.start();
        }

        CleanerTask cleaner = new CleanerTask(deque);
        cleaner.start();
```

12. 执行程序，查看执行结果。


### 知其所以然

### 永无止境

### 小有所成

本节所用的所有示例代码的完整版。

#### Event类的完整代码
```Java
package com.diguage.books.concurrencycookbook.chapter1.recipe7;

import java.util.Date;

/**
 * 事件信息类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-19
 * Time: 22:56
 */
public class Event {
    private Date date;
    private String event;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
```

#### WriterTask类的完整代码
```Java
package com.diguage.books.concurrencycookbook.chapter1.recipe7;

import java.util.Date;
import java.util.Deque;
import java.util.concurrent.TimeUnit;

/**
 * 每秒生成一个事件。
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-19
 * Time: 22:59
 */
public class WriterTask implements Runnable {
    private Deque<Event> deque;

    public WriterTask(Deque<Event> deque) {
        this.deque = deque;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            Event event = new Event();
            event.setDate(new Date());
            event.setEvent(String.format("The thread %s has generated an event",
                    Thread.currentThread().getId()));
            deque.addFirst(event);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
```

#### CleanerTask类的完整代码
```Java
package com.diguage.books.concurrencycookbook.chapter1.recipe7;

import java.util.Date;
import java.util.Deque;

/**
 * 事件清理
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-19
 * Time: 23:33
 */
public class CleanerTask extends Thread {
    private Deque<Event> deque;

    public CleanerTask(Deque<Event> deque) {
        this.deque = deque;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (true) {
            Date date = new Date();
            clean(date);
        }
    }

    /**
     * 删除事件。
     *
     * @param date
     */
    private void clean(Date date) {
        long difference;
        boolean delete;

        if (deque.size() == 0) {
            return;
        }

        delete = false;
        do {
            Event e = deque.getLast();
            difference = date.getTime() - e.getDate().getTime();
            if (difference > 10000) {
                System.out.printf("Cleaner: %s\n", e.getDate());
                deque.removeLast();
                delete = true;
            }
        } while (difference > 10000);

        if (delete) {
            System.out.printf("Clearner: Size of the queue: %d\n", deque.size());
        }
    }
}
```

#### Main类的完整代码
```Java
package com.diguage.books.concurrencycookbook.chapter1.recipe7;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-19
 * Time: 23:54
 */
public class Main {
    public static void main(String[] args) {
        Deque<Event> deque = new ArrayDeque<>();
        WriterTask writer = new WriterTask(deque);
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(writer);
            thread.start();
        }

        CleanerTask cleaner = new CleanerTask(deque);
        cleaner.start();
    }
}
```
