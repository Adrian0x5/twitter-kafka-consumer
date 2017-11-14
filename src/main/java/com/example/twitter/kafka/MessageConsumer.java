package com.example.twitter.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

    private List<ConsumerThread> consumerThreadList;

    public void recieveMessage() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        consumerThreadList.forEach(executorService::submit);
        String line = "";
        Scanner in = new Scanner(System.in);
        while (!line.equals("exit")) {
            line = in.next();
        }
        executorService.shutdown();
        logger.info("Stopping consumer... ");
    }

    public void setConsumerThreadList(List<ConsumerThread> consumerThreadList) {
        this.consumerThreadList = consumerThreadList;
    }
}
