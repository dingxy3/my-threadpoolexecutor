package thread.pool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @param
 * @Author: dingxy3
 * @Description:
 * @Date: Created in  2018/6/17
 **/
public class MyThreadPool2 {
    public static void main(String[] args) {
        int corePoolSize = 1;
        int maxPoolSize = 1 ;
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(1);

        ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 0, TimeUnit.SECONDS, queue, new ThreadFactory() {
           private final AtomicLong THREAD_COUNT = new AtomicLong(1L);
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("线程 name"+THREAD_COUNT.getAndIncrement());
                return t ;
            }
        });

        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        for (int i = 0 ;i<5 ;i++) {
            final int index = i;
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println((Thread.currentThread().getName() + "begin run task " + index));
                }
            });
        }
            pool.shutdown();


    }
}
