package com.delay.queue.consumer.handler;

import com.delay.queue.consumer.executer.DelayBucketExecuter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @description: DelayBucketHandler
 * @author: 贾诩
 * @date: 2021/3/28 19:37
 */
@Component
@Scope("prototype")
public class DelayBucketHandler implements Runnable {
    @Autowired
    private DelayBucketExecuter delayBucketExecuter;

    private String delayBucketKey;

    @Override
    public void run() {
        delayBucketExecuter.run(delayBucketKey);
    }

    public DelayBucketExecuter getDelayBucketExecuter() {
        return delayBucketExecuter;
    }

    public void setDelayBucketExecuter(DelayBucketExecuter delayBucketExecuter) {
        this.delayBucketExecuter = delayBucketExecuter;
    }

    public String getDelayBucketKey() {
        return delayBucketKey;
    }

    public void setDelayBucketKey(String delayBucketKey) {
        this.delayBucketKey = delayBucketKey;
    }
}