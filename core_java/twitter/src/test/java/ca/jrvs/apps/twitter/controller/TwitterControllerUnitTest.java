package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

public class TwitterControllerUnitTest {
    @Mock
    TwitterService twitterService;

    @InjectMocks
    TwitterController twitterController;

    @Test
    public void postTweet() {
        String[] user_input = {"post", "testingController", "1d:-1d"};
        Mockito.when(twitterService.postTweet(any())).thenReturn(new Tweet());
        Tweet postTestTweet = twitterController.postTweet(user_input);
        assertNotNull(postTestTweet);
        assertNotNull(postTestTweet.getText());
        assertNotNull(postTestTweet.getCoordinates());
        assertNotNull(postTestTweet.getId_str());
    }

    @Test
    public void showTweet() {
        String[] user_input = {"show", "1097607853932564480", "text,created_at"};
        Mockito.when(twitterService.showTweet(any(),any())).thenReturn(new Tweet());
        Tweet showTestTweet = twitterController.showTweet(user_input);
        assertNotNull(showTestTweet);
        assertNotNull(showTestTweet.getText());
        assertNotNull(showTestTweet.getCoordinates());
        assertNotNull(showTestTweet.getId_str());
    }

    @Test
    public void deleteTweet() {
        String[] user_input = {"delete", "1097607853932564480,1097607853932564220"};
        Mockito.when(twitterService.deleteTweets(any())).thenReturn(new ArrayList<>());
        List<Tweet> deletedTweet = twitterController.deleteTweet(user_input);
        for (Tweet tweet : deletedTweet){
            assertNotNull(tweet);
            assertNotNull(tweet.getText());
            assertNotNull(tweet.getCoordinates());
            assertNotNull(tweet.getId_str());

        }
    }
}