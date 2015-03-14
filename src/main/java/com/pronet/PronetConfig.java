package com.pronet;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by neerajakukday on 3/13/15.
 */

@org.springframework.context.annotation.Configuration
@EnableAutoConfiguration
@ComponentScan
@PropertySource(value = "classpath:/db-rds.properties",ignoreResourceNotFound = false)
public class PronetConfig {



}
