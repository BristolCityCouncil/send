package uk.gov.bristol.send.pages.summary;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * A section of the Summary page for Communication & Interaction.
 * This contains the Needs and Provisions links.
 */
public class CommunicationInteraction {
    
    @FindBy(id = "needsPageLink_expressiveCommunication")
    private WebElement elcNeedsTitle;

    @FindBy(id = "maximumNeedLevel_expressiveCommunication")
    private WebElement elcNeedsLevel;

    @FindBy(id = "provisionPageLink_expressiveCommunication")
    private WebElement elcProvisionsTitle;

    @FindBy(id = "selectedProvisionTitle_expressiveCommunication_1")
    private WebElement elcProvisionSelectedTitle;

    @FindBy(id = "selectedProvisionText__expressiveCommunication_1")
    private WebElement elcProvisionSelectedText;

    @FindBy(id = "selectedProvisionType_expressiveCommunication_1")
    private WebElement elcProvisionSelectedType;

    @FindBy(id = "selectedProvisionLinks_expressiveCommunication_1")
    private WebElement elcProvisionsEditRemove;


    public CommunicationInteraction(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void clickElcNeedsLink() {
        elcNeedsTitle.findElement(By.linkText("Expressive language and communication")).click();
    }

    public String getELCNeedsLevel() {
        return elcNeedsLevel.getText();
    }

    public String getElcProvisionsTitle() {
        return elcProvisionsTitle.getText();
    }

    public void clickElcProvisionsLink() {
        elcProvisionsTitle.findElement(By.linkText("Expressive language and communication")).click();
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
    public void clickElcProvisionEdit() {
        elcProvisionsEditRemove.findElement(By.linkText("Edit")).click();
    }

    public void clickElcProvisionRemove() {
        elcProvisionsEditRemove.findElement(By.linkText("Remove")).click();
    }
}
