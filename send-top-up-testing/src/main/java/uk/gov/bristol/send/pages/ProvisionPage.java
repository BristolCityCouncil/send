package uk.gov.bristol.send.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.bristol.send.Assessment;

@Component
public abstract class ProvisionPage extends BasePage {

    @Autowired
    private Assessment assessment;

    public ProvisionPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public String selectByIndex(WebElement webElement, String indexStr) {
        Select dropdown = new Select(webElement);
        dropdown.selectByIndex(Integer.parseInt(indexStr));

        return dropdown.getFirstSelectedOption().getAttribute("title");
    }

    public abstract void selectByIndex(String provisionType, String indexStr);

}
