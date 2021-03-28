package com.delay.queue;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description: DelayQueueJob
 * @author: 贾诩
 * @date: 2021/3/27 19:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DelayQueueJob implements Serializable {
    /**
     * 延迟任务的唯一标识，用于检索任务
     */
    private String queueId;
    /**
     * 任务类型
     */
    private String topic;
    /**
     * 调用者传延迟的毫秒数，消费者放入redis中为延迟时间的时间戳
     */
    private long delayTime;
    /**
     * 任务具体的消息内容，用于处理具体业务逻辑用
     */
    private String message;
    /**
     * 重试次数
     */
    private int retryCount = 0;
    /**
     * 失败次数
     */
    private int failCount = 0;
    /**
     * 优先级 优先级越低越先触发执行
     */
    private int priority = 0;
    /**
     * 延迟消息创建时间戳
     */
    private long createTime = System.currentTimeMillis();


}