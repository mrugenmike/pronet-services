package com.pronet.search.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserSearchService {
    @Autowired
    StringRedisTemplate redisTemplate;

    private String tags="users:".intern();

    public UserListings findUser(String query, int skip, int limit) {

        final String[] split = query.split(" ");
        if(split!=null && split.length>0){
            final List<String> words = Arrays.asList(split);
            List<String> combinations = words.stream().map(w -> tags+"*" + w.toLowerCase() + "*").distinct().collect(Collectors.toList());
            final List<Set<String>> collect = combinations.stream().map(c -> redisTemplate.keys(c)).collect(Collectors.toList());
            final Set<String> distinctUserKeys = collect.stream().flatMap(set -> set.stream()).distinct().collect(Collectors.toSet());
            final List<UserListing> userListings = distinctUserKeys.stream().map(key -> UserListing.instance(redisTemplate.opsForHash().getOperations().boundHashOps(key).entries())).collect(Collectors.toList());
            return new UserListings(userListings,distinctUserKeys.size());
        }
        return null;
    }
}
