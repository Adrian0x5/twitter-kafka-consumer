package com.example.twitter.dao;

import org.springframework.social.twitter.api.Tweet;

import java.util.List;

public interface TweetDao {

    void addTweet(Tweet tweet);

    void deleteTweet(Tweet tweet);

    void updateTweet(Tweet tweet);

    Tweet getTweet(long id);

    List<Tweet> getAllTweets();
}
