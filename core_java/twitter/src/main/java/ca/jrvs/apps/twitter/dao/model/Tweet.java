package ca.jrvs.apps.twitter.dao.model;


public class Tweet {
    public String text;
    public String coordinate;

    public Tweet create (Tweet tweet){
        return tweet;
    }

    public String getText(){
        return text;

    }

    public String getCoordinates() {
        return coordinate;
    }

    public Object getEntities() {
    }
}

