package com.delay.queue.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: TODO
 * @author: 贾诩
 * @date: 2021/3/27 13:38
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping("main")
    public String index() {
        log.debug("进入main方法");
        log.info("进入main方法");
        log.warn("进入main方法");
        log.error("进入main方法");
        return "success";
    }
}