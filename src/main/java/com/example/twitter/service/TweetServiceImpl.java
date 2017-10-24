package com.example.twitter.service;

import com.example.twitter.dao.TweetDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetServiceImpl implements TweetService {

    private TweetDao tweetDao;

    @Override
    public void addTweet(Tweet tweet) {
        tweetDao.addTweet(tweet);
    }

    @Override
    public void deleteTweet(Tweet tweet) {
        tweetDao.deleteTweet(tweet);
    }

    @Override
    public void updateTweet(Tweet tweet) {
        tweetDao.updateTweet(tweet);
    }

    @Override
    public Tweet getTweet(long id) {
        return tweetDao.getTweet(id);
    }

    @Override
    public List<Tweet> getAllTweets() {
        return tweetDao.getAllTweets();
    }

    @Autowired
    public void setTweetDao(TweetDao tweetDao) {
        this.tweetDao = tweetDao;
    }
}
