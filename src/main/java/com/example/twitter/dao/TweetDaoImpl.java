package com.example.twitter.dao;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.error.DocumentAlreadyExistsException;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class TweetDaoImpl implements TweetDao {

    private static final Logger logger = LoggerFactory.getLogger(TweetDaoImpl.class);
    private static final String SELECT_ALL_TWEETS = "Select * from `twitter`";

    private Bucket bucket;

    private Gson mapper;

    @Override
    public void addTweet(Tweet tweet) {
    try {
        bucket.insert(JsonDocument.create(String.valueOf(tweet.getId()),
                JsonObject.fromJson(mapper.toJson(tweet))));
        } catch (DocumentAlreadyExistsException e) {
            logger.error("tweet with id: " + tweet.getId() + " already exists");
        }
    }

    @Override
    public void deleteTweet(Tweet tweet) {
        bucket.remove(String.valueOf(tweet.getId()));
    }

    @Override
    public void updateTweet(Tweet tweet) {
        bucket.upsert(JsonDocument.create(String.valueOf(tweet.getId()),
                JsonObject.fromJson(mapper.toJson(tweet))));
    }

    @Override
    public Tweet getTweet(long id) {
        JsonDocument doc = bucket.get(String.valueOf(id));
        if (doc != null)
            return mapper.fromJson(doc.content().toString(), Tweet.class);
        else
            return null;
    }

    @Override
    public List<Tweet> getAllTweets() {
        N1qlQueryResult result = bucket.query(N1qlQuery.simple(SELECT_ALL_TWEETS));
        return result.allRows().stream().map(mapQueryResultToTweet()).collect(Collectors.toList());
    }

    private Function<N1qlQueryRow, Tweet> mapQueryResultToTweet() {
        return a -> mapper.fromJson(a.value().get("twitter").toString(), Tweet.class);
    }

    @Autowired
    @Qualifier("create-tweet-bucket")
    public void setBucket(Bucket bucket) {
        this.bucket = bucket;
    }

    @Autowired
    public void setMapper(Gson mapper) {
        this.mapper = mapper;
    }
}
