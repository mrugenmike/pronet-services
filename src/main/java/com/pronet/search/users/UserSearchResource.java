package com.pronet.search.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserSearchResource {

    @Autowired
    UserSearchService userSearchService;

    @RequestMapping(value = {"/users"})
    @ResponseStatus(HttpStatus.OK)
    public UserListings fetchUsers(@RequestParam("query") String query,@RequestParam(defaultValue = "0")int skip, @RequestParam(defaultValue = "3")int limit){
        return userSearchService.findUser(query,skip,limit);
    }
}
