package com.pronet.recommendation;

import org.apache.mahout.cf.taste.common.TasteException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by varuna on 4/28/15.
 */

@RestController
@Component("RecommendationController")
@RequestMapping("/api/v1")
public class RecommendationController {

    RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @RequestMapping(value = "/topNfriends/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List getTopNFriends(@PathVariable("id") String id) throws EmptyResultDataAccessException, TasteException, UnknownHostException {
        int intID = Integer.parseInt(id);
        return recommendationService.getTopThreeFriends(intID);
    }

    @RequestMapping(value = "/skills/recommendation/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List getSkills(@PathVariable("id") String id) throws EmptyResultDataAccessException, TasteException, UnknownHostException {
        int intID = Integer.parseInt(id);
        return recommendationService.getTopThreeSkills(intID);
    }


    @RequestMapping(value = "/skills/PearsonReco/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List getSkillsPearson(@PathVariable("id") String id) throws EmptyResultDataAccessException, TasteException, UnknownHostException {
        int intID = Integer.parseInt(id);
        return recommendationService.Pearson(intID);
    }

    @RequestMapping(value = "/skills/EuclideanReco/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List getSkillsEuclidean(@PathVariable("id") String id) throws EmptyResultDataAccessException, TasteException, UnknownHostException {
        int intID = Integer.parseInt(id);
        return recommendationService.Euclidean(intID);
    }

    @RequestMapping(value = "/skills/TanimotoReco/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List getSkillsTanimoto(@PathVariable("id") String id) throws EmptyResultDataAccessException, TasteException, UnknownHostException {
        int intID = Integer.parseInt(id);
        return recommendationService.Tanimoto(intID);
    }

    @RequestMapping(value = "/careerPath/recommendation/{currentRole}/{DestinationRole}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<JSONObject> getCareerPathRecommendation(@PathVariable("currentRole") String currentRole,@PathVariable("DestinationRole") String DestinationRole) throws Exception
    {
        return recommendationService.careerPath(currentRole, DestinationRole);
    }

    @RequestMapping(value = "/career/recommendation/{currentPosition}/{expectedPostion}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<CareerPath> getFind(@PathVariable String currentPosition,@PathVariable String expectedPostion)
    {
        final List<CareerPath> careerPaths = recommendationService.fetchCareerPathRecommendation(currentPosition, expectedPostion);
        return careerPaths;
    }

    @RequestMapping(value = "/jobs/recommendation/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ArrayList getJobs(@PathVariable("id") String id) throws EmptyResultDataAccessException, TasteException, UnknownHostException {
        int intID = Integer.parseInt(id);
        return recommendationService.getTopThreeJobs(intID);
    }
}
