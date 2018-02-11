package com.example.twitter.event.handlers;


import com.example.twitter.model.DeleteEvent;
import com.example.twitter.service.DeleteEventService;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.StreamDeleteEvent;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class DeleteTweetEventsHandler implements EventHandler {

    private static final Logger logger = LoggerFactory.getLogger(DeleteTweetEventsHandler.class);

    private DeleteEventService deleteEventService;

    private Gson gson;

    @Override
    public void onEvent(String message) {
        deleteEventService.addDeleteEvent(fromStreamDeleteEvent(message));
    }

    private DeleteEvent fromStreamDeleteEvent(String message) {
        StreamDeleteEvent streamDeleteEvent = gson.fromJson(message, StreamDeleteEvent.class);
        DeleteEvent deleteEvent = new DeleteEvent();
        deleteEvent.setTweetId(streamDeleteEvent.getTweetId());
        deleteEvent.setUserId(streamDeleteEvent.getUserId());
        deleteEvent.setExtraData(streamDeleteEvent.getExtraData());
        deleteEvent.setDeletedAt(Date.from(Instant.now()));
        return deleteEvent;
    }

    @Override
    public boolean validEvent(String message) {
        try {
            gson.fromJson(message, StreamDeleteEvent.class);
            return true;
        } catch (JsonSyntaxException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Autowired
    public void setDeleteEventService(DeleteEventService deleteEventService) {
        this.deleteEventService = deleteEventService;
    }

    @Autowired
    public void setGson(Gson gson) {
        this.gson = gson;
    }
}
