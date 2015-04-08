package com.pronet.feeds;


import com.pronet.BadRequestException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController

@RequestMapping("/api/v1")
public class FeedsController{


    FeedsService fService;

    @Autowired
    public FeedsController(FeedsService fService) {
        this.fService = fService;
    }

    //POST User Feeds
    @RequestMapping(value = "/feeds/users/{id}",method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    FeedsModel newUserFeed(@PathVariable(value="id") String id,@Valid @RequestBody FeedsModel feed, BindingResult result) throws Exception
    {
        if(result.hasErrors())
        {
            throw new BadRequestException("Request Body Is Missing Required Parameters");

        }
        return fService.newUserFeedAt(id,feed);
    }
    //POST user feeds
    @RequestMapping(value = "/feeds/company/{id}",method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    FeedsModel newCompanyFeed(@PathVariable(value="id") String id,@Valid @RequestBody FeedsModel feed, BindingResult result) throws Exception
    {
        if(result.hasErrors())
        {
            throw new BadRequestException("Request Body Is Missing Required Parameters");

        }
        return fService.newCompanyFeedAt(id, feed);
    }

    //Get User Feeds
    @RequestMapping(value = "/feeds/users/{id}",method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    JSONObject getUserFeed(@PathVariable(value="id") String id) throws Exception
    {
        return fService.getUserFeedAt(id);
    }

    //Get Company Feeds
    @RequestMapping(value = "/feeds/company/{id}",method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    JSONObject getCompanyFeed(@PathVariable(value="id") String id) throws Exception
    {
        return fService.getCompanyFeedAt(id);
    }

    //delete feeds
    @RequestMapping(value = "/feeds/{id}",method= RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    void deleteFeed(@PathVariable(value="id") String id) throws Exception
    {
        fService.deleteFeedAt(id);
    }
}
