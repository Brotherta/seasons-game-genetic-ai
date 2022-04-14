package com.game;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(value = Cucumber.class)
@CucumberOptions(plugin = {"pretty"}, features = "src/test/resources/features", glue = { "com.game", "com.utils"})
public class RunCucumberTest { // will run all features found on the classpath
    // in the same package as this class
}