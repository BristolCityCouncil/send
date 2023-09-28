package uk.gov.bristol.send.pages;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.bristol.send.Assessment;
import uk.gov.bristol.send.Utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BasePage {

    @FindBy(id = "backButton")
    protected WebElement backButton;

    @FindBy(xpath = "//button[@value='Submit']")
    protected WebElement saveButton;

    @FindBy(id = "pageTitleId")
    private WebElement pageTitle;

    @FindBy(className = "assessment")
    private WebElement summaryAssessment;

    @FindBy(id = "overview-href")
    private WebElement overviewLink;

    @Autowired
    private Assessment assessment;

    private WebDriver webDriver;
    public BasePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void clickBackButton() {
        WebElement updatedBack = new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(backButton));
        updatedBack.click();
        assessment.clearTransientValues();
    }
    public void clickSaveButton() {
        saveButton.click();
        assessment.saveTransientValues();
        Utils.driverWait(2000);
    }

    public String getPageTitle() {
        return pageTitle.getText();
    }

    public void clickOverviewLink() {
        overviewLink.click();
    }

    public WebElement getSummaryAssessment() {
        return summaryAssessment;
    }

    /**
     * Return a Map of all Provisions found on the Summary page.
     * Key values of ProvisionType -> ProvisionText
     * @return Map
     * @param provisionSection
     * @return Map
     */
    public Map<String, String> getSummaryProvisions(WebElement provisionSection) {
        // Each selected provision is divided into 'info' <div> sections
        List<WebElement> summaryProvisions = provisionSection.findElements(By.className("info"));

        Map<String, String> summaryProvisionMap = new HashMap<>();
        for (WebElement element : summaryProvisions) {
            String text = element.getText();
            Object[] provisionAttributes = text.lines().toArray();
            summaryProvisionMap.put((String) provisionAttributes[1], (String) provisionAttributes[2]);
        }
        return summaryProvisionMap;
    }

}
