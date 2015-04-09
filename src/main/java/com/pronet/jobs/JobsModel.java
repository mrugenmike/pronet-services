package com.pronet.jobs;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="JobPosting")
public class JobsModel {

    String id;
    String jid;
    String jtitle;
    String description;
    String skills;
    String start_date;
    String ex_date;
    String job_region;
    String job_status;

    public JobsModel(){}


    @DynamoDBHashKey(attributeName="jtitle")
    public String getJtitle() {
        return jtitle;
    }

    public void setJtitle(String jtitle) {
        this.jtitle = jtitle;
    }

    @DynamoDBAttribute(attributeName="id")
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName="description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @DynamoDBAttribute(attributeName="skills")
    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    @DynamoDBAttribute(attributeName="start_date")
    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    @DynamoDBAttribute(attributeName="ex_date")
    public String getEx_date() {
        return ex_date;
    }

    public void setEx_date(String ex_date) {
        this.ex_date = ex_date;
    }

    @DynamoDBAttribute(attributeName="job_region")
    public String getJob_region() {
        return job_region;
    }

    public void setJob_region(String job_region) {
        this.job_region = job_region;
    }
    @DynamoDBAttribute(attributeName="job_status")
    public String getJob_status() {
        return job_status;
    }

    public void setJob_status(String job_status) {
        this.job_status = job_status;
    }

    @DynamoDBAttribute(attributeName="jid")
    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }
}
