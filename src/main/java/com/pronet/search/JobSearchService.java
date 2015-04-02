package com.pronet.search;

public interface JobSearchService {
    JobListings fetchJobListings(String term,int skipResults,int limitResults);
}
