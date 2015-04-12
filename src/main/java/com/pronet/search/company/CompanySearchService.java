package com.pronet.search.company;

import com.pronet.search.users.UserListing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CompanySearchService {
    private final StringRedisTemplate redisTemplate;
    private String tags="tags:company:".intern();
    private String companyHashSchemaKey ="company:".intern();
    @Autowired
    CompanySearchService(StringRedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public CompanyListings fetchListings(String query, int skip, int limit) {
        final String[] split = query.split(" ");
        if(split!=null && split.length>0){
            final List<String> words = Arrays.asList(split);
            List<String> distinctCompanyTagsToBeSearched = words.stream().map(w -> tags + "*" + w.toLowerCase() + "*").distinct().collect(Collectors.toList());
            final List<Set<String>> collect = distinctCompanyTagsToBeSearched.stream().map(c -> redisTemplate.keys(c)).collect(Collectors.toList());
            final Set<String> tagsMatchingtheSearchTerm = collect.stream().flatMap(set -> set.stream()).distinct().collect(Collectors.toSet());

            final CompanyListings companyListings = new CompanyListings();
            for(String tag:tagsMatchingtheSearchTerm){
                Long totalTerms = redisTemplate.opsForZSet().zCard(tag);
                final Set<String> companyIdsMatchingTheSearch = redisTemplate.opsForZSet().range(tag, skip, skip + limit);
                if(companyIdsMatchingTheSearch!=null && !companyIdsMatchingTheSearch.isEmpty()){
                    List<Map<Object, Object>> foundCompanyEntries = companyIdsMatchingTheSearch.stream().map(companyId -> companyHashSchemaKey + companyId).map(companySearchKey -> redisTemplate.opsForHash().entries(companySearchKey)).collect(Collectors.toList());
                    companyListings.add(foundCompanyEntries.stream().map(user -> CompanyListing.instance(user)).collect(Collectors.toList()));
                    companyListings.addToTotalTerms(totalTerms);
                }
            }
            return companyListings;
        }
        return null;
    }
}
