package com.example.twitter.dao;

import org.springframework.social.twitter.api.StreamDeleteEvent;

import java.util.List;

public interface StreamDeleteEventDao {

    void addStreamDeleteEvent(StreamDeleteEvent streamDeleteEvent);

    void updateStreamDeleteEvent(StreamDeleteEvent streamDeleteEvent);

    void deleteStreamDeleteEvent(StreamDeleteEvent streamDeleteEvent);

    StreamDeleteEvent getStreamDeleteEvent(long tweetId);

    List<StreamDeleteEvent> getAllStreamDeleteEvents();

}
