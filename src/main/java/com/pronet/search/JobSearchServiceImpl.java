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

    private String jobTags = "tags:jobs".intern();
    private String jobsSchema = "jobs:".intern();
    private String setKey = "0".intern();

    @Override
    public JobListings fetchJobListings(String term,int skipResults,int limitResults ) {
        if(term!=null||!term.isEmpty()){
            List<String> terms = Arrays.asList(term.toLowerCase().trim().split(" "));
            String searchTerm = terms.stream().map(t -> t).collect(Collectors.joining("_"));
            final String setName = jobTags + ":" + searchTerm;
            Long totalTerms = redisTemplate.opsForZSet().zCard(setName);
            Set<String> jobIds = redisTemplate.opsForZSet().range(setName,skipResults,skipResults+limitResults);

            if(jobIds!=null && !jobIds.isEmpty()){
                List<Map<Object, Object>> foundJobEntries = jobIds.stream().map(jobId -> jobsSchema + jobId).map(jobSearchKey -> redisTemplate.opsForHash().entries(jobSearchKey)).collect(Collectors.toList());
                final List<JobListing> jobListings = foundJobEntries.parallelStream().map(job -> JobListing.instance(job)).collect(Collectors.toList());
                return new JobListings(jobListings,totalTerms);
            }
        }
        return null;
    }
}
