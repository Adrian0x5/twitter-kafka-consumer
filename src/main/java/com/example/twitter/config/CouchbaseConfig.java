package com.example.twitter.config;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.example.twitter.dao.TweetDao;
import com.example.twitter.dao.TweetDaoImpl;
import com.example.twitter.service.TweetServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.example.twitter")
public class CouchbaseConfig {

    @Bean
    public Cluster cluster() {
        return CouchbaseCluster.create("localhost");
    }

    @Bean
    @Qualifier("create-tweet-bucket")
    public Bucket getTwitterBucket() {
        return cluster().openBucket("twitter");
    }

    @Bean
    @Qualifier("delete-event-bucket")
    public Bucket getDeleteEventBucket() {
        return cluster().openBucket("delete-event");
    }

}
