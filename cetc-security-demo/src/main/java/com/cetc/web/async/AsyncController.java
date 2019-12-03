package com.cetc.web.async;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class AsyncController {

    @Autowired
    private MockQueue mockQueue;
    @Autowired
    private DeferredResultHolder deferredResultHolder;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("order")
    public DeferredResult<String> order() throws InterruptedException {
        logger.info("主线程开始");
        String orderNum = RandomStringUtils.randomNumeric(8);
        //模拟发送订单到消息队列-应用2处理并返回完成结果。
        mockQueue.setPlaceOrder(orderNum);
        //DeferredResult放入DeferredResultHolder中
        DeferredResult<String> result = new DeferredResult<>();
        deferredResultHolder.getMap().put(orderNum,result);
        logger.info("主线程结束");
        return result;
    }



}
