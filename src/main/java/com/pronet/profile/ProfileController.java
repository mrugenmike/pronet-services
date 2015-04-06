package com.pronet.profile;


import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@Component("ProfileController")
@RequestMapping("/api/v1")
public class ProfileController {

    ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public JSONObject getProfile(@PathVariable("id") String id) {

        return profileService.getProfileAt(Integer.parseInt(id));


    }

    @RequestMapping(value = "/profile/updateImageURL", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateProfile(@RequestBody ProfileImgUpdateModel model) {

        profileService.updateProfileAt(model);


    }
}
