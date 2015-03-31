package com.pronet.signup;

import com.sun.javafx.beans.IDProperty;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by varuna on 3/19/15.
 */
public class signUpModel {


    private String ID;

    @JsonProperty
    private String name;

    @JsonProperty @NotBlank
    @Email
    private String email;

    @JsonProperty @NotBlank
    private String password;

    @JsonProperty @NotBlank
    private String role;

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

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

    public String getID() {
        return ID;
    }
}
