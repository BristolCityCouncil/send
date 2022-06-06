package uk.gov.bristol.send.pages.comminteraction.needsrlc;

import org.springframework.stereotype.Component;
import uk.gov.bristol.send.pages.AccordionPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Component
public class MemoryPage extends AccordionPage {

    @FindBy(id = "A1SA2SC2SG2_accordion")
    private WebElement accordionBtn;

    @FindBy(id = "A1SA2SC2SG2STT1")
    private WebElement statementTargeted;

    @FindBy(id = "A1SA2SC2SG2STA1")
    private WebElement statementLevelA;

    @FindBy(id = "A1SA2SC2SG2STB1")
    private WebElement statementLevelB;

    @FindBy(id = "A1SA2SC2SG2STC1")
    private WebElement statementLevelC;

    @FindBy(id = "A1SA2SC2SG2_clear")
    private WebElement clearSelectionBtn;

    public MemoryPage(WebDriver webDriver) {
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
