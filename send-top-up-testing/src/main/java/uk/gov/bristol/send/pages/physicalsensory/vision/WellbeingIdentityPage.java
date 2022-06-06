package uk.gov.bristol.send.pages.physicalsensory.vision;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;
import uk.gov.bristol.send.SENDException;
import uk.gov.bristol.send.pages.AccordionPage;

@Component
public class WellbeingIdentityPage extends AccordionPage {

    @FindBy(id = "A4SA8SC5SG10_accordion")
    private WebElement accordionBtn;

    @FindBy(id = "A4SA8SC5SG10STA1")
    private WebElement statementLevelA;

    @FindBy(id = "A4SA8SC5SG10STB1")
    private WebElement statementLevelB;

    @FindBy(id = "A4SA8SC5SG10STC1")
    private WebElement statementLevelC;

    @FindBy(id = "A4SA8SC5SG10_clear")
    private WebElement clearSelectionBtn;

    public WellbeingIdentityPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public void selectStatementTargeted() {
        throwLevelUnavailableException();
    }

    public void selectStatementLevelA() {
        expandSectionSelectLvl(accordionBtn, statementLevelA);
    }

    public void selectStatementLevelB() {
        expandSectionSelectLvl(accordionBtn, statementLevelB);
    }

    public void selectStatementLevelC() {
        expandSectionSelectLvl(accordionBtn, statementLevelC);
    }

    public void clickClearSelection() {
        clearStatementSelection(accordionBtn, clearSelectionBtn);
    }
}
