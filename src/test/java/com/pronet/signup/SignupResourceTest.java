package com.pronet.signup;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
@Ignore
public class SignupResourceTest {

    @Test
    public void itSignupUser(){
        //Given
        SignupService signUpService = new SignupServiceImpl();

        SignupResource signupResource = new SignupResource(signUpService);



        //when
        SignUpDetails signUpDetails = new SignUpDetails("neeraja","kukday","nkukday@gmail.com","helloworld");
        SignUpDetails user = signupResource.signUpUser(signUpDetails);
        //then
        assert user!=null;
        assertEquals("neeraja",user.getFname());
        assertEquals("nkukday@gmail.com",user.getEmail());
        assertEquals("kukday",user.getLname());

    }

}