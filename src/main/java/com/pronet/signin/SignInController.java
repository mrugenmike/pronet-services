package com.pronet.signin;


import com.pronet.BadRequestException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Component("signInController")
@RequestMapping("/api/v1")
public class SignInController {

    SignInService signInService;

    @Autowired
    public SignInController(SignInService signInService) {
        this.signInService = signInService;
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public JSONObject signInUser(@Valid @RequestBody SignInModel model, BindingResult result) {

            if (result.hasErrors()) {
                throw new BadRequestException("Error in Request Body");
            }

        return signInService.signInUserAt(model);

    }
}
