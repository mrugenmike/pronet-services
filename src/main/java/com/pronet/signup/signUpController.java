package com.pronet.signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import com.pronet.BadRequestException;

/**
 * Created by varuna on 3/19/15.
 */
@RestController
@Component("signUpController")
public class signUpController {

    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @RequestMapping(value = "/signingUp", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void signUpUser(@Valid @RequestBody signUpModel model, BindingResult result) throws EmptyResultDataAccessException {
        {
            if (model.getName() == null || model.getName().trim().equals(""))
                throw new BadRequestException("Require Name");

            if (result.hasErrors()) {
                throw new BadRequestException("Error in Request Body");
            }

            //insert into database - RDS - local mysql

            try {
                System.out.println(model.getEmail() + " " + model.getName() + " " + model.getPassword() + " " + model.getRole() + " ");
                String sql = "SELECT ID FROM user_details WHERE email ='" + model.getEmail() + "'";
                String id = jdbcTemplate.queryForObject(sql, String.class);
                if (!id.isEmpty())
                    throw new BadRequestException("Account is already registered");

            } catch (EmptyResultDataAccessException e) {
                jdbcTemplate.update(
                        "INSERT INTO user_details(name,email,password,role) values(?,?,?,?)",
                        model.getName(), model.getEmail(), model.getPassword(), model.getRole());
            }

        }
        //return new ResponseEntity<String>("true",HttpStatus.CREATED);
    }

}
