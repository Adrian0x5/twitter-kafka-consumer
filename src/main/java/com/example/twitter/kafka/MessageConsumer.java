package com.example.twitter.kafka;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MessageConsumer {

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
        System.out.println("Stopping consumer... ");
    }

    public void setConsumerThreadList(List<ConsumerThread> consumerThreadList) {
        this.consumerThreadList = consumerThreadList;
    }
}
