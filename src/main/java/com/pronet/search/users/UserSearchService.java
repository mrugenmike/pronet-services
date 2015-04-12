package com.pronet.search.users;

import com.pronet.search.jobs.JobListing;
import com.pronet.search.jobs.JobListings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserSearchService {
    @Autowired
    StringRedisTemplate redisTemplate;

    private String tags="tags:users:".intern();
    private String userHashSchemaKey ="users:".intern();

    public UserListings findUser(String query, int skip, int limit) {

        final String[] split = query.split(" ");
        if(split!=null && split.length>0){
            final List<String> words = Arrays.asList(split);
            List<String> distinctUserTagsToBeSearched = words.stream().map(w -> tags + "*" + w.toLowerCase() + "*").distinct().collect(Collectors.toList());
            final List<Set<String>> collect = distinctUserTagsToBeSearched.stream().map(c -> redisTemplate.keys(c)).collect(Collectors.toList());
            final Set<String> tagsMatchingtheSearchTerm = collect.stream().flatMap(set -> set.stream()).distinct().collect(Collectors.toSet());

            final UserListings userListings = new UserListings();
            for(String tag:tagsMatchingtheSearchTerm){
                Long totalTerms = redisTemplate.opsForZSet().zCard(tag);
                final Set<String> userIdsMatchingTheSearch = redisTemplate.opsForZSet().range(tag, skip, skip + limit);
                if(userIdsMatchingTheSearch!=null && !userIdsMatchingTheSearch.isEmpty()){
                    List<Map<Object, Object>> foundUserEntries = userIdsMatchingTheSearch.stream().map(userId -> userHashSchemaKey + userId).map(userSearchKey -> redisTemplate.opsForHash().entries(userSearchKey)).collect(Collectors.toList());
                    userListings.add(foundUserEntries.stream().map(user -> UserListing.instance(user)).collect(Collectors.toList()));
                    userListings.addToTotalTerms(totalTerms);
                }
            }
            return userListings;
        }
        return null;
    }
    
    
}
