package com.pronet.jobs;

/**
 * Created by parin on 4/2/15.
 */

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.sun.javafx.beans.IDProperty;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

@DynamoDBTable(tableName="CompanyProfile")
public class JobsModel {

    String id;
    String jtitle;
    String desc;
    String skills;
    String start_date;
    String ex_date;
    String region;
    String status;

    @DynamoDBHashKey(attributeName="id")
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName="jtitle")
    public String getJtitle() {
        return jtitle;
    }

    public void setJtitle(String jtitle) {
        this.jtitle = jtitle;
    }

    @DynamoDBAttribute(attributeName="desc")
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @DynamoDBAttribute(attributeName="skills")
    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    @DynamoDBAttribute(attributeName="sdate")
    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    @DynamoDBAttribute(attributeName="exdate")

    public String getEx_date() {
        return ex_date;
    }

    public void setEx_date(String ex_date) {
        this.ex_date = ex_date;
    }

    @DynamoDBAttribute(attributeName="region")
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
    @DynamoDBAttribute(attributeName="status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
