package com.pronet.company;

/**
 * Created by parin on 3/29/15.
 */
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;


@DynamoDBTable(tableName="CompanyProfile")
public class CompanyProfile {


    private String id;
    private String name;
    private String logo;
    private String url;
    private String overview;

    public CompanyProfile(){}

    @DynamoDBHashKey(attributeName="id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName="name")
    public String getName() {
        return name;
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

    public void setName(String name) {
        this.name = name;
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
