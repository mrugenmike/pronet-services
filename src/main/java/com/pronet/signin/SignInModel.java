package com.pronet.signin;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;


public class SignInModel {

    @JsonProperty
    @NotBlank
    @Email
    private String email;

    @JsonProperty @NotBlank
    private String password;

    public SignInModel(){}


    public SignInModel(@JsonProperty String email, @JsonProperty String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
