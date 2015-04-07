package com.pronet.signup;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class SignUp {

    private String ID;

    @JsonProperty
    private String user_name;

    @JsonProperty @NotBlank
    @Email
    private String email;

    @JsonProperty @NotBlank
    private String password;

    @JsonProperty @NotBlank
    private String role;

    private String last_seen;

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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

    public void setLast_seen(String last_seen) {
        this.last_seen = last_seen;
    }

    public SignUp(){}


    public SignUp(@JsonProperty String user_name, @JsonProperty String email, @JsonProperty String password, @JsonProperty String role) {
        this.user_name = user_name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getUser_name() {
        return user_name;
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

    public String getLast_seen() {
        return last_seen;
    }
}
