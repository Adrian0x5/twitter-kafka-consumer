package com.example.twitter;

import com.example.twitter.kafka.MessageConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class TwitterConsumerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(TwitterConsumerApplication.class, args);
		MessageConsumer messageConsumer = context.getBean(MessageConsumer.class);
		messageConsumer.recieveMessage();
	}
}
