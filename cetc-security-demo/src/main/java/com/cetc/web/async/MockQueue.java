package com.cetc.web.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/*
    模拟订单队列
 */
@Component
public class MockQueue {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private  String placeOrder;
    private  String completeOrder;


    public String getPlaceOrder() {
        return placeOrder;
    }

    public void setPlaceOrder(String placeOrder) throws InterruptedException {
        new Thread(()->{
            logger.info("消息队列接到应用1服务器发送的下单请求");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("模拟应用2服务器处理消息队列的订单请求");
            this.completeOrder = placeOrder;
            logger.info("下单请求处理完毕,应用2的处理结果发送至消息队列,订单号:"+placeOrder);
        }).start();
        //this.placeOrder = placeOrder;
    }

    public String getCompleteOrder() {
        return completeOrder;
    }

    public void setCompleteOrder(String completeOrder) {
        this.completeOrder = completeOrder;
    }




}
