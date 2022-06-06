package uk.gov.bristol.send.pages.comminteraction.needsrlc;

import org.springframework.stereotype.Component;
import uk.gov.bristol.send.pages.AccordionPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Component
public class AttentionListeningPage extends AccordionPage {

    @FindBy(id = "A1SA2SC1SG1_accordion")
    private WebElement accordionBtn;

    @FindBy(id = "A1SA2SC1SG1STT1")
    private WebElement statementTargeted;

    @FindBy(id = "A1SA2SC1SG1STA1")
    private WebElement statementLevelA;

    @FindBy(id = "A1SA2SC1SG1STB1")
    private WebElement statementLevelB;

    @FindBy(id = "A1SA2SC1SG1STC1")
    private WebElement statementLevelC;

    @FindBy(id = "A1SA2SC1SG1_clear")
    private WebElement clearSelectionBtn;

    public AttentionListeningPage(WebDriver webDriver) {
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
