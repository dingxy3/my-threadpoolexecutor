package thread.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author : dingxy3
 * @description :〈描述〉
 * @date : 2018/6/17
 */
public class ThreadPoolUtil
{

    /**
     * 默认最大cpu核心数量2倍
     *
     * @return
     */
    public static ExecutorService newThreadPool()
    {
        int threadNum = Runtime.getRuntime().availableProcessors() * 2;
        return ThreadPoolUtil.newThreadPool(threadNum, threadNum, TimeUnit.MILLISECONDS, 1000);
    }

    public static ExecutorService newThreadPool(int coreThreadNum, int maxThreadNum, TimeUnit
            timeUnit, int queueNum)
    {
        return new ThreadPoolExecutor(coreThreadNum, maxThreadNum, 0L, timeUnit, new
                LinkedBlockingQueue(queueNum), new ThreadFactory()
        {
            private final AtomicLong THREAD_COUNT = new AtomicLong(0L);

            public Thread newThread(Runnable r)
            {
                Thread t = new Thread(r);
                t.setName("tread:" + this.THREAD_COUNT.getAndIncrement());
                t.setDaemon(false);
                return t;
            }
        }, new ThreadPoolExecutor.CallerRunsPolicy());
    }

    /**
     * 测试并发任务,默认8个线程
     *
     * @param taskCount 任务数量
     * @param callable
     * @return
     * @throws InterruptedException
     */
    public static <T> List<Future<T>> execute(int taskCount, Callable<T> callable) throws
            ExecutionException,
            InterruptedException
    {
        ExecutorService service = ThreadPoolUtil.newThreadPool();
        List<Future<T>> taskList = new ArrayList<Future<T>>(taskCount);
        long start = System.currentTimeMillis();
        for (int i = 0; i < taskCount; i++)
        {
            taskList.add(service.submit(callable));
        }
        for (Future future : taskList)
        {
            future.get();
        }
        long end = System.currentTimeMillis();
        long timeConsuming = end - start;
        System.out.println("耗时:" + (timeConsuming) / 1000);
        service.shutdown();
        return taskList;
    }

}
