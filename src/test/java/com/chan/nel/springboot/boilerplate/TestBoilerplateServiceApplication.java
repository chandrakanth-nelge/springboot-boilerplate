package com.chan.nel.springboot.boilerplate;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@EnableAutoConfiguration
@EnableConfigurationProperties(value = { BootstrapServiceApplicationProperties.class })
public class TestBoilerplateServiceApplication {

}