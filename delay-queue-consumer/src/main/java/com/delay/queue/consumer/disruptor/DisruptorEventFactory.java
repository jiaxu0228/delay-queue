package com.delay.queue.consumer.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @description: DisruptorEvent 工厂生产
 * @author: 贾诩
 * @date: 2021/3/28 11:54
 */
public class DisruptorEventFactory<T> implements EventFactory<DisruptorEvent<T>> {
    @Override
    public DisruptorEvent<T> newInstance() {
        return new DisruptorEvent<T>();
    }
}