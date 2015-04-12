package com.pronet.search.company;

import java.util.Map;

import static com.pronet.search.company.CompanyFields.COMAPANYLOGO;
import static com.pronet.search.company.CompanyFields.COMPANYID;
import static com.pronet.search.company.CompanyFields.COMPANYNAME;

public class CompanyListing {
    String id;
    String logo;
    String companyName;
    String description;

    public CompanyListing(String id, String companyLogo, String companyName) {
        this.id = id;
        this.logo = companyLogo;
        this.companyName = companyName;
    }

    public static CompanyListing instance(Map<Object,Object> company){
        final String id = company.get(COMPANYID).toString();
        final String companyLogo = company.get(COMAPANYLOGO).toString();
        final String companyName = company.get(COMPANYNAME).toString();
        return new CompanyListing(id,companyLogo,companyName);
    }

}
