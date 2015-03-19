package com.pronet.signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.pronet.BadRequestException;

import javax.validation.Valid;

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
    public ResponseEntity<SignUpDetails> signUpUser(@Valid @RequestBody SignUpDetails signUpDetails, BindingResult result){

        if(signUpDetails.getFname() == null || signUpDetails.getFname().trim().equals(""))
            throw new BadRequestException("Require First Name");

        if(signUpDetails.getLname() == null || signUpDetails.getLname().trim().equals(""))
            throw new BadRequestException("Require Last Name");

        if(result.hasErrors())
        {
            throw new BadRequestException("Error in Request Body");

        }

        return new ResponseEntity<SignUpDetails>(signupService.signupUser(signUpDetails), HttpStatus.CREATED);
    }

}
