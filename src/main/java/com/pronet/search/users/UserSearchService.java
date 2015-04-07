package com.pronet.search.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserSearchService {
    @Autowired
    StringRedisTemplate redisTemplate;



    public UserListings findUser(String query, int skip, int limit) {
        //redisTemplate.keys()
        return null;
    }
}
