package com.example.twitter.service;

import com.example.twitter.dao.StreamDeleteEventDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.StreamDeleteEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StreamDeleteEventServiceImpl implements StreamDeleteEventService {

    private StreamDeleteEventDao streamDeleteEventDao;

    @Override
    public void addTweetDeleteEvent(StreamDeleteEvent streamDeleteEvent) {
        streamDeleteEventDao.addStreamDeleteEvent(streamDeleteEvent);
    }

    @Override
    public void updateTweetDeleteEvent(StreamDeleteEvent streamDeleteEvent) {
        streamDeleteEventDao.updateStreamDeleteEvent(streamDeleteEvent);
    }

    @Override
    public void deleteTweetDeleteEvent(StreamDeleteEvent streamDeleteEvent) {
        streamDeleteEventDao.deleteStreamDeleteEvent(streamDeleteEvent);
    }

    @Override
    public StreamDeleteEvent getStreamDeleteEvent(long tweetId) {
        return streamDeleteEventDao.getStreamDeleteEvent(tweetId);
    }

    @Override
    public List<StreamDeleteEvent> getAllStreamDeleteEvents() {
        return streamDeleteEventDao.getAllStreamDeleteEvents();
    }

    @Autowired
    public void setStreamDeleteEventDao(StreamDeleteEventDao streamDeleteEventDao) {
        this.streamDeleteEventDao = streamDeleteEventDao;
    }
}
