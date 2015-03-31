package com.pronet.company;

/**
 * Created by parin on 3/29/15.
 */
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.sun.javafx.beans.IDProperty;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;


@DynamoDBTable(tableName="CompanyDetails")
public class CompanyDetails {

    private String ID;
    private String name;
    private String logo;
    private String url;
    private String overview;

    public CompanyDetails(){}

    @DynamoDBHashKey(attributeName="ID")
    public String getID() {
        return ID;
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

    public void setID(String ID) {
        this.ID = ID;
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
