package com.example.twitter.model;

import java.util.Date;
import java.util.Map;

public class DeleteEvent {

    private long tweetId;

    private long userId;

    private Date deletedAt;

    private Map<String, Object> extraData;

    public DeleteEvent() {
    }

    public DeleteEvent(long tweetId, long userId, Date deletedAt, Map<String, Object> extraData) {
        this.tweetId = tweetId;
        this.userId = userId;
        this.deletedAt = deletedAt;
        this.extraData = extraData;
    }

    public long getTweetId() {
        return tweetId;
    }

    public void setTweetId(long tweetId) {
        this.tweetId = tweetId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Map<String, Object> getExtraData() {
        return extraData;
    }

    public void setExtraData(Map<String, Object> extraData) {
        this.extraData = extraData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeleteEvent that = (DeleteEvent) o;

        if (tweetId != that.tweetId) return false;
        if (userId != that.userId) return false;
        if (deletedAt != null ? !deletedAt.equals(that.deletedAt) : that.deletedAt != null) return false;
        return extraData != null ? extraData.equals(that.extraData) : that.extraData == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (tweetId ^ (tweetId >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (deletedAt != null ? deletedAt.hashCode() : 0);
        result = 31 * result + (extraData != null ? extraData.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DeleteEvent{" +
                "tweetId=" + tweetId +
                ", userId=" + userId +
                ", deletedAt=" + deletedAt +
                ", extraData=" + extraData +
                '}';
    }
}
