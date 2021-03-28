package com.delay.queue.consumer.disruptor;

import lombok.Data;

/**
 * @description: DisruptorEvent 事件声明
 * @author: 贾诩
 * @date: 2021/3/28 11:52
 */
@Data
public class DisruptorEvent<T> {
    private T t;
}