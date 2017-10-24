package com.example.twitter.config;

import com.example.twitter.event.handlers.CreatedTweetsHandler;
import com.example.twitter.event.handlers.DeleteTweetEventsHandler;
import com.example.twitter.event.handlers.EventHandler;
import com.example.twitter.kafka.ConsumerThread;
import com.example.twitter.kafka.MessageConsumer;
import com.example.twitter.service.TweetService;
import com.example.twitter.utils.MyDateTypeAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

@Configuration
@Import(CouchbaseConfig.class)
@ComponentScan("com.example.twitter")
public class KafkaConfig {

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String KAFKA_SERVER;
    @Value("${spring.kafka.consumer.key-deserializer}")
    private String KEY_DESERIALIZER;
    @Value("${spring.kafka.consumer.value-deserializer}")
    private String VALUE_DESERIALIZER;
    @Value("${spring.kafka.consumer.group-id}")
    private String GROUP_ID;
    @Value("${spring.kafka.consumer.client-id}")
    private String CLIENT_ID_CONFIG;
    @Value("${kafka.tweets.create.topic}")
    private String createTwitterTopic;
    @Value("${kafka.tweets.delete.topic}")
    private String deleteTweetsTopic;

    @Autowired
    private CreatedTweetsHandler createdTweetsHandler;

    @Autowired
    private DeleteTweetEventsHandler deleteTweetEventsHandler;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }


    private Properties properties() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, KEY_DESERIALIZER);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, VALUE_DESERIALIZER);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        return properties;
    }

    @Bean
    @Scope("prototype")
    public KafkaConsumer kafkaConsumer() {
        return new KafkaConsumer(properties());
    }

    @Bean
    @Scope("prototype")
    public ConsumerThread createConsumerThread(String topic, EventHandler eventHandler) {
        return new ConsumerThread(kafkaConsumer(), topic, eventHandler);
    }

    @Bean
    public List<ConsumerThread> consumersList() {
        List<ConsumerThread> consumerList = new ArrayList<>();
        consumerList.add(createConsumerThread(createTwitterTopic, createdTweetsHandler));
        consumerList.add(createConsumerThread(deleteTweetsTopic, deleteTweetEventsHandler));
        return consumerList;
    }

    @Bean
    public MessageConsumer messageConsumer() {
        MessageConsumer messageConsumer = new MessageConsumer();
        messageConsumer.setConsumerThreadList(consumersList());
        return messageConsumer;
    }

    @Bean
    public Gson gson() {
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Date.class, new MyDateTypeAdapter());
        return gsonBuilder.create();
    }


}
