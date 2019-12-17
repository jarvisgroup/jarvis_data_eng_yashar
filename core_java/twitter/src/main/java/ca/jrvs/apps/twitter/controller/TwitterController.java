package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.util.TweetUtil;
import org.codehaus.groovy.util.StringUtil;

import java.util.List;

public class TwitterController implements Controller{

    private static final String COORD_SEP = ":";
    private static final String COMMA = ",";

    private Service service;

    public TwitterController(Service service) {
        this.service = service;
    }

    /**
     * Parse user argument and post a tweet by calling service classes
     *
     * @param args
     * @return a posted tweet
     * @throws IllegalArgumentException if args are invalid
     */
    @Override
    public Tweet postTweet(String[] args) {
        // pre-condition
        if(args.length != 3){
            throw new IllegalArgumentException(
                    "USAGE: TwitterCLIApp post \"text\" \"latitude:longitude\"");
        }
        // parse argument
        String text = args[1];
        String coordinate = args[2];
        String[] coorArray = coordinate.split(COORD_SEP);
        // validate coordinates and twitter text
        if(coorArray.length != 2 || text.isEmpty() == true ){
            throw new IllegalArgumentException(

                    "Invalid format\nUSAGE: TwitterCLIApp post \"text\" \"latitude:longitude\"");
        }
        Double lat = null;
        Double lon = null;
        try{
            lat = Double.parseDouble(coorArray[0]);
            lon = Double.parseDouble(coorArray[1]);
        }catch (Exception e ){
            throw new IllegalArgumentException("Invalid location format",e);
        }

        // build tweet using argument and call service
        Tweet postTweet = TweetUtil.buildTweet(text,lon,lat);
        return service.postTweet(postTweet);
    }

    /**
     * Parse user argument and search a tweet by calling service classes
     *
     * @param args
     * @return a tweet
     * @throws IllegalArgumentException if args are invalid
     */
    @Override
    public Tweet showTweet(String[] args) {
        // pre-condition
        if(args.length != 3){
            throw new IllegalArgumentException(
                    "USAGE: TwitterCLIApp show \"id\" \"options..\"");
        }
        // parse arguments
        String id = args[1];
        String fields = args[2];
        String[] fieldArray = fields.split(COMMA);
        // ids and fields will be validated in service.showTweet
        return service.showTweet(id,fieldArray);
    }

    @Override
    public List<Tweet> deleteTweet(String[] args) {
        // pre-condition
        if(args.length != 3){
            throw new IllegalArgumentException(
                    "USAGE: TwitterCLIApp delete \"ids\" \"options..\"");
        }
        //parse arguments
        String ids = args[1];
        String[] idArray = ids.split(COMMA);
        return service.deleteTweets(idArray);
    }
}
