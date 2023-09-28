package uk.gov.bristol.send.pages.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;

@Component
public class HeaderPage {
    @FindBy(id = "myaccount__logout")
    private WebElement signOutLink;

    @FindBy(id = "upn")
    private WebElement upnIdentifier;

    public HeaderPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void clickSignOutLink() {
        signOutLink.click();
    }
}
