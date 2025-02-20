import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        //plugin = {"html:target/my-report.html", "json:target/json-report.json"},
        plugin = {"html:target/my-report.html", "json:target/reports.json"},
        publish = false,
        features = {"src/test/resources/features/OpenCircle/"},
        glue = {"/hooks", "/steps", "/support"}
)
public class RunCucumberTest {
}
