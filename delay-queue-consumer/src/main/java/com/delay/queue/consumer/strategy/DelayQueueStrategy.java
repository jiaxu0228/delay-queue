package com.delay.queue.consumer.strategy;

import com.delay.queue.DelayQueueJob;
/**
  * @description: 统一策略接口
  * @author: 贾诩
  * @date:  8:23
 */
public interface DelayQueueStrategy {
    /**
     * @description:统一策略方法
     * @author: 贾诩
     * @date: 2021/3/28 8:23
     * @param: [delayQueueJob]
     * @return: boolean
     */
    public boolean consume(DelayQueueJob delayQueueJob);
}
