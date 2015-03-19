package com.pronet.signup;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by neerajakukday on 3/13/15.
 */

public class SignUpDetails {
    @JsonProperty
    private String fname;
    @JsonProperty
    private String lname;
    @JsonProperty @NotBlank @Email
    private String email;
    @JsonProperty @NotBlank
    private String password;

    public SignUpDetails(){}


    public SignUpDetails(@JsonProperty String fname, @JsonProperty String lname,
                         @JsonProperty String email, @JsonProperty String password) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
    }

    public String getFname() {
        return fname;
    }

    public String getEmail() {
        return email;
    }


    public String getLname() {
        return lname;
    }

    public String getPassword() {
        return password;
    }
}
