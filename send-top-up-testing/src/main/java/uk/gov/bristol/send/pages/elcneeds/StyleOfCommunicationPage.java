package uk.gov.bristol.send.pages.elcneeds;

import uk.gov.bristol.send.Utils;
import uk.gov.bristol.send.pages.AccordionPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class StyleOfCommunicationPage extends AccordionPage {

    @FindBy(id = "A1SA1SC1SG1_accordion")
    private WebElement accordianBtn;

    @FindBy(id = "A1SA1SC1SG1STA1")
    private WebElement statementLevelA;

    @FindBy(id = "A1SA1SC1SG1STB1")
    private WebElement statementLevelB;

    @FindBy(id = "A1SA1SC1SG1STC1")
    private WebElement statementLevelC;

    @FindBy(id = "A1SA1SC1SG1_clear")
    private WebElement clearSelectionBtn;

    private WebDriver webDriver;

    public StyleOfCommunicationPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
    }

    public void clickAccordionBtn(){
        Utils.driverWait(1000);
        accordianBtn.click();
    }

    public void selectStatementLevelA() {
        Utils.jsScrollIntoView(webDriver, statementLevelA);
        Utils.jsClick(webDriver, statementLevelA);
    }

    public void selectStatementLevelB() {
        Utils.jsScrollIntoView(webDriver, statementLevelB);
        Utils.jsClick(webDriver, statementLevelB);
    }

    public void selectStatementLevelC() {
        Utils.jsScrollIntoView(webDriver, statementLevelC);
        Utils.jsClick(webDriver, statementLevelC);
    }
    public void clickClearSelection() {
        clearSelectionBtn.click();
    }
}
