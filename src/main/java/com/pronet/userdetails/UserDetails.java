package com.pronet.userdetails;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="UserDetails")
public class UserDetails {
    private String id;
    private String user_name;
    private String img;
    private String role;
    private String region;
    private String education;
    private String workex;
    private String summary;
    private String educationDetails;
    private String skills;
    private String certifications;
    private String lastseen;

    @DynamoDBHashKey(attributeName="id")
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName="user_name")
    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @DynamoDBAttribute(attributeName="img")
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    @DynamoDBAttribute(attributeName="workex")
    public String getWorkex() {
        return workex;
    }

    public void setWorkex(String workex) {
        this.workex = workex;
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
        return "id : " + id + " Name:" + user_name + " URL:"+ img + " role:"+ role +
                " Region:" + region + " Education: " + education + " workex: " + workex +
                " Summary:"+summary + " Ed Details:"+educationDetails + " Skills:"+skills +
                " cerification:"+certifications + " lastseen:"+lastseen;

    }
}
