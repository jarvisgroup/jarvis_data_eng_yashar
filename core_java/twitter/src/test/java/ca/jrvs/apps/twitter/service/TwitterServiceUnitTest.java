package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static net.bytebuddy.matcher.ElementMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {
    @Mock
    CrdDao dao;

    @InjectMocks
    TwitterService service;

    @Test
    public void postTweet(){
        Mockito.when(dao.create(any())).thenReturn(new Tweet());
        Tweet postTestTweet = service.postTweet(TweetUtil.buildTweet("unitTesting",1d,-1d));
        assertNotNull(postTestTweet);
        assertNotNull(postTestTweet.getText());
        assertNotNull(postTestTweet.getCoordinates());
        assertNotNull(postTestTweet.getId_str());
    }

    @Test
    public void showTweet(){
        String[] fields = {"text","created_at"};
        Mockito.when(dao.findById(any())).thenReturn(new Tweet());
        Tweet showTestTweet = service.showTweet("1097607853932564480",fields);
        assertNotNull(showTestTweet);
        assertNotNull(showTestTweet.getText());
        assertNotNull(showTestTweet.getCoordinates());
        assertNotNull(showTestTweet.getId_str());
    }

    @Test
    public void deleteTweet(){
        Tweet deleteTestTweet = new Tweet();
        String[] delete_ids = {"1097607853932564230","1097607853932562380"};
        Mockito.when(dao.deleteById(any())).thenReturn(deleteTestTweet);
        List<Tweet> deletedTweet = service.deleteTweets(delete_ids);
        for(Tweet tweet : deletedTweet){
            assertNotNull(tweet);
            assertNotNull(tweet.getText());
            assertNotNull(tweet.getCoordinates());
            assertNotNull(tweet.getId_str());
        }

    }
}
