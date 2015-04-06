package com.pronet.profile;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

@DynamoDBTable(tableName="CompanyProfile")
public class ProfileImgUpdateModel {

    @DynamoDBHashKey(attributeName="id")
    @JsonProperty @NotBlank
    private String id;

    @DynamoDBAttribute(attributeName="logo")
    @JsonProperty @NotBlank
    private String logo;



    public String getId() {return id;}

    public String getLogo() {return logo;}

    public void setId(String id) {this.id = id;}

    public void setLogo(String logo) {this.logo = logo;}

    public ProfileImgUpdateModel(){}

    public ProfileImgUpdateModel(@JsonProperty String id, @JsonProperty String logo) {
        this.id = id;
        this.logo = logo;
    }
}
