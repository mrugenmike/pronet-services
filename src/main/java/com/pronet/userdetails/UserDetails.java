package com.pronet.userdetails;

/**
 * Created by varuna on 3/30/15.
 */

import java.util.Set;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="UserDetails")
public class UserDetails {
    private String id;
    private String name;
    private String imgURL;
    private String role;
    private String region;
    private String education;
    private String workExperience;
    private String summary;
    private String educationDetails;
    private String skills;
    private String certifications;
    private String lastseen;

    @DynamoDBHashKey(attributeName="id")
    public String getID() {
        return id;
    }
    public void setID(String ID) {
        this.id = ID;
    }

    @DynamoDBAttribute(attributeName="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName="imgURL")
    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    @DynamoDBAttribute(attributeName="role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @DynamoDBAttribute(attributeName="region")
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @DynamoDBAttribute(attributeName="education")
    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    @DynamoDBAttribute(attributeName="workExperience")
    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

    @DynamoDBAttribute(attributeName="summary")
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @DynamoDBAttribute(attributeName="educationDetails")
    public String getEducationDetails() {
        return educationDetails;
    }

    public void setEducationDetails(String educationDetails) {
        this.educationDetails = educationDetails;
    }

    @DynamoDBAttribute(attributeName="skills")
    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    @DynamoDBAttribute(attributeName="certifications")
    public String getCertifications() {
        return certifications;
    }

    public void setCertifications(String certifications) {
        this.certifications = certifications;
    }

    @DynamoDBAttribute(attributeName="lastseen")
    public String getLastseen() {
        return lastseen;
    }

    public void setLastseen(String lastseen) {
        this.lastseen = lastseen;
    }


    public String toString(){
        return "ID : " + id + " Name:" + name + " URL:"+ imgURL + " role:"+ role +
                " Region:" + region + " Education: " + education + " workex: " + workExperience +
                " Summary:"+summary + " Ed Details:"+educationDetails + " Skills:"+skills +
                " cerification:"+certifications + " lastseen:"+lastseen;

    }
}
