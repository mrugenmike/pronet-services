package com.pronet.signin;

import com.pronet.signup.SignUpDetails;

import java.sql.SQLException;

/**
 * Created by neerajakukday on 3/13/15.
 */
public interface DataServiceSignin {

    public String userLoginDB(SignUpDetails signUpDetails) throws SQLException;

}
