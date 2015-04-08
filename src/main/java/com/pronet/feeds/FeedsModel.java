package com.pronet.feeds;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

public class FeedsModel {

    //feed_id is on auto_increment

    //@JsonProperty
    private String userID;
    @JsonProperty
    private String feed_title;
    @JsonProperty @NotBlank
    private String feed_description;
    //@JsonProperty
    private String feed_role;
    private String user_name;
    private String img;

    public FeedsModel(){}


    public FeedsModel(@JsonProperty String userID, @JsonProperty String feed_title,
                      @JsonProperty String feed_description, @JsonProperty String feed_role,
                      @JsonProperty String user_name, @JsonProperty String img) {
        this.userID = userID;
        this.feed_title = feed_title;
        this.feed_description = feed_description;
        this.feed_role = feed_role;
        this.user_name = user_name;
        this.img = img;
    }


    public String getUserID() {
        return userID;
    }

    public String getFeed_title() {
        return feed_title;
    }

    public String getFeed_description() {
        return feed_description;
    }

    public String getFeed_role() {
        return feed_role;
    }


    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setFeed_role(String feed_role) {
        this.feed_role = feed_role;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}
