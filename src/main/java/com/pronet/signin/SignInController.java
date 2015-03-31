package com.pronet.signin;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.pronet.BadRequestException;
import com.pronet.company.CompanyDetails;
import com.pronet.signup.signUpModel;
import com.pronet.signup.signUpRowMapper;
import com.pronet.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by varuna on 3/19/15.
 */
@RestController
@Component("signInController")
public class SignInController {

    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @Autowired
    DynamoDB dyDB;

    @Autowired
    DynamoDBMapper mapper;

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public JSONObject signInUser(@Valid @RequestBody singInModel model, BindingResult result) throws EmptyResultDataAccessException, JSONException {{

            if (result.hasErrors()) {
                throw new BadRequestException("Error in Request Body");
            }

            JSONObject json= new JSONObject();
            try {

                String sql = "SELECT ID FROM SignIn WHERE email ='" + model.getEmail() + "'";
                String id = jdbcTemplate.queryForObject(sql, String.class);

                System.out.println(id);

                //fetch the login details for the ID
                String sql1 = "SELECT * FROM SignIn WHERE ID =" + id ;
                List<signUpModel> user = jdbcTemplate.query(sql1, new signUpRowMapper());

                for(signUpModel u : user) {
                    System.out.println("here");
                    System.out.println(u);
                    if (!u.getPassword().equals(model.getPassword()))
                        throw new BadRequestException("Invalid email or password ");

                    //select user_details from dynamo
                    if (u.getRole().equals("U")) {

                        UserDetails getUser = mapper.load(UserDetails.class, u.getID());
                        System.out.println(getUser);
                        json.put("role" , "U");
                        json.put("id" , getUser.getID());
                        json.put("img" , getUser.getImgURL());
                        json.put("name", getUser.getName());
                        json.put("role" , getUser.getRole());
                        json.put("region" , getUser.getRegion());
                        json.put("education", getUser.getEducation());
                        json.put("workex", getUser.getWorkExperience());
                        json.put("summary", getUser.getSummary());
                        json.put("educationDetails", getUser.getEducationDetails());
                        json.put("certification", getUser.getCertifications());
                        json.put("skills", getUser.getSkills());
                        json.put("lastseen", getUser.getLastseen());
                        System.out.println(json);
                    }
                    else
                    {
                        CompanyDetails getCompany = mapper.load(CompanyDetails.class, u.getID());
                        System.out.println(getCompany);
                        json.put("role","C");
                        json.put("id" , getCompany.getID());
                        json.put("name" , getCompany.getName());
                        json.put("logo" , getCompany.getLogo());
                        json.put("companyURL" , getCompany.getUrl());
                        json.put("overview", getCompany.getOverview());
                        System.out.println(json);
                    }
                }
                //return user details to front end

            } catch (EmptyResultDataAccessException e) {
                System.out.println("User does not exists");
                throw new BadRequestException("User does not exists");
            }
        return json;
    }
        //return new ResponseEntity<String>("true",HttpStatus.OK);
    }
}
