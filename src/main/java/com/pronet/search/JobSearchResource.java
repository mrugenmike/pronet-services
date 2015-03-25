package com.pronet.search;

import com.pronet.exceptions.NoContentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class JobSearchResource {

    private final JobSearchService jobSearchService;
    @Autowired
    public JobSearchResource(JobSearchService jobSearchService) {
        this.jobSearchService = jobSearchService;
    }

    @RequestMapping("jobs")
    List<JobListing> fetchJobs(@RequestParam("query") String term) throws NoContentException {
        List<JobListing> jobListings = jobSearchService.fetchJobListings(term);
        if(jobListings==null||jobListings.isEmpty()){
            throw new NoContentException("Job Listings Not Found");
        }
        return jobListings;
    }
}
