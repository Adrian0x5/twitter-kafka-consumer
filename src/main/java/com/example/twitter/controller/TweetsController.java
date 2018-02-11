package com.example.twitter.controller;

import com.example.twitter.operations.TweetsOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController("/")
public class TweetsController {

    private static final Logger logger = LoggerFactory.getLogger(TweetsController.class);

    @Autowired
    private TweetsOperations tweetsOperations;

    @GetMapping("getDeletedTweets")
    public ResponseEntity<List<String>> getDeletedTweets() {
        return new ResponseEntity<>(tweetsOperations.getDeletedTweetsPrint(), HttpStatus.OK);
    }

    @GetMapping("getDeletedTweetsById")
    public ResponseEntity<Tweet> getTweetsById(@RequestParam("tweetId") Long tweetId) {
        return new ResponseEntity<>(tweetsOperations.getTweetById(tweetId), HttpStatus.OK);
    }

    @GetMapping("getDeletedTweetsReversOrder")
    public ResponseEntity<List<Tweet>> getDeletedTweetsReversOrder() {
        return new ResponseEntity<>(tweetsOperations.getDeletedTweetsReverseOrder(), HttpStatus.OK);
    }

    @GetMapping("getNrTweetsPerUser")
    public ResponseEntity<Map<String, Integer>> getNrUserTweets() {
        return new ResponseEntity<>(tweetsOperations.getNumberOfTweetsPerUser(), HttpStatus.OK);
    }

    @GetMapping("getAllTweets")
    public ResponseEntity<List<String>> getAllTweets() {
        return new ResponseEntity<>(tweetsOperations.getAllTweetsPrint(), HttpStatus.OK);
    }

    @GetMapping("getTweetsByWords")
    public ResponseEntity<List<String>> getTweetsByWord(@RequestParam(value="contains") String word) {
        return new ResponseEntity<>(tweetsOperations.getTweetsThatContainsUandT(word), HttpStatus.OK);
    }
}
