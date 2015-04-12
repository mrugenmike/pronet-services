package com.pronet.search.company;

import java.util.Map;

import static com.pronet.search.company.CompanyFields.*;

public class CompanyListing {
    public String getId() {
        return id;
    }

    public String getLogo() {
        return logo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getDescription() {
        return description;
    }

    String id;
    String logo;
    String companyName;
    String description;

    public CompanyListing(String id, String companyLogo, String companyName, String description) {
        this.id = id;
        this.logo = companyLogo;
        this.companyName = companyName;
        this.description = description;
    }

    public static CompanyListing instance(Map<Object,Object> company){
        final String id = company.get(COMPANYID.toString()).toString();
        final String companyLogo = company.get(COMAPANYLOGO.toString()).toString();
        final String companyName = company.get(COMPANYNAME.toString()).toString();
        final String description = company.get(COMPANYDESC.toString()).toString();
        return new CompanyListing(id,companyLogo,companyName,description);
    }

}
