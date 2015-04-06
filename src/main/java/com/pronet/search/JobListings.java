package com.pronet.search;

import java.util.List;

public class JobListings {
    public List<JobListing> getListings() {
        return listings;
    }

    public Long getTotalTerms() {
        return totalTerms;
    }

    private final List<JobListing> listings;
    private final Long totalTerms;

    public JobListings(List<JobListing> jobListings, Long totalTerms) {
        this.listings = jobListings;
        this.totalTerms = totalTerms;
    }
}
