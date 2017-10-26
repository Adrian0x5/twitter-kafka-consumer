package com.example.twitter;

import com.example.twitter.kafka.MessageConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TwitterApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(TwitterApplication.class, args);
		MessageConsumer messageConsumer = context.getBean(MessageConsumer.class);
		messageConsumer.recieveMessage();
	}
}
