package com.pronet.healthcheck;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HealthCheckResource {

    @RequestMapping("/api/v1/healthcheck")
    public ResponseEntity<String> healthCheck(){
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
