package com.example.twitter.event.handlers;

import com.example.twitter.service.TweetService;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;

@Component
public class CreatedTweetsHandler implements EventHandler {

    private TweetService tweetService;

    private Gson gson;

    @Override
    public void onEvent(String message) {
        tweetService.addTweet(gson.fromJson(message, Tweet.class));
    }

    @Override
    public boolean validEvent(String message) {
        try {
            gson.fromJson(message, Tweet.class);
            return true;
        } catch (JsonSyntaxException e) {
            return false;
        }
    }

    @Autowired
    public void setTweetService(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @Autowired
    public void setGson(Gson gson) {
        this.gson = gson;
    }
}