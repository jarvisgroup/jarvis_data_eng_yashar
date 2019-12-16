package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import ca.jrvs.apps.twitter.util.TweetUtil;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TwitterDaoIntTest {
    private TwitterDao dao;

    @Before
    public void setup(){
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");
        System.out.println(consumerKey +"|" + consumerSecret + "|" + accessToken + "|" + tokenSecret);
        //setup dependency
        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey,consumerSecret,accessToken,tokenSecret);
        // pass dependency
        this.dao = new TwitterDao(httpHelper);
    }

    @Test
    public void create() throws Exception{
        String hashTag = "#abc";
        String text = "@someone sometext" + hashTag + " " + System.currentTimeMillis();
        Double lat = 1d;
        Double lon = -1d;
        Tweet postTweet = TweetUtil.buildTweet(text, lon, lat);
        System.out.println(JsonUtil.toPrettyJson(postTweet));

        Tweet tweet = dao.create(postTweet);
        assertEquals(text, tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2,tweet.getCoordinates().getCoordinates().size());
        assertEquals(lon,tweet.getCoordinates().getCoordinates().get(0));
        assertEquals(lat,tweet.getCoordinates().getCoordinates().get(1));

        assertTrue(hashTag.contains(tweet.getEntities().getHashtags().getText()));



    }
}
