package com.example.twitter.dao;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.error.DocumentAlreadyExistsException;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import com.example.twitter.model.DeleteEvent;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class DeleteEventDaoImpl implements DeleteEventDao {

    private static final Logger logger = LoggerFactory.getLogger(DeleteEventDaoImpl.class);
    private static final String SELECT_ALL_DELETE_EVENTS = "Select * from `delete-events`";

    private Bucket bucket;

    private Gson mapper;

    @Override
    public void addDeleteEvent(DeleteEvent deleteEvent) {
        try {
            bucket.insert(JsonDocument.create(String.valueOf(deleteEvent.getTweetId()),
                    JsonObject.fromJson(mapper.toJson(deleteEvent))));
        } catch (DocumentAlreadyExistsException e) {
            logger.error("delete event with tweetId: " + deleteEvent.getTweetId() + " already exists");
        }

    }

    @Override
    public void updateDeleteEvent(DeleteEvent deleteEvent) {
        bucket.upsert(JsonDocument.create(String.valueOf(deleteEvent.getTweetId()),
                JsonObject.fromJson(mapper.toJson(deleteEvent))));
    }

    @Override
    public void deleteDeleteEvent(DeleteEvent deleteEvent) {
        bucket.remove(String.valueOf(deleteEvent.getTweetId()));
    }

    @Override
    public DeleteEvent getDeleteEvent(long tweetId) {
        return mapper.fromJson(bucket.get(String.valueOf(tweetId)).content().toString(), DeleteEvent.class);
    }

    @Override
    public List<DeleteEvent> getAllDeleteEvents() {
        N1qlQueryResult result = bucket.query(N1qlQuery.simple(SELECT_ALL_DELETE_EVENTS));
        return result.allRows().stream().map(mapQueryResultToDeleteEvent()).collect(Collectors.toList());
    }

    private Function<N1qlQueryRow, DeleteEvent> mapQueryResultToDeleteEvent() {
        return a -> mapper.fromJson(a.value().get("delete-events").toString(), DeleteEvent.class);
    }

    @Autowired
    @Qualifier("delete-event-bucket")
    public void setBucket(Bucket bucket) {
        this.bucket = bucket;
    }

    @Autowired
    public void setMapper(Gson mapper) {
        this.mapper = mapper;
    }
}
