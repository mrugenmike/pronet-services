package com.pronet.search.jobs;

public interface JobSearchService {
    public String jobTags = "tags:jobs".intern();
    public String jobsSchema = "jobs:".intern();
    public String setKey = "0".intern();
    JobListings fetchJobListings(String term, int skipResults, int limitResults);
}
