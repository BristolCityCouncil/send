package uk.gov.bristol.send.pages.physicalsensory.needsmpmm;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;
import uk.gov.bristol.send.pages.AccordionPage;

@Component
public class FineMotorSkillsPage extends AccordionPage {

    @FindBy(id = "A4SA7SC5SG8_accordion")
    private WebElement accordionBtn;

    @FindBy(id = "A4SA7SC5SG8STT1")
    private WebElement statementTargeted;

    @FindBy(id = "A4SA7SC5SG8STA1")
    private WebElement statementLevelA;

    @FindBy(id = "A4SA7SC5SG8STB1")
    private WebElement statementLevelB;

    @FindBy(id = "A4SA7SC5SG8STC1")
    private WebElement statementLevelC;

    @FindBy(id = "A4SA7SC5SG8_clear")
    private WebElement clearSelectionBtn;

    public FineMotorSkillsPage(WebDriver webDriver) {
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
