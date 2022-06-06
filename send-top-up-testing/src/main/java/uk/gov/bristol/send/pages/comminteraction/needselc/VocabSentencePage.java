package uk.gov.bristol.send.pages.comminteraction.needselc;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;
import uk.gov.bristol.send.pages.AccordionPage;

@Component
public class VocabSentencePage extends AccordionPage {

    @FindBy(id = "A1SA1SC2SG2_accordion")
    private WebElement accordionBtn;

    @FindBy(id = "A1SA1SC2SG2STT1")
    private WebElement statementTargeted;

    @FindBy(id = "A1SA1SC2SG2STA1")
    private WebElement statementLevelA;

    @FindBy(id = "A1SA1SC2SG2STA2")
    private WebElement statementLevelA2;

    @FindBy(id = "A1SA1SC2SG2STB1")
    private WebElement statementLevelB;

    @FindBy(id = "A1SA1SC2SG2STB2")
    private WebElement statementLevelB2;

    @FindBy(id = "A1SA1SC2SG2STC1")
    private WebElement statementLevelC;

    @FindBy(id = "A1SA1SC2SG2STC2")
    private WebElement statementLevelC2;

    @FindBy(id = "A1SA1SC2SG2_clear")
    private WebElement clearSelectionBtn;

    public VocabSentencePage(WebDriver webDriver) {
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
