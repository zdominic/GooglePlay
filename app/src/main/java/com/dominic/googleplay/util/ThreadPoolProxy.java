package com.dominic.googleplay.util;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dominic on 2017/2/21.
 */

public class ThreadPoolProxy {


    private static ThreadPoolProxy sThreadPoolProxy;

    private ThreadPoolExecutor mThreadPoolExecutor;

    private ThreadPoolProxy(){
        mThreadPoolExecutor = new ThreadPoolExecutor(3, 6, 0, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(50),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());

//        Executors.newFixedThreadPool(3);
    }

    public static ThreadPoolProxy getInstance() {
        if (sThreadPoolProxy == null) {
            synchronized (ThreadPoolProxy.class) {
                if (sThreadPoolProxy == null) {
                    sThreadPoolProxy = new ThreadPoolProxy();
                }
            }
        }
        return sThreadPoolProxy;
    }

    public void execute(Runnable task) {
        mThreadPoolExecutor.execute(task);
    }

    public void remove(Runnable task) {
        mThreadPoolExecutor.remove(task);
    }

}
