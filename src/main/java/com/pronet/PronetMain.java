package com.pronet;

import com.pronet.PronetConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by neerajakukday on 3/13/15.
 */

@SpringBootApplication
public class PronetMain {

    public static void main(String[] args) {
        SpringApplication.run(PronetConfig.class, args);
    }
}
