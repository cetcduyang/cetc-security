package com.cetc.web.async;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class QueueListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private MockQueue mockQueue;
    @Autowired
    private DeferredResultHolder deferredResultHolder;

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        new Thread(()->{
            while (true){
                if(StringUtils.isNotBlank(mockQueue.getCompleteOrder())){
                    String orderNum = mockQueue.getCompleteOrder();
                    logger.info("监听程序，返回订单处理结果："+orderNum);
                    deferredResultHolder.getMap().get(orderNum).setResult("处理订单成功！");
                    mockQueue.setCompleteOrder(null);
                }else{
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }).start();




    }
}
