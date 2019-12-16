package ca.jrvs.apps.twitter.dao.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Entities {
    private Hashtag hashtag;
    private UserMention userMention;

    public Hashtag getHashtags() {
        return hashtag;
    }

    public void setHashtags(Hashtag hashtag) {
        this.hashtag = hashtag;
    }

    public UserMention getUserMention() {
        return userMention;
    }

    public void setUserMention(UserMention userMention) {
        this.userMention = userMention;
    }
}
