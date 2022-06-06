package uk.gov.bristol.send.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ELCNeedsPage extends BasePage {

    @FindBy(id = "maximumNeedLevel")
    private WebElement maxNeedLevel;

    @FindBy(id = "maxNeedNotSelected")
    private WebElement maxNeedNotSelected;

    public ELCNeedsPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public String getMaxNeedLevel() {
        return maxNeedLevel.getText();
    }

    public WebElement getMaxNeedNotSelected() {
        return maxNeedNotSelected;
    }
}
