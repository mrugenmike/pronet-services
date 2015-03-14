package com.pronet.signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by neerajakukday on 3/13/15.
 */
@RestController
@Component("SignupResource")
public class SignupResource {

    SignupService signupService;

    @Autowired
    public SignupResource(SignupService signUpService) {
        this.signupService = signUpService;
    }


    @RequestMapping(value="/signup",method = RequestMethod.POST)
    public SignUpDetails signUpUser(@RequestBody SignUpDetails signUpDetails) {
        return signupService.signupUser(signUpDetails);
    }
}
