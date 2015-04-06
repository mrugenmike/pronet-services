package com.pronet.company;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/api/v1")
public class CompanyController {

    CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RequestMapping(value = "/company/update",method= RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    JSONObject updateDetails(@RequestBody CompanyDetails companyDetails) throws Exception
    {
        return companyService.updateDetailsAt(companyDetails);
    }

    

    @RequestMapping(value = "/companyprofile/{id}/{currentID}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JSONObject getCompanyDetails(@PathVariable("id") String id, @PathVariable("currentID") String currentID) throws Exception {

       return companyService.getCompanyDetailsAt(id,currentID);
    }


}
