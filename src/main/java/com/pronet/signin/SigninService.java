package com.pronet.signin;

import com.pronet.signup.SignUpDetails;

/**
 * Created by neerajakukday on 3/13/15.
 */
public interface SigninService {

    public String userLogin(SignUpDetails signUpDetails) throws Exception;
}
