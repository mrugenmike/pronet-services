package com.pronet.signin;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by varuna on 3/19/15.
 */
public class singInModel {

    @JsonProperty
    @NotBlank
    @Email
    private String email;

    @JsonProperty @NotBlank
    private String password;

    public singInModel(){}


    public singInModel( @JsonProperty String email, @JsonProperty String password) {
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
