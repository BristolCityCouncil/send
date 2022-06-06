package uk.gov.bristol.send.stepdefs;

import org.junit.Assert;
import uk.gov.bristol.send.SENDException;
import uk.gov.bristol.send.pages.AccordionPage;

public class StepUtils {

    private static final String NEED_NOT_SELECTED = "Save the assessment to record or update the highest level of need";
    private static final String NEED_LEVEL_A = "Block A";
    private static final String NEED_LEVEL_B = "Block B";
    private static final String NEED_LEVEL_C = "Block C";

    protected <T extends AccordionPage> void assertSelectionCleared(T page) {
        Assert.assertEquals(NEED_NOT_SELECTED, page.getNeedNotSelected());
    }

    protected <T extends AccordionPage> void selectAllAccordionLevels(T page) {

        try {
            page.selectStatementLevel(page, "Block C");
            page.clickSaveButton();
            Assert.assertEquals(page.getClass().toString(), NEED_LEVEL_C, page.getSelectedNeedLevel());

            page.selectStatementLevel(page, "Block B");
            page.clickSaveButton();
            Assert.assertEquals(page.getClass().toString(), NEED_LEVEL_B, page.getSelectedNeedLevel());

            page.selectStatementLevel(page, "Block A");
            page.clickSaveButton();
            Assert.assertEquals(page.getClass().toString(), NEED_LEVEL_A, page.getSelectedNeedLevel());

            page.selectStatementTargeted();
            page.clickSaveButton();
            Assert.assertEquals(page.getClass().toString(), NEED_NOT_SELECTED, page.getNeedNotSelected());

        } catch (SENDException ex) {
            if (!"Level unavailable to select".equals(ex.getMessage())) {
                throw new SENDException("Error getting page level for: " + page);
            }
            page.clickClearSelection();
            page.clickSaveButton();

        }
    }
}
