package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.model.Tweet;

import java.util.ArrayList;
import java.util.List;

public class TwitterService implements Service {

    private CrdDao dao;
    private static final int MAX_LENGTH = 140;
    public TwitterService(CrdDao dao) {
        this.dao = dao;
    }

    /**
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

    /**
     * Search a tweet by ID
     *
     * @param id tweet id
     * @param fields set fields not in the list to null
     * @return Tweet object which is returned by the Twitter API
     *
     * @throws IllegalArgumentException if id or fields param is invalid
     */
    @Override
    public Tweet showTweet(String id, String[] fields) {
        //pre-condition, check if id in correct format
        if(!id.matches("[0-9]+")){
            throw new IllegalArgumentException("ID is not in correct format");
        }

        return (Tweet) dao.findById(id);

    }

    /**
     * Delete Tweet(s) by id(s).
     *
     * @param ids tweet IDs which will be deleted
     * @return A list of Tweets
     *
     * @throws IllegalArgumentException if one of the IDs is invalid.
     */
    @Override
    public List<Tweet> deleteTweets(String[] ids) {
        List<Tweet> deletedTweet = new ArrayList<Tweet>();
        for(String id : ids){
            if(!id.matches("[0-9]+")){
                throw new IllegalArgumentException("ID is not in correct format");
            }else{
                deletedTweet.add((Tweet) dao.deleteById(id));
            }
        }
        return deletedTweet;
    }
}
