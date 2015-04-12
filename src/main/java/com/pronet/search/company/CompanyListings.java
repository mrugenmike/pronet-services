package com.pronet.search.company;

import java.util.ArrayList;
import java.util.List;

public class CompanyListings {
    public List<CompanyListing> getCompanyListings() {
        return companyListings;
    }

    List<CompanyListing> companyListings = new ArrayList<CompanyListing>();
    int totalEntries;
    public void add(List<CompanyListing> listings) {
        if(listings!=null){
            companyListings.addAll(listings);
        }
    }

    public void addToTotalTerms(Long totalTerms) {
        totalEntries+=totalTerms;
    }
}
