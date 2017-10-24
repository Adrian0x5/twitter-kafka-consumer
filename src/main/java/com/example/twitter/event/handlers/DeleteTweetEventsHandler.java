package com.example.twitter.event.handlers;


import com.example.twitter.service.StreamDeleteEventService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.StreamDeleteEvent;
import org.springframework.stereotype.Component;

@Component
public class DeleteTweetEventsHandler implements EventHandler {

    private StreamDeleteEventService streamDeleteEventService;

    private Gson gson;

    @Override
    public void onEvent(String message) {
        streamDeleteEventService.addTweetDeleteEvent(gson.fromJson(message, StreamDeleteEvent.class));
    }

    @Override
    public boolean validEvent(String message) {
        try {
            gson.fromJson(message, StreamDeleteEvent.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Autowired
    public void setStreamDeleteEventService(StreamDeleteEventService streamDeleteEventService) {
        this.streamDeleteEventService = streamDeleteEventService;
    }

    @Autowired
    public void setGson(Gson gson) {
        this.gson = gson;
    }
}
