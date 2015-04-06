package com.pronet.search;

import com.pronet.exceptions.NoContentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class JobSearchResource {

    private final JobSearchService jobSearchService;
    @Autowired
    public JobSearchResource(JobSearchService jobSearchService) {
        this.jobSearchService = jobSearchService;
    }

    @RequestMapping("jobs/listings")
    JobListings fetchJobListings(@RequestParam("query") String term,@RequestParam(defaultValue = "0") int skip,@RequestParam(defaultValue ="5") int limit) throws NoContentException {
        JobListings jobListings = jobSearchService.fetchJobListings(term, skip, limit);
        if(jobListings==null){
            throw new NoContentException("Job Listings Not Found");
        }
        return jobListings;
    }
}
