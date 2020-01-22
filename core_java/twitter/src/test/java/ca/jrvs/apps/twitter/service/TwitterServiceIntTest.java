package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.HttpHelper;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.TwitterHttpHelper;
import ca.jrvs.apps.twitter.dao.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TwitterServiceIntTest {

    private TwitterDao dao;
    private TwitterService twitterService;
    private Tweet tweet;
    private Tweet tweet2;

    @Before
    public void setupandpostTweet() {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");
        System.out.println(consumerKey + "|" + consumerSecret + "|" + accessToken + "|" + tokenSecret);
        //setup dependency
        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        // pass dependency
        this.dao = new TwitterDao(httpHelper);
        this.twitterService = new TwitterService(dao);
        //create post
        String text = "testing1Service"+ System.currentTimeMillis();
        Double lat = 1d;
        Double lon = -1d;
        Tweet postTweet = TweetUtil.buildTweet(text, lon, lat);
        // second post for testing deleteTweets
        String text2 = "testing2service" + System.currentTimeMillis();
        Double lat2 = 1d;
        Double lon2 = -1d;
        Tweet postTweet2 = TweetUtil.buildTweet(text2, lon2, lat2);
        //post tweet
        tweet = twitterService.postTweet(postTweet);
        tweet2 = twitterService.postTweet(postTweet2);
        assertEquals(text, tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2, tweet.getCoordinates().getCoordinates().size());
        assertEquals(lon, tweet.getCoordinates().getCoordinates().get(0));
        assertEquals(lat, tweet.getCoordinates().getCoordinates().get(1));
    }

    @Test
    public void showTweet() throws Exception{
        // adding properties for testing purposes
        String[] fields = {"text","created_at"};
        Tweet showTestTweet = twitterService.showTweet(tweet.getId_str(),fields);
        assertEquals(tweet.getText(), showTestTweet.getText());
    }

    @After
    public void deleteTweets() throws Exception{
        String[] deleted_ids = {tweet.getId_str(), tweet2.getId_str()};
        List<Tweet> deletedTweets = twitterService.deleteTweets(deleted_ids);
        assertEquals(deletedTweets.get(0).getText(), tweet.getText());
        assertEquals(deletedTweets.get(1).getText(), tweet2.getText());
    }
}