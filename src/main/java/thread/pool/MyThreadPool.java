package thread.pool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @param
 * @Author: dingxy3
 * @Description:线程池的实现和四种拒绝策略
 * {
 *     DiscardPolicy  0 1被执行
 *     DiscardOldestPolicy 0 4被执行
 *     CallerRunsPolicy 给调用当前线程的线程执行 01234
 *     AbortPolicy 0 其他被丢弃抛异常
 *
 * }
 * @Date: Created in  2018/6/17
 **/
public class MyThreadPool {
    public static void main(String[] args) {

        int corePoolSize = 1;

        int maxPoolSize = 1;

        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(1);

        ExecutorService pool = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 0, TimeUnit.SECONDS, queue, new ThreadFactory() {

            private final AtomicLong THREAD_COUNT = new AtomicLong(1L);

            @Override
            public Thread newThread(Runnable r) {

                Thread t = new Thread(r);

                t.setName("thread:" + this.THREAD_COUNT.getAndIncrement());

                t.setDaemon(false);

                return t;
            }
        },new ThreadPoolExecutor.DiscardPolicy());

        for (int i = 0 ;i<5 ;i++){
            final int index = i;
            pool.submit(new Runnable() {
                 @Override
                 public void run() {
                     System.out.println((Thread.currentThread().getName()+"begin run task "+index));
                }
             });
        }
        pool.shutdown();
    }
}
