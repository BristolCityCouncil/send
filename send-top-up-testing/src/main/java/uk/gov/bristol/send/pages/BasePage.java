package uk.gov.bristol.send.pages;

import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.bristol.send.Assessment;
import uk.gov.bristol.send.Utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    @FindBy(id = "back-button")
    protected WebElement backButton;

    @FindBy(xpath = "//button[@value='Submit']")
    protected WebElement saveButton;

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


}
