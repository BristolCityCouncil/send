package uk.gov.bristol.send.pages.physicalsensory.sensory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;
import uk.gov.bristol.send.pages.AccordionPage;

@Component
public class VisualProcessingPage extends AccordionPage {

    @FindBy(id = "A4SA10SC6SG10_accordion")
    private WebElement accordionBtn;

    @FindBy(id = "A4SA10SC6SG10STT1")
    private WebElement statementTargeted;

    @FindBy(id = "A4SA10SC6SG10STA1")
    private WebElement statementLevelA;

    @FindBy(id = "A4SA10SC6SG10_clear")
    private WebElement clearSelectionBtn;

    public VisualProcessingPage(WebDriver webDriver) {
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
