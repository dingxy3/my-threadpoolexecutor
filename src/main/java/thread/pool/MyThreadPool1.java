package thread.pool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

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

        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());

        for (int i = 0 ; i<5 ;i++){
            FutureTask<Integer> future  = new FutureTask(new FutureThread(i));
            pool.submit(future);
        }

        pool.shutdown();


    }

   static   class FutureThread implements Callable{

       private  int index  ;

       FutureThread(int index){
           this.index = index;
       }

        @Override
        public Object call() throws Exception {

             System.out.println((Thread.currentThread().getName()+"begin run task "+index));

            return index;
        }
    }
}
