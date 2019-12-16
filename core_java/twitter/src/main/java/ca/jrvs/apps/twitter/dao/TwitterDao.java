package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;


/*
 * Constructs Twitter REST API URIs
 * and make HTTP calls using HttpHelper
 * implements CrdDao interface
 */
public class TwitterDao implements CrdDao<Tweet, String>{

    // URI constants
    private static final String API_BASE_URI = "http://api.twiiter.com";
    private static final String POST_PATH = "/1.1/statuses/update.json";
    private static final String SHOW_PATH = "/1.1/statuses/show.json";
    private static final String DELETE_PATH = "/1.1/statuses/destroy";
    //URI symbols
    private static final String QUERy_SYM = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUAL = "=";

    // Response code
    private static final int HTTP_OK = 200;

    private HttpHelper httpHelper;

    @Autowired
    public  TwitterDao(HttpHelper httpHelper){this.httpHelper= httpHelper;}

    @Override
    public Tweet create(Tweet tweet){
        URI uri;
        try{
            uri = getPostUri(tweet);
        }catch (URISyntaxException | UnsupportedEncodingException e){
            throw new IllegalArgumentException("Invalid tweet input", e);
        }
        // execute Http request
        HttpResponse response = httpHelper.httpPost(uri);
        // validate response and parse response to Tweet object
        return parseResponseBody(response,HTTP_OK);

    }
    // helper function to get the POST uri
    private URI getPostUri(Tweet tweet) throws URISyntaxException,UnsupportedEncodingException {
        URI uri = new URI(API_BASE_URI + POST_PATH + tweet.getId());
        return uri;
    }

    @Override
    public Tweet findById(String s) {
        return null;
    }

    @Override
    public Tweet deleteById(String s) {
        return null;
    }


    public Tweet parseResponseBody(HttpResponse response, Integer statusCode) {
        Tweet tweet = null;
        //check response status
        int status = response.getStatusLine().getStatusCode();
        if(status != statusCode){
            try{
                System.out.println(EntityUtils.toString(response.getEntity()));
            }catch (IOException e){
                System.out.println("Response has no entity");
            }
            throw new RuntimeException("Unexpected HTTP status: " + status);
        }

        //Convert Response Entity to str
        String jsonStr;
        try{
            jsonStr = EntityUtils.toString(response.getEntity());
        }catch (IOException e){
            throw new RuntimeException("Failed to convert entity to String", e);
        }

        //parse Json String to Tweet Object
        try{
            tweet = JsonUtil.toObjectFromJson(jsonStr, Tweet.class);
        }catch (IOException e){
            throw new RuntimeException("Unable to convert JSON str to Object", e);
        }
        return tweet;
    }

}
