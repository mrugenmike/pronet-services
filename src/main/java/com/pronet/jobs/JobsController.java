package com.pronet.jobs;

import com.pronet.exceptions.BadRequestException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Component("JobsController")
@RequestMapping("/api/v1")
public class JobsController {

    JobsService jobsService;

    @Autowired
    public JobsController(JobsService jobsService) {
        this.jobsService = jobsService;
    }



    @RequestMapping(value = "/jobs", method = RequestMethod.POST)
    public void saveJobPost(@Valid @RequestBody JobsModel model, BindingResult result) {

        if(result.hasErrors())
        {
            throw new BadRequestException("Request Body Is Missing Required Parameters");

        }

        model.setJob_status("ACTIVE");

        jobsService.saveJobPostAt(model);


    }

    @RequestMapping(value = "/jobs/{jid}/{userID}", method = RequestMethod.GET)
    public JSONObject getJobDetails(@PathVariable("jid") String jid , @PathVariable("userID") String uid) {
        System.out.println("In jobs controller");
        return jobsService.getJobDetailsAt(jid,uid);
    }

    @RequestMapping(value = "/jobs/{jid}", method = RequestMethod.DELETE)
    public void deleteJob(@PathVariable("jid") String jid) throws Exception{

         jobsService.deleteJobAt(jid);
    }

    @RequestMapping(value = "/jobs/company/{c_id}", method = RequestMethod.GET)
    public JSONArray getAllCompanyJobs(@PathVariable("c_id") String c_id) throws Exception{

        return jobsService.getAllCompanyJobsAt(c_id);
    }

    //returns all applications for a particular job
    @RequestMapping(value = "/jobs/applist/{j_id}", method = RequestMethod.GET)
    public List getAlApps(@PathVariable("j_id") String j_id) throws Exception{

        return jobsService.getAlAppsAt(j_id);
    }

    }


