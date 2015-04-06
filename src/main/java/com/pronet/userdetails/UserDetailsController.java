package com.pronet.userdetails;

import com.pronet.BadRequestException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RestController
@Component("UserDetailsController")
@RequestMapping("/api/v1")
public class UserDetailsController {

    UserDetailsService userDetailsService;

    @Autowired
    public UserDetailsController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @RequestMapping(value = "/userprofile/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void UpdateUserDetails(@PathVariable("id") String id, @Valid @RequestBody UserDetails user, BindingResult result) throws EmptyResultDataAccessException {
        if (result.hasErrors()) {
            throw new BadRequestException("Error in Request Body");
        }
        userDetailsService.UpdateUserDetailsAt(id, user);
    }


    @RequestMapping(value = "/userprofile/{id}/{currentID}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JSONObject getUserDetails(@PathVariable("id") String id, @PathVariable("currentID") String currentID) throws Exception {
        return userDetailsService.getUserDetailsAt(id, currentID);
    }
}

