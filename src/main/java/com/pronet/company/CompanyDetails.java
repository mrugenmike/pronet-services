package com.pronet.company;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;


@DynamoDBTable(tableName="CompanyProfile")
public class CompanyDetails {

    private String id;
    private String user_name;
    private String logo;
    private String url;
    private String overview;

    public CompanyDetails(){}

    @DynamoDBHashKey(attributeName="id")
    public String getId() {
        return id;
    }

    @DynamoDBAttribute(attributeName="user_name")
    public String getUser_name() {
        return user_name;
    }

    @DynamoDBAttribute(attributeName="logo")
    public String getLogo() {
        return logo;
    }

    @DynamoDBAttribute(attributeName="url")
    public String getUrl() {
        return url;
    }

    @DynamoDBAttribute(attributeName="overview")
    public String getOverview() {
        return overview;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }


}
