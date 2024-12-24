package com.project.wishlist;

import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static io.cucumber.core.options.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@SpringBootTest
@SuppressWarnings("all")
@IncludeEngines("cucumber")
@CucumberContextConfiguration
@SelectPackages("com.project.wishlist")
@ContextConfiguration(classes = Object.class)
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
public class CucumberSpringConfiguration {
}