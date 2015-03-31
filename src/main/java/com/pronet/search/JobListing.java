package com.pronet.search;

import org.springframework.cache.annotation.Cacheable;

import java.io.Serializable;
import java.util.Map;

public class JobListing implements Serializable{
    private String positionTitle;
    private String jobId;
    private String companyName;
    private String companyLogoUrl;
    private String positionLocation;

    public String getDescription() {
        return description;
    }

    private String description;

    public JobListing(String jobId,String positionTitle, String companyName, String companyLogoUrl, String positionLocation,String description) {
        this.jobId = jobId;
        this.positionTitle = positionTitle;
        this.companyName = companyName;
        this.companyLogoUrl = companyLogoUrl;
        this.positionLocation = positionLocation;
        this.description = description;
    }

    public String getPositionTitle() {
        return positionTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyLogoUrl() {
        return companyLogoUrl;
    }

    public String getPositionLocation() {
        return positionLocation;
    }
    public String getJobId() {
        return jobId;
    }

    public static JobListing instance(Map<Object, Object> job) {
        String position_title = (String) job.get("positionTitle");
        String company_name= (String) job.get("companyName");
        String company_logo= (String) job.get("companyLogo");
        String location= (String) job.get("positionLocation");
        String id= (String) job.get("id");
        String description= (String) job.get("description");
        return new JobListing(id,position_title,company_name,company_logo,location,description);
    }
}
