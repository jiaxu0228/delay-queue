package com.delay.queue.common.utils;

import com.delay.queue.common.constants.RedisConstants;

public class StringUtil {

    public static final String EMPTY_STRING = "";

    /**
     * 字符串连接
     *
     * @param objs
     * @return String
     */
    public static String concat(Object... objs) {
        if (null == objs || objs.length < 1)
            return EMPTY_STRING;
        StringBuffer sb = new StringBuffer();
        for (Object obj : objs) {
            sb.append(null == obj ? EMPTY_STRING : obj.toString());
        }
        return sb.toString();
    }

    /**
     * 是否为空
     *
     * @param str
     * @return boolean
     */
    public static boolean isBlank(String str) {
        return str == null || str.length() <= 0;
    }

    /**
     * 是否不为空
     * @param str
     * @return boolean
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 获取 delayBucket key
     *
     * @param queueId
     * @return String
     */
    public static String getDelayBucketKey(String queueId) {
        return RedisConstants.DELAY_QUEUE_BUCKET_PREFIX + Math.abs(queueId.hashCode()) % RedisConstants.DELAY_BUCKET_NUM;
    }
}