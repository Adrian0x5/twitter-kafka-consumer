package com.example.twitter.controller;

import com.example.twitter.operations.TweetsOperations;
import com.example.twitter.service.TweetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController("/")
public class DeletedTweetsController {

    private static final Logger logger = LoggerFactory.getLogger(DeletedTweetsController.class);

    @Autowired
    private TweetService tweetService;

    @Autowired
    private TweetsOperations tweetsOperations;

    @GetMapping("getDeletedTweets")
    public ResponseEntity<List<String>> getDeletedTweets() {
        return new ResponseEntity<>(tweetsOperations.getAllDeletedTweets()
                .stream()
                .sorted(Comparator.comparing(c -> c.getUser().getName()))
                .map(this::getUserAndText)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("getNrTweetsPerUser")
    public ResponseEntity<Map<String, Integer>> getNrUserTweets() {
        return new ResponseEntity<>(tweetsOperations.getNumberOfTweetsPerUser(), HttpStatus.OK);
    }

    @GetMapping("getAllTweets")
    public ResponseEntity<List<String>> getAllTweets() {
        return new ResponseEntity<>(tweetService.getAllTweets()
                .stream()
                .map(this::getUserAndText)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    private String getUserAndText(Tweet tweet) {
        return "User: " + tweet.getUser().getName() + " text: " + tweet.getText();
    }

}
