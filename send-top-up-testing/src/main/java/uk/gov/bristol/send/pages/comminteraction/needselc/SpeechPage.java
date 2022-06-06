package uk.gov.bristol.send.pages.comminteraction.needselc;

import org.springframework.stereotype.Component;
import uk.gov.bristol.send.pages.AccordionPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Component
public class SpeechPage extends AccordionPage {

    @FindBy(id = "A1SA1SC4SG5_accordion")
    private WebElement accordionBtn;
                   //A1SA1SC4SG5ST1
    @FindBy(id = "A1SA1SC4SG5STT1")
    private WebElement statementTargeted;

    @FindBy(id = "A1SA1SC4SG5STA1")
    private WebElement statementLevelA;

    @FindBy(id = "A1SA1SC4SG5STA2")
    private WebElement statementLevelA2;

    @FindBy(id = "A1SA1SC4SG5STA3")
    private WebElement statementLevelA3;

    @FindBy(id = "A1SA1SC4SG5STB1")
    private WebElement statementLevelB;

    @FindBy(id = "A1SA1SC4SG5STB2")
    private WebElement statementLevelB2;

    @FindBy(id = "A1SA1SC4SG5STB3")
    private WebElement statementLevelB3;

    @FindBy(id = "A1SA1SC4SG5STC1")
    private WebElement statementLevelC;

    @FindBy(id = "A1SA1SC4SG5STC2")
    private WebElement statementLevelC2;

    @FindBy(id = "A1SA1SC4SG5STC3")
    private WebElement statementLevelC3;

    @FindBy(id = "A1SA1SC4SG5_clear")
    private WebElement clearSelectionBtn;

    public SpeechPage(WebDriver webDriver) {
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
