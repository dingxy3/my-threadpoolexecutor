package thread.pool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @param
 * @Author: dingxy3
 * @Description:
 * @Date: Created in  2018/6/17
 **/
public class MyThreadPool3 {
    public static void main(String[] args) {
        int corePoolSize = 1;
        int maxPoolSize = 1 ;
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(1);

        ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 0, TimeUnit.SECONDS, queue,new ThreadPoolExecutor.DiscardPolicy());

        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

        pool.submit(new Runnable() {
            int index = 0;
            @Override
            public void run() {
                System.out.println((Thread.currentThread().getName()+"begin run task "+index));
            }
        });
        pool.shutdown();


    }
}
