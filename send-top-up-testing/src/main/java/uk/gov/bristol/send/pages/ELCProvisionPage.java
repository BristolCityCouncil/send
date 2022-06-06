package uk.gov.bristol.send.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.bristol.send.Assessment;

@Component
public class ELCProvisionPage extends BasePage {

    @Autowired
    private Assessment assessment;

    public ELCProvisionPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public void selectByIndex(WebElement webElement, String indexStr, String provisionType) {
        Select dropdown = new Select(webElement);
        dropdown.selectByIndex(Integer.parseInt(indexStr));

        assessment.setElcProvisions(provisionType, dropdown.getFirstSelectedOption().getAttribute("title"));
    }

}
