package uk.gov.bristol.send.pages.summary;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;
import uk.gov.bristol.send.pages.BasePage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CognitionLearningPage extends BasePage {

    @FindBy(id = "needshref_academicLearning")
    private WebElement aleNeedsLink;

    @FindBy(id = "provisionhref_academicLearning")
    private WebElement aleProvisionsLink;

    @FindBy(id = "needshref_behavioursForLearning")
    private WebElement bflNeedsLink;

    @FindBy(id = "provisionhref_behavioursForLearning")
    private WebElement bflProvisionsLink;

    @FindBy(id = "provisionPageLink_academicLearning")
    private WebElement aleSelectedProvisions;

    @FindBy(id = "provisionPageLink_behavioursForLearning")
    private WebElement bflSelectedProvisions;

    @FindBy(id = "maximumNeedLevel")
    private WebElement maxNeedLevel;

    @FindBy(id = "maxNeedNotSelected")
    private WebElement maxNeedNotSelected;

    public String getMaxNeedLevel() {
        return maxNeedLevel.getText();
    }

    public WebElement getMaxNeedNotSelected() {
        return maxNeedNotSelected;
    }

    public CognitionLearningPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public List<WebElement> getAllNeedsLinks() {
        return new ArrayList<>(List.of(aleNeedsLink, bflNeedsLink));
    }

    public List<WebElement> getAllProvisionsLinks() {
        return new ArrayList<>(List.of(aleProvisionsLink, bflProvisionsLink));
    }

    public void clickAleNeedsLink() {
        aleNeedsLink.click();
    }

    public void clickAleProvisionsLink() {
        aleProvisionsLink.click();
    }

    public void clickBflNeedsLink() {
        bflNeedsLink.click();
    }

    public void clickBflProvisionsLink() {
        bflProvisionsLink.click();
    }

    public WebElement getAleProvisionsLink() {
        return aleProvisionsLink;
    }

    /**
     * Return a Map of all Expression Language Communication Provisions found on the
     * Summary page. Key values of ProvisionType -> ProvisionText
     * @return Map
     */
    public Map<String, String> getAleSummaryProvisions() {
        // Each selected provision is divided into 'info' <div> sections
        List<WebElement> summaryProvisions = aleSelectedProvisions.findElements(By.className("info"));

        Map<String, String> summaryProvisionMap = new HashMap<>();
        for (WebElement element : summaryProvisions) {
            String text = element.getText();
            Object[] provisionAttributes = text.lines().toArray();
            summaryProvisionMap.put((String) provisionAttributes[1], (String) provisionAttributes[2]);
        }
        return summaryProvisionMap;
    }

    public Map<String, String> getBflSummaryProvisions() {
        return getSummaryProvisions(bflSelectedProvisions);
    }
}
