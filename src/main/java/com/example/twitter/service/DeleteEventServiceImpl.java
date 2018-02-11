package com.example.twitter.service;

import com.example.twitter.dao.DeleteEventDao;
import com.example.twitter.model.DeleteEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeleteEventServiceImpl implements DeleteEventService {

    private DeleteEventDao eventDao;

    @Override
    public void addDeleteEvent(DeleteEvent deleteEvent) {
        eventDao.addDeleteEvent(deleteEvent);
    }

    @Override
    public void updateDeleteEvent(DeleteEvent deleteEvent) {
        eventDao.updateDeleteEvent(deleteEvent);
    }

    @Override
    public void deleteDeleteEvent(DeleteEvent deleteEvent) {
        eventDao.deleteDeleteEvent(deleteEvent);
    }

    @Override
    public DeleteEvent getDeleteEvent(long tweetId) {
        return eventDao.getDeleteEvent(tweetId);
    }

    @Override
    public List<DeleteEvent> getAllDeleteEvents() {
        return eventDao.getAllDeleteEvents();
    }

    @Autowired
    public void setEventDao(DeleteEventDao eventDao) {
        this.eventDao = eventDao;
    }
}
