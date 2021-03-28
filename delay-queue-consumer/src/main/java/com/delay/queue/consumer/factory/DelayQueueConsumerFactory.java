package com.delay.queue.consumer.factory;

import com.delay.queue.annotation.RedisDelayTopic;
import com.delay.queue.consumer.strategy.DelayQueueStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 策略工厂
 * @author: 贾诩
 * @date: 2021/3/28 8:31
 */
@Component
public class DelayQueueConsumerFactory {

    @Autowired
    private List<DelayQueueStrategy> list;
    private Map<String, DelayQueueStrategy> strategyCache = new HashMap<String, DelayQueueStrategy>();

    public DelayQueueStrategy getStrategyInstance(String topic) {
        DelayQueueStrategy delayQueueStrategy = strategyCache.get(topic);
        return delayQueueStrategy;
    }

    @PostConstruct
    private void strategyCache() {
        if (!CollectionUtils.isEmpty(list)) {
            for (DelayQueueStrategy delayQueueStrategy : list) {
                RedisDelayTopic redisDelayTopic = delayQueueStrategy.getClass().getAnnotation(RedisDelayTopic.class);
                strategyCache.put(redisDelayTopic.topic(), delayQueueStrategy);
            }
        }
    }
}