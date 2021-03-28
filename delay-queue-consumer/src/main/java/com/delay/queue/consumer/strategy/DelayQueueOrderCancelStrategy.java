package com.delay.queue.consumer.strategy;

import com.alibaba.fastjson.JSON;
import com.delay.queue.DelayQueueJob;
import com.delay.queue.annotation.RedisDelayTopic;
import com.delay.queue.common.constants.TopicConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @description: 未支付订单自定取消
 * @author: 贾诩
 * @date: 2021/3/28 8:25
 */
@Component
@RedisDelayTopic(topic = TopicConstants.ORDER_CANCE)
public class DelayQueueOrderCancelStrategy extends BaseStrategy implements DelayQueueStrategy {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean consume(DelayQueueJob delayQueueJob) {
        /**
         * TODO 处理业务逻辑
         */
        System.out.println(JSON.toJSONString(delayQueueJob));
        ack(stringRedisTemplate, delayQueueJob.getQueueId());
        return true;
    }
}