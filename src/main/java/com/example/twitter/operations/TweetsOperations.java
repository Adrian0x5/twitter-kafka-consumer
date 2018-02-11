package com.example.twitter.operations;

import com.example.twitter.model.DeleteEvent;
import com.example.twitter.service.DeleteEventService;
import com.example.twitter.service.TweetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TweetsOperations {

    private static final Logger logger = LoggerFactory.getLogger(TweetsOperations.class);

    private TweetService tweetService;

    private DeleteEventService deleteEventService;

    public List<Tweet> getAllDeletedTweets() {
        return deleteEventService.getAllDeleteEvents()
                .stream()
                .map(a -> tweetService.getTweet(a.getTweetId()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<String> getDeletedTweetsPrint() {
        return deleteEventService.getAllDeleteEvents()
                .stream()
                .filter(a -> Objects.nonNull(tweetService.getTweet(a.getTweetId())))
                .sorted(Comparator.comparing(DeleteEvent::getDeletedAt).reversed())
                .map(a -> getUserTextDateForDelete(tweetService.getTweet(a.getTweetId()), a))
                .collect(Collectors.toList());
    }

    public Map<String, Integer> getNumberOfTweetsPerUser() {
        Map<String, Integer> map = new LinkedHashMap<>();
        tweetService.getAllTweets().forEach(t -> map.put(t.getFromUser(), incrementNrUserTweet(map, t.getFromUser())));
        return map.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public Tweet getTweetById(Long tweetId) {
        return tweetService.getTweet(tweetId);
    }

    public List<Tweet> getDeletedTweetsReverseOrder() {
        return getAllDeletedTweets()
                .stream()
                .sorted(Comparator.comparing(Tweet::getCreatedAt))
                .collect(Collectors.toList());
    }

    private Integer incrementNrUserTweet(Map<String, Integer> map, String user) {
        if (map.keySet().contains(user)) return map.get(user) + 1;
        else return 1;
    }

    private List<Tweet> getAllTweets() {
        return tweetService.getAllTweets();
    }

    public List<String> getAllTweetsPrint() {
        return getAllTweets().stream()
                .map(this::getUserAndText)
                .collect(Collectors.toList());
    }

    private List<Tweet> getTweetsThatContains(String word) {
        return getAllTweets()
                .stream()
                .filter(t -> t.getText().contains(word))
                .collect(Collectors.toList());
    }

    public List<String> getTweetsThatContainsUandT(String word) {
        return getTweetsThatContains(word).stream()
                .sorted(Comparator.comparing(c -> c.getUser().getName()))
                .map(this::getUserAndText)
                .collect(Collectors.toList());
    }

    private String getUserAndText(Tweet tweet) {
        return "User: " + tweet.getUser().getName() + " text: " + tweet.getText();
    }

    private String getUserTextDateForDelete(Tweet tweet, DeleteEvent deleteEvent) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss z");
        return "User: " + tweet.getUser().getName() + " text: " + tweet.getText() + " deletedAt: " + simpleDateFormat.format(deleteEvent.getDeletedAt());
    }

    @Autowired
    public void setTweetService(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @Autowired
    public void setDeleteEventService(DeleteEventService deleteEventService) {
        this.deleteEventService = deleteEventService;
    }
}
