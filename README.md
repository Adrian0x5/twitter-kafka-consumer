# twitter-kafka-consumer

This is a spring boot application that consumes kafka stream produce by twitter-kafka-producer and stores
the Tweets and StreamDeleteEvents in couchbase.

It also has a rest controller with some operations like 
/getNrTweetsPerUser, 
/getAllTweets, 
etc.