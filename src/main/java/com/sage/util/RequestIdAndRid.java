package com.sage.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.sage.util.HttpUtil.getParamString;

/**
 * 多线程测试
 */
public class RequestIdAndRid {


    public static void main(String[] args) {

        RequestIdAndRid requestIdAndRid = new RequestIdAndRid();
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            requestIdAndRid.print(cachedThreadPool,i);
        }
    }

    private  void print(ExecutorService cachedThreadPool,int d){

        cachedThreadPool.execute(()->{
            for (int i = 0; i < 100; i++) {
                System.out.println(d+"----"+i);
            }
        });
    }

}
