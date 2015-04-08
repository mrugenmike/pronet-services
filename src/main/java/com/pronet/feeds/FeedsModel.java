package com.pronet.feeds;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

public class FeedsModel {

    //feed_id is on auto_increment

    //@JsonProperty
    private String userID;

    private String feedID;
    @JsonProperty
    private String feed_title;

    @JsonProperty @NotBlank
    private String feed_description;
    //@JsonProperty
    private String feed_role;
    private String feed_username;
    private String feed_userimage;

    public FeedsModel(){}


    public FeedsModel(@JsonProperty String userID, @JsonProperty String feed_title,
                         @JsonProperty String feed_description, @JsonProperty String feed_role , @JsonProperty String feed_username,
                         @JsonProperty String feed_userimage) {
        this.userID = userID;
        this.feed_title = feed_title;
        this.feed_description = feed_description;
        this.feed_role = feed_role;
        this.feed_username = feed_username;
        this.feed_userimage = feed_userimage;
    }



    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFeed_title() {
        return feed_title;
    }

    public void setFeed_title(String feed_title) {
        this.feed_title = feed_title;
    }

    public String getFeed_description() {
        return feed_description;
    }

    public void setFeed_description(String feed_description) {
        this.feed_description = feed_description;
    }

    public String getFeed_role() {
        return feed_role;
    }

    public void setFeed_role(String feed_role) {
        this.feed_role = feed_role;
    }

    public String getFeed_username() {
        return feed_username;
    }

    public void setFeed_username(String feed_username) {
        this.feed_username = feed_username;
    }

    public String getFeed_userimage() {
        return feed_userimage;
    }

    public void setFeed_userimage(String feed_userimage) {
        this.feed_userimage = feed_userimage;
    }

    public String getFeedID() {
        return feedID;
    }

    public void setFeedID(String feedID) {
        this.feedID = feedID;
    }
}
