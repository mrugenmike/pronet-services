package com.pronet.posts;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by parin on 3/31/15.
 */


public class PostsModel {



    @JsonProperty
    String title;

    @JsonProperty
    String providerId;

    @JsonProperty
    String description;

    @JsonProperty
    String role;

    PostsModel() {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
