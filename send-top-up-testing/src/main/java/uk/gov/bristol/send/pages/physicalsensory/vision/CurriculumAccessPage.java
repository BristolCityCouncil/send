package uk.gov.bristol.send.pages.physicalsensory.vision;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;
import uk.gov.bristol.send.pages.AccordionPage;

@Component
public class CurriculumAccessPage extends AccordionPage {

    @FindBy(id = "A4SA8SC2SG3_accordion")
    private WebElement accordionBtn;

    @FindBy(id = "A4SA8SC2SG3STT1")
    private WebElement statementTargeted;

    @FindBy(id = "A4SA8SC2SG3STA1")
    private WebElement statementLevelA;

    @FindBy(id = "A4SA8SC2SG3STB1")
    private WebElement statementLevelB;

    @FindBy(id = "A4SA8SC2SG3STC1")
    private WebElement statementLevelC;

    @FindBy(id = "A4SA8SC2SG3_clear")
    private WebElement clearSelectionBtn;

    public CurriculumAccessPage(WebDriver webDriver) {
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
