package com.example.twitter.controller;

import com.example.twitter.service.StreamDeleteEventService;
import com.example.twitter.service.TweetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController("/")
public class DeletedTweetsController {

    private static final Logger logger = LoggerFactory.getLogger(DeletedTweetsController.class);

    @Autowired
    private TweetService tweetService;

    @Autowired
    private StreamDeleteEventService deleteEventService;

    @GetMapping("getDeletedTweets")
    public ResponseEntity<List<String>> getDeletedTweets() {
        List<String> tweets = deleteEventService.getAllStreamDeleteEvents()
                .stream()
                .map(a -> tweetService.getTweet(a.getTweetId()))
                .filter(Objects::nonNull)
                .map(this::getUserAndText)
                .collect(Collectors.toList());
        return new ResponseEntity<>(tweets, HttpStatus.OK);
    }

    private String getUserAndText(Tweet tweet) {
        return "User: " + tweet.getUser().getName() + " text: " + tweet.getText();
    }

}
