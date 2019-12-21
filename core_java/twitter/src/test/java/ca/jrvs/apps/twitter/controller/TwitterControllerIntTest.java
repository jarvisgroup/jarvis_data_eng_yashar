package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.HttpHelper;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.TwitterHttpHelper;
import ca.jrvs.apps.twitter.dao.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.TweetUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TwitterControllerIntTest {

    private Controller controller;
    private TwitterService twitterService;
    private TwitterDao dao;
    private Tweet tweet;
    private Tweet tweet2;
    @Before
    public void setupandPostTweet() {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");
        System.out.println(consumerKey + "|" + consumerSecret + "|" + accessToken + "|" + tokenSecret);
        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        // pass dependency
        this.dao = new TwitterDao(httpHelper);
        this.twitterService = new TwitterService(dao);
        this.controller = new TwitterController(twitterService);

        //post tweet
        String text = "testing1Controller"+ System.currentTimeMillis();
        Double lat = 1d;
        Double lon = -1d;
        Tweet postTweet = TweetUtil.buildTweet(text, lon, lat);
        String[] user_input = {"post", postTweet.getText(),"-1d:1d"};
        tweet = controller.postTweet(user_input);
        String text2 = "testing2Controller" + System.currentTimeMillis();
        Double lat2 = 1d;
        Double lon2 = -1d;
        Tweet postTweet2 = TweetUtil.buildTweet(text2, lon2, lat2);
        String[] user_input2 = {"post", postTweet2.getText(),"-1d:1d"};
        tweet2 = controller.postTweet(user_input);

        assertEquals(text, tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2, tweet.getCoordinates().getCoordinates().size());
        assertEquals(lon, tweet.getCoordinates().getCoordinates().get(0));
        assertEquals(lat, tweet.getCoordinates().getCoordinates().get(1));
    }

    @Test
    public void showTweet() {
        String[] user_input = {"show", tweet.getId_str(),"created_at"};
        Tweet showTestTweet = controller.showTweet(user_input);
        assertEquals(tweet.getText(), showTestTweet.getText());
        assertEquals(tweet.getId_str(), showTestTweet.getId_str());
    }

    @After
    public void deleteTweet() {
        String deleted_ids = tweet.getId_str() + "," + tweet2.getId_str();
        String[] user_input = {"delete", deleted_ids};
        List<Tweet> deletedTweets = controller.deleteTweet(user_input);
        assertEquals(deletedTweets.get(0).getText(), tweet.getText());
        assertEquals(deletedTweets.get(1).getText(), tweet2.getText());
    }
}