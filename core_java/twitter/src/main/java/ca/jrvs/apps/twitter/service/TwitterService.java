package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.model.Tweet;

import java.util.List;

public class TwitterService implements Service {

    private CrdDao dao;
    private static final int MAX_LENGTH = 140;
    public TwitterService(CrdDao dao) {
        this.dao = dao;
    }

    /*
     * validate and post tweet
     * @pre-condition text <= 140 char
     * and lon/lat is not out of range
     */
    @Override
    public Tweet postTweet(Tweet tweet) {
        double lon = tweet.getCoordinates().getCoordinates().get(0);
        double lat = tweet.getCoordinates().getCoordinates().get(1);
        // check for text length
        if(tweet.getText().length() > MAX_LENGTH){
            throw new IllegalArgumentException("tweet text exceed limit!!");
        }
        // check for lon and lat range
        if(lon > 180 || lon < -180 || lat < -90 || lat > 90){
            throw new IllegalArgumentException("longitude/latitude out of range");
        }
        //create tweet via dao
        return (Tweet) dao.create(tweet);
    }

    @Override
    public Tweet showTweet(String id, String[] fields) {

        return null;
    }

    @Override
    public List<Tweet> deleteTweets(String[] ids) {
        return null;
    }
}
