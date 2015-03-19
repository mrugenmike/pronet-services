package com.pronet.signin;

import com.pronet.BadRequestException;
import com.pronet.signup.SignUpDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by neerajakukday on 3/13/15.
 */
@RestController
@Component("SigninResource")
public class SigninResource {

    SigninService signinService;

    @Autowired
    public SigninResource(SigninService signinService) {
        this.signinService = signinService;
    }


        @RequestMapping(value="/signin",method = RequestMethod.POST)
    public ResponseEntity<String> userLogin(@Valid @RequestBody SignUpDetails signUpDetails, BindingResult result) throws Exception{
        if(result.hasErrors())
        {
            throw new BadRequestException("Request Body is missing parameters");

        }
        return new ResponseEntity<String>(signinService.userLogin(signUpDetails), HttpStatus.OK);
    }

}
