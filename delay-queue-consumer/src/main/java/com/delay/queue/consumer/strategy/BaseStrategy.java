package com.delay.queue.consumer.strategy;

import com.delay.queue.common.constants.RedisConstants;
import com.delay.queue.common.utils.StringUtil;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @description: 策略基类
 * @author: 贾诩
 * @date: 2021/3/28 14:48
 */
public class BaseStrategy {

    public boolean ack(StringRedisTemplate stringRedisTemplate, String queueId) {
        stringRedisTemplate.opsForHash().delete(RedisConstants.DELAY_QUEUE_JOBPOOL_KEY, queueId);
        stringRedisTemplate.opsForZSet().remove(StringUtil.getDelayBucketKey(queueId), queueId);
        return true;
    }
}