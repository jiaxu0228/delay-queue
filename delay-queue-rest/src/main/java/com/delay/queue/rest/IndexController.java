package com.delay.queue.rest;

import com.delay.queue.DelayQueueJob;
import com.delay.queue.api.DelayQueueProducerApi;
import com.delay.queue.common.constants.TopicConstants;
import com.delay.queue.producer.DelayQueueProducer;
import com.delay.queue.producer.tasktest.Task;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @description: TODO
 * @author: 贾诩
 * @date: 2021/3/27 13:38
 */
@RestController
@RequestMapping("/index")
@Slf4j
public class IndexController {

    //private static final Logger log = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private DelayQueueProducerApi delayQueueProducerApi;

    @Autowired
    private Task task;

    @RequestMapping("main")
    public String index() {
        log.debug("进入main方法");
        log.info("进入main方法");
        log.warn("进入main方法");
        log.error("进入main方法");
        return "success";
    }

    @RequestMapping("/send")
    public String send() {
        DelayQueueJob delayQueueJob = DelayQueueJob.builder().
                queueId(UUID.randomUUID().toString().replace("-", ""))
                .topic(TopicConstants.ORDER_CANCE)
                .message("content")
                .delayTime(1000 * 5)
                .createTime(System.currentTimeMillis())
                .build();
        delayQueueProducerApi.produceDelayMessage(delayQueueJob);
        DelayQueueJob delayQueueJob2 = DelayQueueJob.builder().
                queueId(UUID.randomUUID().toString().replace("-", ""))
                .topic(TopicConstants.LOGIN)
                .message("content")
                .delayTime(1000 * 10)
                .createTime(System.currentTimeMillis())
                .build();
        delayQueueProducerApi.produceDelayMessage(delayQueueJob2);
        return "success";
    }

    @RequestMapping("/executeTask")
    public String executeTask() {
        task.doTaskOne();
        return "success";
    }
}