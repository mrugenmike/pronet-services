package com.pronet.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class JobSearchServiceImpl implements JobSearchService {

    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    public JobSearchServiceImpl(RedisTemplate<String,String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private String jobTags = "tags:jobs";
    private String jobsSchema = "jobs:";

    @Override
    public List<JobListing> fetchJobListings(String term) {
        if(term!=null||!term.isEmpty()){
            List<String> terms = Arrays.asList(term.toLowerCase().trim().split(" "));
            String searchTerm = terms.stream().map(t -> t).collect(Collectors.joining("_"));
            Set<String> jobIds = redisTemplate.opsForSet().members(jobTags+":"+searchTerm);
            if(jobIds!=null && !jobIds.isEmpty()){
                List<Map<Object, Object>> foundJobEntries = jobIds.stream().map(jobId -> jobsSchema + jobId).map(jobSearchKey -> redisTemplate.opsForHash().entries(jobSearchKey)).collect(Collectors.toList());
                return foundJobEntries.parallelStream().map(job -> JobListing.instance(job)).collect(Collectors.toList());
            }
        }
        return null;
    }
}
