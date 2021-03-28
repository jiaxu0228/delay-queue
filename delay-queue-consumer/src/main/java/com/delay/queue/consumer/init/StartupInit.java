package com.delay.queue.consumer.init;

import com.delay.queue.common.constants.RedisConstants;
import com.delay.queue.common.utils.ApplicationContextProvider;
import com.delay.queue.consumer.handler.DelayBucketHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description: TODO
 * @author: 贾诩
 * @date: 2021/3/28 19:47
 */
@Component
@Order(1)
public class StartupInit implements CommandLineRunner {

    private ExecutorService executorService = Executors.newFixedThreadPool(RedisConstants.DELAY_BUCKET_NUM);

    public static Map<String, DelayBucketHandler> delayBucketHandlerMap = new HashMap<String, DelayBucketHandler>();

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < RedisConstants.DELAY_BUCKET_NUM; i++) {
            DelayBucketHandler delayBucketHandler = ApplicationContextProvider.getBean("delayBucketHandler", DelayBucketHandler.class);
            String delayBucketKey = RedisConstants.DELAY_QUEUE_BUCKET_PREFIX + i;
            delayBucketHandler.setDelayBucketKey(delayBucketKey);
            delayBucketHandlerMap.put(delayBucketKey, delayBucketHandler);
            executorService.execute(delayBucketHandler);
        }
    }
}