package com.example.twitter.utils;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
public class LocalCacheEvict {

    @CacheEvict(cacheNames = "getAllTweets", allEntries = true)
    public void evictAllTweets() {

    }
}
