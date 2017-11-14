package com.example.twitter.config;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.example.twitter")
public class CouchbaseConfig {

    @Value("${couchbase.cluster}")
    private String cluster;

    @Value("${couchbase.twitter.bucket}")
    private String twitterBucket;

    @Value("${couchbase.delete-event.bucket}")
    private String deleteEventBucket;

    @Bean
    public Cluster cluster() {
        return CouchbaseCluster.create(cluster);
    }

    @Bean
    @Qualifier("create-tweet-bucket")
    public Bucket getTwitterBucket() {
        return cluster().openBucket(twitterBucket);
    }

    @Bean
    @Qualifier("delete-event-bucket")
    public Bucket getDeleteEventBucket() {
        return cluster().openBucket(deleteEventBucket);
    }

}
