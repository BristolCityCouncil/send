package uk.gov.bristol.send.pages.elcprovision;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import uk.gov.bristol.send.pages.ELCProvisionPage;

public class FamilySupportPage extends ELCProvisionPage {

    @FindBy(id = "dropdown-PTID25")
    private WebElement familySupportDD;

    private static final String FAMILY_SUPPORT = "Family Support";

    public FamilySupportPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public void selectByIndex(String provisionType, String indexStr) {
        if (FAMILY_SUPPORT.equalsIgnoreCase(provisionType)) {
            selectByIndex(familySupportDD, indexStr, FAMILY_SUPPORT);
        }
    }
}
