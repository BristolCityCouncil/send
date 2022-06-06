package uk.gov.bristol.send;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = { "pretty",
                "html:target/site/cucumber-report.html"},
        tags = "@expCommInter or @validation",
        features = "classpath:features",
        glue = "uk.gov.bristol.send.stepdefs")
public class RunCucumberTest {

}
