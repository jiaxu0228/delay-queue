package com.delay.queue.api;

import com.delay.queue.DelayQueueJob;

/**
 * @description: 延迟消息生产api
 * @author: 贾诩
 * @date: 2021/3/27 19:11
 */
public interface DelayQueueProducerApi {
    /**
     * @description: 延迟消息生产
     * @author: 贾诩
     * @date: 2021/3/27 19:12
     * @param: [delayQueueJob]
     * @return: java.lang.Boolean
     */
    public Boolean produceDelayMessage(DelayQueueJob delayQueueJob);
}
