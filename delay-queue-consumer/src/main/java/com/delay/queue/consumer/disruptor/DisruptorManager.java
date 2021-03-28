package com.delay.queue.consumer.disruptor;

import com.delay.queue.DelayQueueJob;
import com.delay.queue.consumer.handler.DisruptorEventHandler;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;

/**
 * @description: DisruptorManager 初始化
 * @author: 贾诩
 * @date: 2021/3/28 12:00
 */
@Component
@Order(0)
public class DisruptorManager implements CommandLineRunner {

    private static final int bufferSize = 1024 * 1024;

    private Disruptor<DisruptorEvent<DelayQueueJob>> disruptor;
    @Autowired
    private DisruptorEventHandler disruptorEventHandler;

    @Override
    public void run(String... args) throws Exception {
        disruptor = new Disruptor<DisruptorEvent<DelayQueueJob>>(new DisruptorEventFactory<>(), bufferSize, Executors.defaultThreadFactory(), ProducerType.SINGLE, new YieldingWaitStrategy());
        disruptor.handleEventsWith(disruptorEventHandler);
        disruptor.start();
    }

    @PreDestroy
    private void close() {
        if (disruptor != null) {
            disruptor.shutdown();
        }
    }

    public Disruptor<DisruptorEvent<DelayQueueJob>> getDisruptor() {
        return disruptor;
    }

    public void setDisruptor(Disruptor<DisruptorEvent<DelayQueueJob>> disruptor) {
        this.disruptor = disruptor;
    }
}