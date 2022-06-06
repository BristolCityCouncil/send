package uk.gov.bristol.send.pages.physicalsensory.sensory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;
import uk.gov.bristol.send.SENDException;
import uk.gov.bristol.send.pages.AccordionPage;

@Component
public class InteroceptiveSensePage extends AccordionPage {

    @FindBy(id = "A4SA10SC2SG3_accordion")
    private WebElement accordionBtn;

    @FindBy(id = "A4SA10SC2SG3STT1")
    private WebElement statementTargeted;

    @FindBy(id = "A4SA10SC2SG3STA1")
    private WebElement statementLevelA;

    @FindBy(id = "A4SA10SC2SG3_clear")
    private WebElement clearSelectionBtn;

    public InteroceptiveSensePage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public void selectStatementTargeted() {

        expandSectionSelectLvl(accordionBtn, statementTargeted);
    }

    public void selectStatementLevelA() {
        expandSectionSelectLvl(accordionBtn, statementLevelA);
    }

    public void selectStatementLevelB() {
        throwLevelUnavailableException();
    }

    public void selectStatementLevelC() {
        throwLevelUnavailableException();
    }

    public void clickClearSelection() {
        clearStatementSelection(accordionBtn, clearSelectionBtn);
    }
}
