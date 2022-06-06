package uk.gov.bristol.send.pages.comminteraction.needselc;

import org.springframework.stereotype.Component;
import uk.gov.bristol.send.pages.AccordionPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Component
public class NonVerbalPage extends AccordionPage {

    @FindBy(id = "A1SA1SC10SG17_accordion")
    private WebElement accordionBtn;

    @FindBy(id = "A1SA1SC10SG17STT1")
    private WebElement statementTargeted;

    @FindBy(id = "A1SA1SC10SG17STA1")
    private WebElement statementLevelA;

    @FindBy(id = "A1SA1SC10SG17STB1")
    private WebElement statementLevelB;

    @FindBy(id = "A1SA1SC10SG17STB2")
    private WebElement statementLevelB2;

    @FindBy(id = "A1SA1SC10SG17STB3")
    private WebElement statementLevelB3;

    @FindBy(id = "A1SA1SC10SG17STB4")
    private WebElement statementLevel4;

    @FindBy(id = "A1SA1SC10SG17STC1")
    private WebElement statementLevelC;

    @FindBy(id = "A1SA1SC10SG17STC2")
    private WebElement statementLevelC2;

    @FindBy(id = "A1SA1SC10SG17STC3")
    private WebElement statementLevelC3;

    @FindBy(id = "A1SA1SC10SG17STC4")
    private WebElement statementLevelC4;

    @FindBy(id = "A1SA1SC10SG17_clear")
    private WebElement clearSelectionBtn;

    public NonVerbalPage(WebDriver webDriver) {
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
