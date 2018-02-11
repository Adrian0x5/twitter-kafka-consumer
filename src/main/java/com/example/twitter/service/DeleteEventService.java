package com.example.twitter.service;

import com.example.twitter.model.DeleteEvent;

import java.util.List;

public interface DeleteEventService {

    void addDeleteEvent(DeleteEvent deleteEvent);

    void updateDeleteEvent(DeleteEvent deleteEvent);

    void deleteDeleteEvent(DeleteEvent deleteEvent);

    DeleteEvent getDeleteEvent(long tweetId);

    List<DeleteEvent> getAllDeleteEvents();
}
