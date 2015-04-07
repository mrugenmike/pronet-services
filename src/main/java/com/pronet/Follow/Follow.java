package com.pronet.follow;


import org.codehaus.jackson.annotate.JsonProperty;

public class Follow {

    @JsonProperty
    private String id;

    @JsonProperty
    private String followerId;

    @JsonProperty
    private String followerName;

    @JsonProperty
    private String followerRole;

    @JsonProperty
    private String followerURL;

    public String getFollowerId() {
        return followerId;
    }

    public void setFollowerId(String followerId) {
        this.followerId = followerId;
    }

    public String getFollowerName() {
        return followerName;
    }

    public void setFollowerName(String followerName) {
        this.followerName = followerName;
    }

    public String getFollowerRole() {
        return followerRole;
    }

    public void setFollowerRole(String followerRole) {
        this.followerRole = followerRole;
    }

    public String getFollowerURL() {
        return followerURL;
    }

    public void setFollowerURL(String followerURL) {
        this.followerURL = followerURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Follow(){
        System.out.println("here in follow");
    }


    public Follow(@JsonProperty String id , @JsonProperty String followerId, @JsonProperty String followerName,@JsonProperty String followerRole,@JsonProperty String followerURL) {
        System.out.println("property consructor");
        this.followerId = followerId;
        this.followerName = followerName;
        this.followerRole = followerRole;
        this.followerURL = followerURL;
    }
}
