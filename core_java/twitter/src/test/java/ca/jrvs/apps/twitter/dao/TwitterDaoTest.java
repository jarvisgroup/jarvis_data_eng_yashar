package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import ca.jrvs.apps.twitter.util.TweetUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class TwitterDaoTest {
    private TwitterDao dao;
    private Tweet testTweet;
//    private double testLon = testTweet.getCoordinates().getCoordinates().get(0);
//    private double testLat = testTweet.getCoordinates().getCoordinates().get(1);

    @Before
    public void setup() {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");
        System.out.println(consumerKey + "|" + consumerSecret + "|" + accessToken + "|" + tokenSecret);
        //setup dependency
        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        // pass dependency
        this.dao = new TwitterDao(httpHelper);
    }

    @Test
    public void create() throws Exception {
        String text = "testingmytweet"+ System.currentTimeMillis();
        Double lat = 12.23;
        Double lon = -32.33;
        Tweet postTweet = TweetUtil.buildTweet(text, lon, lat);
        System.out.println(JsonUtil.toPrettyJson(postTweet));
        Tweet tweet = dao.create(postTweet);
        assertEquals(text, tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2, tweet.getCoordinates().getCoordinates().size());
        assertEquals(lon, tweet.getCoordinates().getCoordinates().get(0));
        assertEquals(lat, tweet.getCoordinates().getCoordinates().get(1));

    }

    @Test
    public void findById() {
        String id_str = testTweet.getId_str();
        Tweet tweet = dao.findById(id_str);
        assertEquals(testTweet.getText(), tweet.getText());
        assertEquals(id_str, tweet.getText());

    }

    @After
    public void deleteById() {
        String id_str = testTweet.getId_str();
        Tweet tweet = dao.deleteById(id_str);
        assertEquals(testTweet.getText(), tweet.getText());
        assertEquals(id_str, tweet.getId_str());

    }
}
