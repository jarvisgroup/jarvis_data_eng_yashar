package ca.jrvs.apps.twitter.dao.model;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

// only properties with non-null values are to be included
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
     "create_at",
     "id",
     "id_str",
     "text",
     "entities",
     "coordinates",
     "retweet_count",
     "favorite_count",
     "favorited",
     "retweeted"
})

public class Tweet {
    // setting Json Property to variables
    @JsonProperty("create_at")
    private  String create_at;
    @JsonProperty("id")
    private  long id;
    @JsonProperty("id_str")
    private  String id_str;
    @JsonProperty("text")
    private  String text;
    @JsonProperty("entities")
    private Entities entities;
    @JsonProperty("coordinates")
    private Coordinates coordinates;
    @JsonProperty("retweet_count")
    private int retweet_count;
    @JsonProperty("favorite_count")
    private int favorite_count;
    @JsonProperty("favorited")
    private boolean favorited;
    @JsonProperty("retweeted")
    private boolean retweeted;



    // getter and setter for all Json Properties
    @JsonProperty("create_at")
    public String getCreate_at() {
        return create_at;
    }

    @JsonProperty("create_at")
    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    @JsonProperty("id")
    public long getId() {
        return id;
    }
    @JsonProperty("id")
    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("id_str")
    public String getId_str() {
        return id_str;
    }

    @JsonProperty("id_str")
    public void setId_str(String id_str) {
        this.id_str = id_str;
    }

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty("entities")
    public Entities getEntities() {
        return entities;
    }

    @JsonProperty("entities")
    public void setEntities(Entities entities) {
        this.entities = entities;
    }

    @JsonProperty("coordinates")
    public Coordinates getCoordinates() {
        return coordinates;
    }

    @JsonProperty("coordinates")
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @JsonProperty("retweet_count")
    public int getRetweet_count() {
        return retweet_count;
    }

    @JsonProperty("retweet_count")
    public void setRetweet_count(int retweet_count) {
        this.retweet_count = retweet_count;
    }

    @JsonProperty("favorite_count")
    public int getFavorite_count() {
        return favorite_count;
    }

    @JsonProperty("favorite_count")
    public void setFavorite_count(int favorite_count) {
        this.favorite_count = favorite_count;
    }

    @JsonProperty("favorited")
    public boolean isFavorited() {
        return favorited;
    }

    @JsonProperty("favorited")
    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    @JsonProperty("retweeted")
    public boolean isRetweeted() {
        return retweeted;
    }

    @JsonProperty("retweeted")
    public void setRetweeted(boolean retweeted) {
        this.retweeted = retweeted;
    }


}

