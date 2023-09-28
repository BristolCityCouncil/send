package uk.gov.bristol.send;

import uk.gov.bristol.send.pages.BasePage;
import uk.gov.bristol.send.pages.ProvisionPage;
import uk.gov.bristol.send.pages.cognitionlearning.needsale.*;
import uk.gov.bristol.send.pages.cognitionlearning.needsbfl.*;
import uk.gov.bristol.send.pages.cognitionlearning.provisionsale.SpecialistProfessionalAlePage;
import uk.gov.bristol.send.pages.comminteraction.needselc.*;
import uk.gov.bristol.send.pages.comminteraction.needsrlc.*;
import uk.gov.bristol.send.pages.comminteraction.provisionselc.AdditionalStaffPage;
import uk.gov.bristol.send.pages.comminteraction.provisionselc.SpecialistProfessionalPage;
import uk.gov.bristol.send.pages.socialemotional.needsbem.AnxietyPage;
import uk.gov.bristol.send.pages.socialemotional.needsbem.EmotionalDevelopmentPage;
import uk.gov.bristol.send.pages.socialemotional.needsbem.EmotionalRegulationPage;
import uk.gov.bristol.send.pages.socialemotional.needsbem.IncidentsPage;
import uk.gov.bristol.send.pages.socialemotional.needssui.*;
import uk.gov.bristol.send.pages.summary.CognitionLearningPage;
import uk.gov.bristol.send.pages.summary.CommunicationInteractionPage;
import uk.gov.bristol.send.pages.HomePage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import uk.gov.bristol.send.pages.summary.PhysicalSensoryPage;
import uk.gov.bristol.send.pages.summary.SocialEmotionalMentalPage;

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
public class AppConfig {

    private static final String CHROME_BROWSER = "Chrome";

    @Bean
    public WebDriver webDriver() {
        String browserProperty = System.getProperty("browser");
        if (CHROME_BROWSER.equalsIgnoreCase(browserProperty)) {
            WebDriverManager.chromedriver().setup();
            return new ChromeDriver();
        }
        WebDriverManager.edgedriver().setup();
        return new EdgeDriver();
    }
}
