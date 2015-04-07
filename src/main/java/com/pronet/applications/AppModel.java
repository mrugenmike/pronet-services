package com.pronet.applications;

import org.codehaus.jackson.annotate.JsonProperty;

public class AppModel {

    String job_app_id;
    @JsonProperty
    String job_id;
    @JsonProperty
    String company_id;
    @JsonProperty
    String user_id;
    @JsonProperty
    String company_name;
    @JsonProperty
    String job_title;
    @JsonProperty
    String app_date;

    public String getJob_app_id() {
        return job_app_id;
    }

    public void setJob_app_id(String job_app_id) {
        this.job_app_id = job_app_id;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getApp_date() {
        return app_date;
    }

    public void setApp_date(String app_date) {
        this.app_date = app_date;
    }

    public AppModel(){}


    public AppModel(@JsonProperty String job_app_id,@JsonProperty String job_id,@JsonProperty String company_id,@JsonProperty String user_id,@JsonProperty String company_name,@JsonProperty String job_title,@JsonProperty String app_date) {
        this.job_app_id = job_app_id;
        this.job_id = job_id;
        this.company_id = company_id;
        this.user_id = user_id;
        this.company_name = company_name;
        this.job_title = job_title;
        this.app_date = app_date;
    }

}

