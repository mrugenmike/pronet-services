package com.pronet.signin;

import com.pronet.BadRequestException;
import com.pronet.signup.signUpModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by varuna on 3/19/15.
 */
@RestController
@Component("signInController")
public class SignInController {

    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ResponseEntity<String> signInUser(@Valid @RequestBody singInModel model, BindingResult result) throws EmptyResultDataAccessException {
        {
            System.out.println(model.getEmail() + " " + model.getPassword() + " " );
            if (result.hasErrors()) {
                throw new BadRequestException("Error in Request Body");
            }

            //insert into database - RDS - local mysql

            try {
                System.out.println(model.getEmail() + " " + model.getPassword() + " " );
                String sql = "SELECT password FROM user_details WHERE email ='" + model.getEmail() + "'";
                String password = jdbcTemplate.queryForObject(sql, String.class);
                System.out.println("Password : "+password);
                if (password != model.getPassword())
                    throw new BadRequestException("Invalid password ");

            } catch (EmptyResultDataAccessException e) {
                System.out.println("Invalid Email");
                throw new BadRequestException("Invalid Email");
            }

        }
        return new ResponseEntity<String>("true",HttpStatus.OK);
    }

}
