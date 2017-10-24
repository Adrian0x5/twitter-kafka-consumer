package com.example.twitter.event.handlers;

public interface EventHandler {

    void onEvent(String message);

    boolean validEvent(String message);
}
