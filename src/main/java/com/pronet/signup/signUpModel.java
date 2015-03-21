package com.pronet.signup;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by varuna on 3/19/15.
 */
public class signUpModel {
    @JsonProperty
    private String name;

    @JsonProperty @NotBlank
    @Email
    private String email;

    @JsonProperty @NotBlank
    private String password;

    @JsonProperty @NotBlank
    private String role;

    public signUpModel(){}


    public signUpModel(@JsonProperty String name, @JsonProperty String email, @JsonProperty String password , @JsonProperty String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}
