package com.example.twitter.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;


public class MessageConsumer {

    private List<ConsumerThread> consumerThreadList;

    public void recieveMessage() {
        consumerThreadList.parallelStream().forEach(thread -> new Thread(thread).start());
        String line = "";
        Scanner in = new Scanner(System.in);
        while (!line.equals("exit")) {
            line = in.next();
        }
        consumerThreadList.parallelStream().forEach(c -> c.getKafkaConsumer().wakeup());
        System.out.println("Stopping consumer... ");
    }

    public void setConsumerThreadList(List<ConsumerThread> consumerThreadList) {
        this.consumerThreadList = consumerThreadList;
    }
}
