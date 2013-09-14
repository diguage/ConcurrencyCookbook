### 简介
在IT圈里，每当我们谈论并发时，必定会说起在一台计算机上同时运行的一系列线程。如果这台电脑上有多个处理器或者是一个多核处理器，那么这时是实实在在的“同时运行”；但是，如果计算机只有一个单核处理器，那么这时的“同时运行”只是表象而已。

所有的现代操作系统全部支持任务的并发执行。你可以边听音乐，边上网看新闻，还不耽误首发电子邮件。我们可以说，这种并发是 **进程级并发** 。在进程内部，我也可以看到有许许多多的并发任务。我们把运行在一个进程里面的并发任务称 **线程**。