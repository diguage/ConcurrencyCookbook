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

3. 实现问题程序的主类。
```Java
public class Core {
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


```Java

```


```Java

```


```Java

```


```Java

```


```Java

```


```Java

```


```Java

```

### 知其所以然

### 永无止境

### 小有所成

#### Main类的完整代码
```Java

```
