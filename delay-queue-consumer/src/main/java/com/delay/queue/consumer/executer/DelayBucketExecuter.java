package com.delay.queue.consumer.executer;

import com.alibaba.fastjson.JSON;
import com.delay.queue.DelayQueueJob;
import com.delay.queue.common.constants.RedisConstants;
import com.delay.queue.common.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: TODO
 * @author: 贾诩
 * @date: 2021/3/27 22:13
 */
@Component
@Scope("prototype")
public class DelayBucketExecuter {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private String delayBuckeyKey = "";

    /**
     * @description: 从DelayBucket获取最小score的queueId
     * @author: 贾诩
     * @date: 2021/3/27 22:17
     * @param: [delayBucketKey]
     * @return: java.lang.String
     */
    private String getElementFromBucket(String delayBucketKey) {
        String queueId = "";
        Set<String> ranges = stringRedisTemplate.opsForZSet().range(delayBucketKey, 0, 0);
        if (!CollectionUtils.isEmpty(ranges)) {
            queueId = ranges.iterator().next();
        }
        return queueId;
    }

    /**
     * @description: 获取delayBucketJob
     * @author: 贾诩
     * @date: 2021/3/27 22:24
     * @param: [queueId]
     * @return: com.delay.queue.DelayQueueJob
     */
    private DelayQueueJob getDelayQueueJob(String queueId) {
        DelayQueueJob delayQueueJob = null;
        Object object = stringRedisTemplate.opsForHash().get(RedisConstants.DELAY_QUEUE_JOBPOOL_KEY, queueId);
        if (object != null) {
            delayQueueJob = JSON.parseObject(object.toString(), DelayQueueJob.class);
        }
        return delayQueueJob;
    }

    public void run(String delayBucketKey) {
        this.delayBuckeyKey = delayBucketKey;
        while (true) {
            DelayQueueJob delayQueueJob = null;
            try {
                lock.lock();
                String queueId = getElementFromBucket(delayBucketKey);
                if (StringUtil.isBlank(queueId)) {
                    condition.await();
                    queueId = getElementFromBucket(delayBucketKey);
                    if (StringUtil.isBlank(queueId)) {
                        continue;
                    }
                }
                delayQueueJob = getDelayQueueJob(queueId);
                if (delayQueueJob == null) {
                    stringRedisTemplate.opsForZSet().remove(delayBucketKey, queueId);
                    continue;
                }
                long delayTime = delayQueueJob.getDelayTime();
                long now = System.currentTimeMillis();
                if (delayTime > now) {
                    condition.await(delayTime - now, TimeUnit.MILLISECONDS);
                    continue;
                }
                consume(delayQueueJob);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * @description:消费处理逻辑
     * @author: 贾诩
     * @date: 2021/3/28 19:34
     * @param: [delayQueueJob]
     * @return: void
     */
    private void consume(DelayQueueJob delayQueueJob) {
        String queueId = delayQueueJob.getQueueId();
        stringRedisTemplate.opsForHash().delete(RedisConstants.DELAY_QUEUE_JOBPOOL_KEY, queueId);
        stringRedisTemplate.opsForZSet().remove(delayBuckeyKey, queueId);
        //TODO 消费处理
        System.out.println(JSON.toJSONString("消息消费触发：" + delayQueueJob));
    }

    /**
     * @description:信号通知
     * @author: 贾诩
     * @date: 2021/3/28 19:57
     * @param: []
     * @return: void
     */
    public void singal() {
        try {
            lock.lock();
            condition.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}