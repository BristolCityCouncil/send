package uk.gov.bristol.send.pages.summary;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.bristol.send.Assessment;
import uk.gov.bristol.send.pages.BasePage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A section of the Summary page for Communication & Interaction.
 * This contains the Needs and Provisions links.
 */
@Component
public class CommunicationInteractionPage extends BasePage {
    
    @FindBy(id = "needshref_expressiveCommunication")
    private WebElement elcNeedsLink;

    @FindBy(id = "needshref_receptiveCommunication")
    private WebElement rlcNeedsLink;

    @FindBy(id = "maximumNeedLevel_expressiveCommunication")
    private WebElement elcNeedsLevel;

    @FindBy(id = "provisionhref_expressiveCommunication")
    private WebElement elcProvisionsLink;

    @FindBy(id = "provisionhref_receptiveCommunication")
    private WebElement rlcProvisionsLink;

    @FindBy(id = "provisionPageLink_receptiveCommunication")
    private WebElement rlcSelectedProvisions;

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
        super(webDriver);
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void clickElcNeedsLink() {
        elcNeedsLink.click();
    }

    public List<WebElement> getAllNeedsLinks() {
        return new ArrayList<>(List.of(elcNeedsLink, rlcNeedsLink));
    }

    public List<WebElement> getAllProvisionsLinks() {
        return new ArrayList<>(List.of(elcProvisionsLink, rlcProvisionsLink));
    }

    public void clickRlcNeedsLink() {
        rlcNeedsLink.click();
    }

    public void clickRemoveElcProvisionLink(String itemNumber) {
        String provisionType = webDriver.findElement(By.id("selectedProvisionType_expressiveCommunication_" + itemNumber)).getText();
        webDriver.findElement(By.id("removeProvisionLink_expressiveCommunication_" + itemNumber)).click();
        assessment.removeProvision(provisionType);
    }

    public Map<String, String> getElcSummaryProvisions() {
        return getSummaryProvisions(elcSelectedProvisions);
    }

    public Map<String, String> getRlcSummaryProvisions() {
        return getSummaryProvisions(rlcSelectedProvisions);
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
    public void clickRlcProvisionsLink() {
        rlcProvisionsLink.click();
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
