package com.pronet.apriori;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@Component("AprioriResource")
public class AprioriResource {

    AprioriService aprioriService;

    @RequestMapping(value = "/careerpath/{career_id}", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ArrayList getPath(@PathVariable("career_id") String career_id) throws Exception {

        return aprioriService.path(career_id);
    }
}
