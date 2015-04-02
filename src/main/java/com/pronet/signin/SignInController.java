package com.pronet.signin;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.pronet.BadRequestException;
import com.pronet.company.CompanyProfile;
import com.pronet.signup.signUpModel;
import com.pronet.signup.signUpRowMapper;
import com.pronet.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.json.simple.JSONObject;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by varuna on 3/19/15.
 */
@Controller
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
    @ResponseBody
    public JSONObject signInUser(@Valid @RequestBody singInModel model, BindingResult result) throws EmptyResultDataAccessException {{

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
                   //System.out.println("here");
                   //System.out.println(u);
                    if (!u.getPassword().equals(model.getPassword()))
                        throw new BadRequestException("Invalid email or password ");

                    json.put("page" , u.getRole());
                    json.put("id", u.getID());
                    }
            }
            catch (EmptyResultDataAccessException e) {
                //System.out.println("User does not exists");
                throw new BadRequestException("User does not exists");
            }
            return json;
        }
    }
}
