package com.pronet.search.users;

import java.io.Serializable;
import java.util.Map;
public class UserListing implements Serializable {

    public String getId() {
        return id;
    }

    public String getLogo() {
        return logo;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    private final String id;
    private final String logo;
    private final String name;
    private final String region;

    public UserListing(String id, String logo, String name, String region) {
        this.id = id;
        this.logo = logo;
        this.name = name;
        this.region = region;
    }

    static UserListing instance(Map<Object, Object> entries){
        String id =(String)entries.get(UserFields.USERID.toString());
        String logo =(String)entries.get(UserFields.USERLOGO.toString());
        String name =(String)entries.get(UserFields.NAME.toString());
        String region =(String)entries.get(UserFields.REGION.toString());
        return new UserListing(id,logo,name,region);
    }
}
