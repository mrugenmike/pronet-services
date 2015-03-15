package com.pronet.signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by neerajakukday on 3/13/15.
 */
@Component("SignupServiceImpl")
public class SignupServiceImpl implements SignupService {

    public SignupServiceImpl(){}

    DataService service;

    @Autowired
    public SignupServiceImpl(DataService service){this.service = service;}

    @Override
    public SignUpDetails signupUser(SignUpDetails signUpDetails) {

        return service.registerUserDB(signUpDetails);

    }
}
