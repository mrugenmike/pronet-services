package com.pronet.search;

import java.util.List;

public interface JobSearchService {
    List<JobListing> fetchJobListings(String term);
}
