package com.pronet.signin;

import com.pronet.signup.SignUpDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by neerajakukday on 3/13/15.
 */
@Component("SigninServiceImpl")
public class SigninServiceImpl implements SigninService {

    public SigninServiceImpl(){}

    DataServiceSignin loginService;

    @Autowired
    public SigninServiceImpl(DataServiceSignin loginService){this.loginService = loginService;}

    @Override
    public String userLogin(SignUpDetails signUpDetails) throws Exception{

        return loginService.userLoginDB(signUpDetails);

    }
}
