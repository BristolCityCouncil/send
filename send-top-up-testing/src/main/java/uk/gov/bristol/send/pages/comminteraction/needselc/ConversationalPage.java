package uk.gov.bristol.send.pages.comminteraction.needselc;

import org.springframework.stereotype.Component;
import uk.gov.bristol.send.pages.AccordionPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Component
public class ConversationalPage extends AccordionPage {

    @FindBy(id = "A1SA1SC8SG10_accordion")
    private WebElement accordionBtn;

    @FindBy(id = "A1SA1SC8SG10STT1")
    private WebElement statementTargeted;

    @FindBy(id = "A1SA1SC8SG10STA1")
    private WebElement statementLevelA;

    @FindBy(id = "A1SA1SC8SG10STB1")
    private WebElement statementLevelB;

    @FindBy(id = "A1SA1SC8SG10STC1")
    private WebElement statementLevelC;

    @FindBy(id = "A1SA1SC8SG10_clear")
    private WebElement clearSelectionBtn;

    public ConversationalPage(WebDriver webDriver) {
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
