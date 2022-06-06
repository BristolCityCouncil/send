package uk.gov.bristol.send.pages.elcneeds;

import uk.gov.bristol.send.Utils;
import uk.gov.bristol.send.pages.ELCNeedsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class StyleOfCommunication extends ELCNeedsPage {

    // TODO: Needs - Expressive Language and Comms - no ID tags on accordion.

    @FindBy(id = "A1SA1SC1SG1_accordion")
    private WebElement styleOfCommBtn;

    @FindBy(id = "A1SA1SC1SG1STA1")
    private WebElement styleLevelA;

    @Autowired
    private WebDriver webDriver;

    public void clickStyleOfCommBtn(){
        Utils.driverWait(1000);
        styleOfCommBtn.click();
    }

    public void selectStyleLevelA() {
        Utils.jsScrollIntoView(webDriver, styleLevelA);
        Utils.jsClick(webDriver, styleLevelA);
    }


    public StyleOfCommunication(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

}
