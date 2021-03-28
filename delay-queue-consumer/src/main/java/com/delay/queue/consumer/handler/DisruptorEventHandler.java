package com.delay.queue.consumer.handler;

import com.delay.queue.DelayQueueJob;
import com.delay.queue.consumer.disruptor.DisruptorEvent;
import com.delay.queue.consumer.executer.DelayQueueExecutor;
import com.delay.queue.consumer.factory.DelayQueueConsumerFactory;
import com.lmax.disruptor.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: DisruptorEventHandler
 * @author: 贾诩
 * @date: 2021/3/28 11:56
 */
@Component
public class DisruptorEventHandler implements EventHandler<DisruptorEvent<DelayQueueJob>> {

    @Autowired
    private DelayQueueExecutor delayQueueExecutor;

    @Override
    public void onEvent(DisruptorEvent<DelayQueueJob> delayQueueJobDisruptorEvent, long l, boolean b) throws Exception {
        DelayQueueJob delayQueueJob = delayQueueJobDisruptorEvent.getT();
        delayQueueExecutor.consume(delayQueueJob);
    }
}