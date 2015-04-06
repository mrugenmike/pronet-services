package com.pronet.applications;


import com.pronet.BadRequestException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@Component("AppController")
@RequestMapping("/api/v1")
public class AppController {

    AppService appService;

    @Autowired
    public AppController(AppService appService) {
        this.appService = appService;
    }

    @RequestMapping(value = "/jobapps", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void jobApp(@RequestBody AppModel model,BindingResult result) throws Exception {

        if(result.hasErrors())
        {
            throw new BadRequestException("Request Body Is Missing Required Parameters");

        }

         appService.jobAppAt(model);


    }

    @RequestMapping(value = "/jobapps/{c_id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public JSONObject getAllJobs(@PathVariable("c_id") String c_id) throws Exception {

        return appService.getAllJobsAt(c_id);


    }


}
