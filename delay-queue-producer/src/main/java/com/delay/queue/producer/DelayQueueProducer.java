package com.delay.queue.producer;

import com.alibaba.fastjson.JSON;
import com.delay.queue.DelayQueueJob;
import com.delay.queue.api.DelayQueueProducerApi;
import com.delay.queue.common.constants.RedisConstants;
import com.delay.queue.common.utils.StringUtil;
import com.delay.queue.consumer.handler.DelayBucketHandler;
import com.delay.queue.consumer.init.StartupInit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @description: DelayQueueProducerAPi 实现
 * @author: 贾诩
 * @date: 2021/3/27 19:23
 */
@Component
@Slf4j
public class DelayQueueProducer implements DelayQueueProducerApi {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Boolean produceDelayMessage(DelayQueueJob delayQueueJob) {
        /**
         * 设置延迟时间为消息创建时间+延迟时长
         */
        String queueId = delayQueueJob.getQueueId();
        Long delayTime = delayQueueJob.getCreateTime() + delayQueueJob.getDelayTime();
        delayQueueJob.setDelayTime(delayTime);
        /**
         * 将延迟消息添加到Jobpool中
         */
        stringRedisTemplate.opsForHash().put(RedisConstants.DELAY_QUEUE_JOBPOOL_KEY, queueId, JSON.toJSONString(delayQueueJob));
        /**
         * 将消息添加至Delay Bucket，用于延迟扫描
         */
        String delayBucketKey = StringUtil.getDelayBucketKey(queueId);
        stringRedisTemplate.opsForZSet().add(delayBucketKey, queueId, Double.parseDouble(StringUtil.concat(
                delayTime, ".", delayQueueJob.getPriority())));
        /**
         * 唤醒延迟消息触发处理
         */
        DelayBucketHandler delayBucketHandler = StartupInit.delayBucketHandlerMap.get(delayBucketKey);
        delayBucketHandler.getDelayBucketExecuter().singal();
        return true;
    }
}