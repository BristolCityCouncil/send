package uk.gov.bristol.send.pages;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import uk.gov.bristol.send.SENDException;

/**
 * Abstract class to allow a generic implementation of the statement selection levels
 * Each inheriting class will have different selectors but the same methods
 */
public abstract class AccordionPage extends BasePage {

    protected AccordionPage(WebDriver webDriver) {
        super(webDriver);
    }

    public <T extends AccordionPage> void selectStatementLevel(T page, String level) {
        switch (level) {
            case "Level A" : page.selectStatementLevelA();
                break;
            case "Level B" : page.selectStatementLevelB();
                break;
            case "Level C" : page.selectStatementLevelC();
                break;
            default : throw new SENDException("Level of need was not provided in Scenario");
        }
    }

    public abstract void selectStatementLevelA();
    public abstract void selectStatementLevelB();
    public abstract void selectStatementLevelC();
}
