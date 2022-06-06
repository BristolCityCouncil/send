package uk.gov.bristol.send.pages.physicalsensory.sensory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;
import uk.gov.bristol.send.pages.AccordionPage;

@Component
public class GustatorySystemPage extends AccordionPage {

    @FindBy(id = "A4SA10SC5SG9_accordion")
    private WebElement accordionBtn;

    @FindBy(id = "A4SA10SC5SG9STT1")
    private WebElement statementTargeted;

    @FindBy(id = "A4SA10SC5SG9STA1")
    private WebElement statementLevelA;

    @FindBy(id = "A4SA10SC5SG9STB1")
    private WebElement statementLevelB;

    @FindBy(id = "A4SA10SC5SG9STC1")
    private WebElement statementLevelC;

    @FindBy(id = "A4SA10SC5SG9_clear")
    private WebElement clearSelectionBtn;

    public GustatorySystemPage(WebDriver webDriver) {
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
