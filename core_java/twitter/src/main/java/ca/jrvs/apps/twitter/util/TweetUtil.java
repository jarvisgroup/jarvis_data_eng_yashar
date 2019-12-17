package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.dao.model.Coordinates;
import ca.jrvs.apps.twitter.dao.model.Tweet;

import java.lang.reflect.Array;
import java.util.Arrays;

public class TweetUtil {
    public static Tweet buildTweet(String text, Double lon, Double lat) {
        Tweet tweet = new Tweet();
        Coordinates coordinates = new Coordinates();
        coordinates.setCoordinates(Arrays.asList(lon,lat));
        tweet.setCoordinates(coordinates);
        tweet.setText(text);
        return tweet;
    }
}
