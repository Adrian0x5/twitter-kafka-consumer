package com.example.twitter.operations;

import com.example.twitter.service.StreamDeleteEventService;
import com.example.twitter.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class TweetsOperations {

    private TweetService tweetService;

    private StreamDeleteEventService deleteEventService;

    public List<Tweet> getAllDeletedTweets() {
        return deleteEventService.getAllStreamDeleteEvents()
                .stream()
                .map(a -> tweetService.getTweet(a.getTweetId()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public Map<String, Integer> getNumberOfTweetsPerUser() {
        Map<String, Integer> map = new HashMap<>();
        tweetService.getAllTweets().forEach(t -> map.put(t.getFromUser(), incrementNrUserTweet(map, t.getFromUser())));
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, HashMap::new));
    }

    private Integer incrementNrUserTweet(Map<String, Integer> map, String user) {
        if (map.keySet().contains(user)) return map.get(user) + 1;
        else return 1;
    }

    @Autowired
    public void setTweetService(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @Autowired
    public void setDeleteEventService(StreamDeleteEventService deleteEventService) {
        this.deleteEventService = deleteEventService;
    }
}
