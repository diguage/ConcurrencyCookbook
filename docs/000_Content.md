## 《Java7并发示例集》目录

* ### 第一章：线程管理
  - [线程的创建和执行](./101_Creating_and_running_a_thread.md)
  - [获取和设置线程信息](./102_Getting_and_setting_thread_information.md)
  - [线程中断](./103_Interrupting_a_thread.md)
  - [可控的线程中断](./104_Controlling_the_interruption_of_a_thread.md)
  - [线程休眠与恢复](./105_Sleeping_and_resuming_a_thread.md)
  - [等待线程执行终止](./106_Waiting_for_the_finalization_of_a_thread.md)
  - [守护线程的创建和运行](./107_Creating_and_running_a_daemon_thread.md)
  - [处理线程的非受检异常](./108_Processing_uncontrolled_exceptions_in_a_thread.md)
  - [本地线程变量的使用](./109_Using_local_thread_variables.md)
  - [线程组](./110_Grouping_threads_into_a_group.md)
  - [处理线程组内的非受控异常](./111_Processing_uncontrolled_exceptions_in_a_group_of_threads.md)
  - [使用线程工厂创建线程](./112_Creating_threads_through_a_factory.md)
* ### Chapter 2: Basic Thread Synchronization
  - [Synchronizing a method](./.md)
  - [Arranging independent attributes in synchronized classes](./.md)
  - [Using conditions in synchronized code](./.md)
  - [Synchronizing a block of code with a Lock](./.md)
  - [Synchronizing data access with read/write locks](./.md)
  - [Modifying Lock fairness](./.md)
  - [Using multiple conditions in a Lock](./.md)
* ### Chapter 3: Thread Synchronization Utilities
  - [Controlling concurrent access to a resource](./.md)
  - [Controlling concurrent access to multiple copies of a resource](./.md)
  - [Waiting for multiple concurrent events](./.md)
  - [Synchronizing tasks in a common point](./.md)
  - [Running concurrent phased tasks](./.md)
  - [Controlling phase change in concurrent phased tasks](./.md)
  - [Changing data between concurrent tasks](./.md)
* ### Chapter 4: Thread Executors
  - [Creating a thread executor](./.md)
  - [Creating a fxed-size thread executor](./.md)
  - [Executing tasks in an executor that returns a result](./.md)
  - [Running multiple tasks and processing the frst result](./.md)
  - [Running multiple tasks and processing all the results](./.md)
  - [Running a task in an executor after a delay](./.md)
  - [Running a task in an executor periodically](./.md)
  - [Canceling a task in an executor](./.md)
  - [Controlling a task fnishing in an executor](./.md)
  - [Separating the launching of tasks and the processing of their results in an executor](./.md)
  - [Controlling rejected tasks of an executor](./.md)
* ### Chapter 5: Fork/Join Framework
  - [Creating a Fork/Join pool](./.md)
  - [Joining the results of the tasks](./.md)
  - [Running tasks asynchronously](./.md)
  - [Throwing exceptions in the tasks](./.md)
  - [Canceling a task](./.md)
* ### Chapter 6: Concurrent Collections
  - [Using non-blocking thread-safe lists](./.md)
  - [Using blocking thread-safe lists](./.md)
  - [Using blocking thread-safe lists ordered by priority](./.md)
  - [Using thread-safe lists with delayed elements](./.md)
  - [Using thread-safe navigable maps](./.md)
  - [Generating concurrent random numbers](./.md)
  - [Using atomic variables](./.md)
  - [Using atomic arrays](./.md)
* ### Chapter 7: Customizing Concurrency Classes
  - [Customizing the ThreadPoolExecutor class](./.md)
  - [Implementing a priority-based Executor class](./.md)
  - [Implementing the ThreadFactory interface to generate custom threads](./.md)
  - [Using our ThreadFactory in an Executor object](./.md)
  - [Customizing tasks running in a scheduled thread pool](./.md)
  - [Implementing the ThreadFactory interface to generate custom](./.md)
  - [threads for the Fork/Join framework](./.md)
  - [Customizing tasks running in the Fork/Join framework](./.md)
  - [Implementing a custom Lock class](./.md)
  - [Implementing a transfer Queue based on priorities](./.md)
  - [Implementing your own atomic object](./.md)
* ### Chapter 8: Testing Concurrent Applications
  - [Monitoring a Lock interface](./.md)
  - [Monitoring a Phaser class](./.md)
  - [Monitoring an Executor framework](./.md)
  - [Monitoring a Fork/Join pool](./.md)
  - [Writing effective log messages](./.md)
  - [Analyzing concurrent code with FindBugs](./.md)
  - [Confguring Eclipse for debugging concurrency code](./.md)
  - [Confguring NetBeans for debugging concurrency code](./.md)
  - [Testing concurrency code with MultithreadedTC](./.md)


第二章 : 基本的同步线程 45

介绍 45
同步方法 46
在同步的类里安排独立属性 52
在同步代码中使用条件 57
使用锁来同步一块代码 61
用读/写锁来访问同步数据 65
修改锁的公平性 70
在锁中使用多条件 73

第三章: 线程同步实用程序 83

介绍 83
控制并发访问资源 84
控制并发访问多个资源的副本 89
等待多个并发事件 92
在一个相同点同步任务 97
运行并发阶段性任务 105
在并发阶段性任务控制阶段改变 114
在并发任务间改变数据 120

第四章: 线程执行者 125

介绍 125
创建一个线程执行者 126
创建一个大小可改的线程执行者 131
用执行者执行任务并返回结果 134
运行多个任务并处理第一个结果 138
运行多个任务并处理所有的结果 143
在延迟后执行者运行任务 148
执行者定期的执行任务 151
执行者取消任务 155
执行者控制一个结束任务 158
执行者分离运行任务和处理结果 161
执行者控制被拒绝的任务 167
<h3>第五章: Fork/Join 框架 171
介绍 171
创建 Fork/Join 池 173
加入任务的结果 180
异步运行任务 189
任务中抛出异常 195
取消任务 199

第六章并发集合 207

介绍 207
使用非阻塞线程安全列表 208
使用阻塞线程安全列表 213
用优先级对使用阻塞线程安全列表排序 216
使用线程安全与带有延迟元素的列表 221
使用线程安全的导航地图 226
生成并行随机数 231
使用原子变量 233
使用原子阵列 237

第七章: 并发类的定制 243

介绍 243
定制ThreadPoolExecutor 类 244
实现一个优先级制的执行者类 249
实现ThreadFactory接口来生成自定义线程 253
在执行者对象中使用我们的 ThreadFactory 257
在计划好的线程池中定制运行任务 259
实现ThreadFactory接口来生成自定义线程给Fork/Join框架 267
在Fork/Join框架中定制运行任务 272
实现一个自定义锁类 277
实现一个基于优先级传输Queue 283
实现你自己的原子对象 292

第八章: 测试并发应用程序 299

介绍 299
监控锁界面 300
监控Phaser类 304
监控执行者框架 308
监控Fork/Join池 311
编写有效的日志 316
FindBugs分析并发代码 321
配置Eclipse来调试并发代码 326
配置NetBeans来调试并发代码/ 329
MultithreadedTC测试并发代码 335
