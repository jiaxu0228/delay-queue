package com.delay.queue.producer.tasktest;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class Task {

    @Async("taskExecutor")
    public void doTaskOne(){
        System.out.println("开始任务");
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("结束任务");
    }

}