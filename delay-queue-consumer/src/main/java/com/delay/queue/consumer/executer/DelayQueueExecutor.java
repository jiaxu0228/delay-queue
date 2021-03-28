package com.delay.queue.consumer.executer;

import com.delay.queue.DelayQueueJob;
import com.delay.queue.consumer.factory.DelayQueueConsumerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @description: TODO
 * @author: 贾诩
 * @date: 2021/3/28 12:15
 */
@Component
public class DelayQueueExecutor {

    @Autowired
    private DelayQueueConsumerFactory delayQueueConsumerFactory;

    @Async("delayConsumerExecutor")
    public void consume(DelayQueueJob delayQueueJob) {
        delayQueueConsumerFactory.getStrategyInstance(delayQueueJob.getTopic()).consume(delayQueueJob);
    }
}