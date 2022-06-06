package uk.gov.bristol.send.pages.summary;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.bristol.send.Assessment;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A section of the Summary page for Communication & Interaction.
 * This contains the Needs and Provisions links.
 */
public class CommunicationInteractionPage {
    
    @FindBy(id = "needshref_expressiveCommunication")
    private WebElement elcNeedsLink;

    @FindBy(id = "maximumNeedLevel_expressiveCommunication")
    private WebElement elcNeedsLevel;

    @FindBy(id = "provisionhref_expressiveCommunication")
    private WebElement elcProvisionsLink;

    @FindBy(id = "provisionhref_receptiveCommunication")
    private WebElement rlcProvisionsLink;

    @FindBy(id = "selectedProvisionTitle_expressiveCommunication_1")
    private WebElement elcProvisionSelectedTitle;

    @FindBy(id = "selectedProvisionText__expressiveCommunication_1")
    private WebElement elcProvisionSelectedText;

    @FindBy(id = "selectedProvisionType_expressiveCommunication_1")
    private WebElement elcProvisionSelectedType;

    @FindBy(id = "selectedProvisionLinks_expressiveCommunication_1")
    private WebElement elcProvisionsEditRemove;

    @FindBy(id = "provisionPageLink_expressiveCommunication")
    private WebElement elcSelectedProvisions;

    @Autowired
    private Assessment assessment;

    private WebDriver webDriver;

    public CommunicationInteractionPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void clickElcNeedsLink() {
        elcNeedsLink.click();
    }

    public void clickRemoveElcProvisionLink(String itemNumber) {
        String provisionType = webDriver.findElement(By.id("selectedProvisionType_expressiveCommunication_" + itemNumber)).getText();
        webDriver.findElement(By.id("removeProvisionLink_expressiveCommunication_" + itemNumber)).click();
        assessment.removeElcProvision(provisionType);
    }

    /**
     * Return a Map of all Expression Language Communication Provisions found on the
     * Summary page. Key values of ProvisionType -> ProvisionText
     * @return Map
     */
    public Map<String, String> getElcSummaryProvisions() {
        // Each selected provision is divided into 'info' <div> sections
        List<WebElement> summaryProvisions = elcSelectedProvisions.findElements(By.className("info"));

        Map<String, String> summaryTypeText = new HashMap<>();
        for (WebElement element : summaryProvisions) {
            String text = element.getText();
            Object[] provisionAttributes = text.lines().toArray();
            summaryTypeText.put((String) provisionAttributes[1], (String) provisionAttributes[2]);
        }
        return summaryTypeText;
    }

    public String getELCNeedsLevel() {
        return elcNeedsLevel.getText();
    }

    public WebElement getElcProvisionsLink() {
        return elcProvisionsLink;
    }

    public WebElement getRlcProvisionsLink() {
        return rlcProvisionsLink;
    }

    public void clickElcProvisionsLink() {
        elcProvisionsLink.click();
    }
    public String getElcProvisionSelectedTitle() {
        return elcProvisionSelectedTitle.getText();
    }

    public String getElcProvisionSelectedText() {
        return elcProvisionSelectedText.getText();
    }
    public String getElcProvisionSelectedType() {
        return elcProvisionSelectedType.getText();
    }

}
