import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/FeatureFiles",
        glue = {"support", "stepdefinition"},
        plugin = {"json:target/cucumber.json", "html:target/site/cucumber-pretty", "junit:target/cucumber.xml"},
        monochrome = true, //display the console output
        //strict = false, // check not defined step definitions
        dryRun = false // check the mapping is proper between feature file and step definition
)

public class TestRunner {

}
