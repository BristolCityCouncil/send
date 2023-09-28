package uk.gov.bristol.send.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.stereotype.Component;
import uk.gov.bristol.send.SENDException;
import uk.gov.bristol.send.Utils;

/**
 * Abstract class to allow a generic implementation of the statement selection levels
 * Each inheriting class will have different selectors but the same methods
 */
@Component
public abstract class AccordionPage extends BasePage {

    @FindBy(className = "accordion")
    private WebElement accordionSection;

    @FindBy(id = "maximumNeedLevel")
    private WebElement selectedNeedLevel;

    @FindBy(id = "maxNeedNotSelected")
    private WebElement needNotSelected;

    private WebDriver webDriver;

    private WebElement statementTargeted;
    private WebElement statementLevelA;
    private WebElement statementLevelB;
    private WebElement statementLevelC;

    protected AccordionPage(WebDriver webDriver) {
        super(webDriver);
        this.webDriver = webDriver;
    }

    public void selectNeedsLevel(String level) {
        switch (level) {
            case "Block A" : selectStatementLevelA();
                break;
            case "Block B" : selectStatementLevelB();
                break;
            case "Block C" : selectStatementLevelC();
                break;
            default : throw new SENDException("Level of need was not provided in Scenario");
        }
    }

    public <T extends AccordionPage> void selectStatementLevel(T page, String level) {
        switch (level) {
            case "Block A" : page.selectStatementLevelA();
                break;
            case "Block B" : page.selectStatementLevelB();
                break;
            case "Block C" : page.selectStatementLevelC();
                break;
            default : throw new SENDException("Level of need was not provided in Scenario");
        }
    }

    public WebElement getAccordionSection() {
        return accordionSection;
    }

    public String getSelectedNeedLevel() {
        return selectedNeedLevel.getText();
    }

    public String getNeedNotSelected() {
        return needNotSelected.getText();
    }

    protected void expandSectionSelectLvl(WebElement accordionBtn, WebElement levelSelection) {
        Utils.driverWait(1000);
        accordionBtn.click();
        Utils.jsScrollIntoView(webDriver, levelSelection);
        Utils.jsClick(webDriver, levelSelection);
    }

    protected void clearStatementSelection(WebElement accordionBtn, WebElement clearSelectionBtn) {
        Utils.driverWait(1000);
        accordionBtn.click();
        clearSelectionBtn.click();
    }

    public abstract void selectStatementTargeted();
    public abstract void selectStatementLevelA();
    public abstract void selectStatementLevelB();
    public abstract void selectStatementLevelC();

    public abstract void clickClearSelection();
    
    public void throwLevelUnavailableException() {
        throw new SENDException("Level unavailable to select");
    }
}
