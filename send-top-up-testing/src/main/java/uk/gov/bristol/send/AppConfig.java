package uk.gov.bristol.send;

import uk.gov.bristol.send.pages.BasePage;
import uk.gov.bristol.send.pages.ELCNeedsPage;
import uk.gov.bristol.send.pages.ELCProvisionPage;
import uk.gov.bristol.send.pages.elcneeds.StyleOfCommunicationPage;
import uk.gov.bristol.send.pages.elcprovision.AdditionalStaffPage;
import uk.gov.bristol.send.pages.elcprovision.SpecialistProfessionalPage;
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

    @Bean
    public BasePage basePage() { return new BasePage(webDriver()); }
    @Bean
    public HomePage homePage() {
        return new HomePage(webDriver());
    }

    @Bean
    public CommunicationInteractionPage communicationInteractionPage() {

        return new CommunicationInteractionPage(webDriver());
    }

    @Bean
    public SpecialistProfessionalPage specialistProfessionalPage() {
        return new SpecialistProfessionalPage(webDriver());
    }

    @Bean
    public StyleOfCommunicationPage styleOfCommunicationPage() {
        return new StyleOfCommunicationPage(webDriver());
    }

    @Bean
    public AdditionalStaffPage additionalStaffPage() {
        return new AdditionalStaffPage(webDriver());
    }

    @Bean
    public Assessment assessment() {
        return new Assessment();
    }

    @Bean
    public ELCProvisionPage elcProvisionPage() {
        return new ELCProvisionPage(webDriver());
    }

    @Bean
    public ELCNeedsPage elcNeedsPage() {
        return new ELCNeedsPage(webDriver());
    }
}
