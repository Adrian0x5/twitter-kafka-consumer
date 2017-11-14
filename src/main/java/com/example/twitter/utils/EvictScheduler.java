package com.example.twitter.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EvictScheduler {

    private static final Logger logger = LoggerFactory.getLogger(EvictScheduler.class);

    @Autowired
    private LocalCacheEvict localCacheEvict;

    @Scheduled(fixedDelay = 30000)
    public void clearCaches() {
        logger.info("clearing Caches");
        localCacheEvict.evictAllTweets();
    }
}
