package ca.jrvs.apps.twitter.dao.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Entities {
    private List<Hashtag> hashtags;
    private List<UserMention> user_mentions;

    public List<Hashtag> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<Hashtag> hashtag) {
        this.hashtags = hashtag;
    }

    public List<UserMention> getUserMention() {
        return user_mentions;
    }

    public void setUserMention(List<UserMention> user_mentions) {
        this.user_mentions = user_mentions;
    }
}
