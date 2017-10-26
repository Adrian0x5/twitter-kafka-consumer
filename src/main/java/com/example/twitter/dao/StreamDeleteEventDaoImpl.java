package com.example.twitter.dao;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.error.DocumentAlreadyExistsException;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.social.twitter.api.StreamDeleteEvent;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class StreamDeleteEventDaoImpl implements StreamDeleteEventDao {

    private static final Logger logger = LoggerFactory.getLogger(StreamDeleteEventDao.class);
    private static final String SELECT_ALL_DELETE_EVENTS = "Select * from `delete-event`";

    private Bucket bucket;

    private Gson mapper;

    @Override
    public void addStreamDeleteEvent(StreamDeleteEvent streamDeleteEvent) {
        try {
            bucket.insert(JsonDocument.create(String.valueOf(streamDeleteEvent.getTweetId()),
                    JsonObject.fromJson(mapper.toJson(streamDeleteEvent))));
        } catch (DocumentAlreadyExistsException e) {
            logger.warn("delete event with tweetId: " + streamDeleteEvent.getTweetId() + " already exists");
        }

    }

    @Override
    public void updateStreamDeleteEvent(StreamDeleteEvent streamDeleteEvent) {
        bucket.upsert(JsonDocument.create(String.valueOf(streamDeleteEvent.getTweetId()),
                JsonObject.fromJson(mapper.toJson(streamDeleteEvent))));

    }

    @Override
    public void deleteStreamDeleteEvent(StreamDeleteEvent streamDeleteEvent) {
        bucket.remove(String.valueOf(streamDeleteEvent.getTweetId()));
    }

    @Override
    public StreamDeleteEvent getStreamDeleteEvent(long tweetId) {
        return mapper.fromJson(bucket.get(String.valueOf(tweetId)).content().toString(), StreamDeleteEvent.class);
    }

    @Override
    public List<StreamDeleteEvent> getAllStreamDeleteEvents() {
        N1qlQueryResult result = bucket.query(N1qlQuery.simple(SELECT_ALL_DELETE_EVENTS));
        return result.allRows().stream().map(mapQueryResultToStreamDeleteEvent()).collect(Collectors.toList());
    }

    private Function<N1qlQueryRow, StreamDeleteEvent> mapQueryResultToStreamDeleteEvent() {
        return a -> mapper.fromJson(a.value().get("delete-event").toString(), StreamDeleteEvent.class);
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
