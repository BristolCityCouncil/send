package uk.gov.bristol.send.pages.elcprovision;

import uk.gov.bristol.send.Assessment;
import uk.gov.bristol.send.pages.ELCProvisionPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;

public class SpecialistProfessional extends ELCProvisionPage {

    @FindBy(id = "dropdown-PTID1")
    private WebElement adviceSupportDD;

    @Autowired
    private Assessment assessment;

    public SpecialistProfessional(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public void clickAdviceSupportDD() {
        adviceSupportDD.click();
    }

    public void selectSupervisionCoaching(){
        selectByValue(adviceSupportDD, "PGID1_PTID1_PS1");


    }

    public void selectByValue(WebElement webElement, String value) {
        Select dropdown = new Select(webElement);
        dropdown.selectByValue(value);

        // something like:
        assessment.setElcProvisions("Title_Here", dropdown.getFirstSelectedOption().getAttribute("title"));

        // but state is ONLY maintained AFTER clicking SAVE... so this needs to be considered...
    }
}
