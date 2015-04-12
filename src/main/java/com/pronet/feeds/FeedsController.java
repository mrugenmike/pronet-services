package com.pronet.feeds;


import com.pronet.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Component("FeedsController")
public class FeedsController{
    FeedsService fService;

    @Autowired
    public FeedsController(FeedsService fService) {
        this.fService = fService;
    }

    //POST User Feeds
    @RequestMapping(value = "/userfeeds/{id}",method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    void newUserFeed(@PathVariable(value="id") String id,@Valid @RequestBody FeedsModel feed, BindingResult result) throws Exception
    {
        if(result.hasErrors())
        {
            throw new BadRequestException("Request Body Is Missing Required Parameters");
        }
        fService.newUserFeedAt(id,feed);
    }

    //Get User Feeds
    @RequestMapping(value = "/usersfeeds/{id}",method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List getUserFeed(@PathVariable(value="id") String id) throws Exception
    {
        return fService.getUserFeedAt(id);
    }

    //POST Company Feed
    @RequestMapping(value = "/company/feeds/{id}",method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    void newCompanyFeed(@PathVariable(value="id") String id,@Valid @RequestBody FeedsModel feed, BindingResult result) throws Exception
    {
        if(result.hasErrors())
        {
            throw new BadRequestException("Request Body Is Missing Required Parameters");

        }
         fService.newCompanyFeedAt(id, feed);
    }


    //Get Company Feeds
    @RequestMapping(value = "/company/feeds/{id}",method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List getCompanyFeed(@PathVariable(value="id") String id) throws Exception
    {
        return fService.getCompanyFeedAt(id);
    }

    @RequestMapping(value = "/feeds/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteFeed(@PathVariable("id") int id) throws Exception{

        fService.deleteFeedAt(id);
    }
}
