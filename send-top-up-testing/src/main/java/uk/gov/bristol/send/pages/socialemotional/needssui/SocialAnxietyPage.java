package uk.gov.bristol.send.pages.socialemotional.needssui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;
import uk.gov.bristol.send.pages.AccordionPage;

@Component
public class SocialAnxietyPage extends AccordionPage {

    @FindBy(id = "A3SA5SC2SG4_accordion")
    private WebElement accordionBtn;

    @FindBy(id = "A3SA5SC2SG4STT1")
    private WebElement statementTargeted;

    @FindBy(id = "A3SA5SC2SG4STA1")
    private WebElement statementLevelA;

    @FindBy(id = "A3SA5SC2SG4STB1")
    private WebElement statementLevelB;

    @FindBy(id = "A3SA5SC2SG4STC1")
    private WebElement statementLevelC;

    @FindBy(id = "A3SA5SC2SG4_clear")
    private WebElement clearSelectionBtn;

    public SocialAnxietyPage(WebDriver webDriver) {
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
