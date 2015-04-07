package com.pronet.search.jobs;

public interface JobSearchService {
    JobListings fetchJobListings(String term, int skipResults, int limitResults);
}
