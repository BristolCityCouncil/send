package uk.gov.bristol.send.pages.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;

@Component
public class FooterPage {

    @FindBy(linkText = "contact_link")
    private WebElement contactUs;

    public FooterPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }
}
