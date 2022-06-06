package uk.gov.bristol.send.pages.cognitionlearning.needsbfl;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;
import uk.gov.bristol.send.pages.AccordionPage;

@Component
public class FlexibleThinkingPage extends AccordionPage {

    @FindBy(id = "A2SA4SC4SG6_accordion")
    private WebElement accordionBtn;

    @FindBy(id = "A2SA4SC4SG6STT1")
    private WebElement statementTargeted;

    @FindBy(id = "A2SA4SC4SG6STA1")
    private WebElement statementLevelA;

    @FindBy(id = "A2SA4SC4SG6STB1")
    private WebElement statementLevelB;

    @FindBy(id = "A2SA4SC4SG6STC1")
    private WebElement statementLevelC;

    @FindBy(id = "A2SA4SC4SG6_clear")
    private WebElement clearSelectionBtn;

    public FlexibleThinkingPage(WebDriver webDriver) {
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
