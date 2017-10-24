package com.example.twitter.kafka;

import com.example.twitter.event.handlers.EventHandler;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class ConsumerThread  implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerThread.class);

    private KafkaConsumer kafkaConsumer;

    private String topicName;

    private EventHandler eventHandler;

    public ConsumerThread(KafkaConsumer kafkaConsumer, String topicName, EventHandler eventHandler) {
        this.kafkaConsumer = kafkaConsumer;
        this.topicName = topicName;
        this.eventHandler = eventHandler;
    }

    @Override
    public void run() {
        kafkaConsumer.subscribe(Arrays.asList(topicName));
        try {
            while(true) {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
                for(ConsumerRecord<String, String> record : records) {
                    String message = record.value();
                    if (eventHandler.validEvent(message)) {
                        eventHandler.onEvent(message);
                        logger.info("topic: " + topicName + "\n message: " + message);
                    } else {
                        logger.info("Invalid event on topic: " + topicName + "\n message: " + message);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            kafkaConsumer.close();
        }
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public void setKafkaConsumer(KafkaConsumer kafkaConsumer) {
        this.kafkaConsumer = kafkaConsumer;
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public KafkaConsumer getKafkaConsumer() {
        return kafkaConsumer;
    }
}
