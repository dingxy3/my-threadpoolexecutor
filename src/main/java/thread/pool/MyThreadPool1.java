package thread.pool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @param
 * @Author: dingxy3
 * @Description:构造方法2 没有线程工厂
 * @Date: Created in  2018/6/17
 **/
public class MyThreadPool1 {

    public static void main(String[] args) {
        int corePoolSize = 1;
        int maxPoolSize = 1 ;
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(1);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize,maxPoolSize,0, TimeUnit.SECONDS,queue);

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
