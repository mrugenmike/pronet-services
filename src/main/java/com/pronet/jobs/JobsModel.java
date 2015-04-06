package com.pronet.jobs;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="JobPosting")
public class JobsModel {

    String id;
    String jid;
    String jtitle;
    String desc;
    String skills;
    String start_date;
    String ex_date;
    String job_region;
    String status;



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

    @DynamoDBAttribute(attributeName="job_region")
    public String getJob_region() {
        return job_region;
    }

    public void setJob_region(String job_region) {
        this.job_region = job_region;
    }
    @DynamoDBAttribute(attributeName="status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @DynamoDBAttribute(attributeName="jid")
    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }
}
