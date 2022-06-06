package uk.gov.bristol.send.pages.comminteraction.needselc;

import org.springframework.stereotype.Component;
import uk.gov.bristol.send.pages.AccordionPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Component
public class WordFindingPage extends AccordionPage {

    @FindBy(id = "A1SA1SC3SG4_accordion")
    private WebElement accordionBtn;

    @FindBy(id = "A1SA1SC3SG4STT1")
    private WebElement statementTargeted;

    @FindBy(id = "A1SA1SC3SG4STA1")
    private WebElement statementLevelA;

    @FindBy(id = "A1SA1SC3SG4STB1")
    private WebElement statementLevelB;

    @FindBy(id = "A1SA1SC3SG4STC1")
    private WebElement statementLevelC;

    @FindBy(id = "A1SA1SC3SG4_clear")
    private WebElement clearSelectionBtn;

    public WordFindingPage(WebDriver webDriver) {
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
        expandSectionSelectLvl(accordionBtn, statementLevelB);
    }

    public void selectStatementLevelC() {
        expandSectionSelectLvl(accordionBtn, statementLevelC);
    }

    public void clickClearSelection() {
        clearStatementSelection(accordionBtn, clearSelectionBtn);
    }
}
