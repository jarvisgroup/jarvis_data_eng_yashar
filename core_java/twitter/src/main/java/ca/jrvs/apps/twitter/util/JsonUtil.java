package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.dao.model.Tweet;

import java.io.IOException;

public class JsonUtil {
    public static boolean toPrettyJson(Tweet postTweet) {
        return true;
    }

    public static Tweet toObjectFromJson(String jsonStr, Class<Tweet> tweetClass) throws IOException {
        Tweet tweet = null;
        return tweet;
    }
}
