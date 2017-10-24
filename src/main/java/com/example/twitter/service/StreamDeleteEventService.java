package com.example.twitter.service;

import org.springframework.social.twitter.api.StreamDeleteEvent;

import java.util.List;

public interface StreamDeleteEventService {

    void addTweetDeleteEvent(StreamDeleteEvent streamDeleteEvent);

    void updateTweetDeleteEvent(StreamDeleteEvent streamDeleteEvent);

    void deleteTweetDeleteEvent(StreamDeleteEvent streamDeleteEvent);

    StreamDeleteEvent getStreamDeleteEvent(long tweetId);

    List<StreamDeleteEvent> getAllStreamDeleteEvents();
}
