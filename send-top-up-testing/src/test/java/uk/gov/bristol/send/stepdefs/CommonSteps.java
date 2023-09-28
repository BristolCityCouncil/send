package uk.gov.bristol.send.stepdefs;

import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;

import org.openqa.selenium.*;
import uk.gov.bristol.send.AppConfig;
import uk.gov.bristol.send.Assessment;
import uk.gov.bristol.send.Utils;
import uk.gov.bristol.send.pages.BasePage;
import uk.gov.bristol.send.pages.HomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import uk.gov.bristol.send.pages.NeedsPage;
import uk.gov.bristol.send.pages.OverviewPage;
import uk.gov.bristol.send.pages.cognitionlearning.needsale.QualificationsEmployabilityPage;
import uk.gov.bristol.send.pages.comminteraction.needselc.StyleOfCommunicationPage;
import uk.gov.bristol.send.pages.socialemotional.needssui.SocialInterestPage;
import uk.gov.bristol.send.pages.summary.CognitionLearningPage;
import uk.gov.bristol.send.pages.summary.CommunicationInteractionPage;
import uk.gov.bristol.send.pages.summary.PhysicalSensoryPage;
import uk.gov.bristol.send.pages.summary.SocialEmotionalMentalPage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@CucumberContextConfiguration
@ContextConfiguration(classes = AppConfig.class)
public class CommonSteps {

    @Autowired
    private HomePage homePage;

    @Autowired
    private WebDriver webDriver;

    @Autowired
    private BasePage basePage;

    @Autowired
    private Assessment assessment;

    @Autowired
    private CommunicationInteractionPage communicationInteractionPage;

    @Autowired
    private CognitionLearningPage cognitionLearningPage;

    @Autowired
    private PhysicalSensoryPage physicalSensoryPage;

    @Autowired
    private SocialEmotionalMentalPage socialEmotionalMentalPage;

    @Autowired
    private QualificationsEmployabilityPage qualificationsEmployabilityPage;

    @Autowired
    private StyleOfCommunicationPage styleOfCommunicationPage;

    @Autowired
    private SocialInterestPage socialInterestPage;

    @Autowired
    private OverviewPage overviewPage;


    private static final String NEED_NOT_SELECTED = "Save the assessment to record or update the highest level of need";
    private static final String NEED_LEVEL_A = "A";
    private static final String NEED_LEVEL_B = "B";
    private static final String NEED_LEVEL_C = "C";

    private static final String ASSESSMENT_UPN = "A991111111119";

    private static final String OVERVIEW_TITLE = "Overview";

    @Before
    /**
     * Where an assessment is exists the state is inconsistent so it must be removed.
     * In either case a new assessment must be created before each test run.
     */
    public void setup() {
        homePage.load();
        try {
            homePage.getAssessmentUPN().getText();
            clearNeedsAndProvisions();
        } catch (NoSuchElementException ex) {
            homePage.typeUPN(ASSESSMENT_UPN);
            homePage.typeSchoolName("Red Brick School");
            homePage.clickCreateNew();
            Utils.driverWait(2000);
            communicationInteractionPage.clickBackButton();
            Utils.driverWait(2000);
        }
    }

    @After
    /**
     * Depending on the previous test result the HomePage may not be the final page. The isHomePage() method
     * is intended to return to the HomePage so the Delete assessment functionality can be used to reset state.
     */
    public void tearDown() {
        try {
            isHomepage();
            homePage.getAssessmentUPN().getText();
            clearNeedsAndProvisions();
        } catch (NoSuchElementException ex) {
        }

    }

    @AfterStep
    public void takeSnapShot(Scenario scenario) throws Exception{

        if (scenario.isFailed()) {

            String scenarioFileName = scenario.getName().substring(0, scenario.getName().indexOf('-'));

            TakesScreenshot scrShot = (TakesScreenshot) webDriver;
            File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

            // Embedded for generic cucumber.html report
            final byte[] embedScreenshot = scrShot.getScreenshotAs(OutputType.BYTES);
            scenario.attach(embedScreenshot, "image/png", scenarioFileName);

            // Separate file for Azure
            File DestFile = new File("./errors/" + scenarioFileName + ".png");
            FileUtils.copyFile(SrcFile, DestFile);
        }

        if (scenario.isFailed()) {
            String scenarioFileName = scenario.getName().substring(0, scenario.getName().indexOf('-'));
            TakesScreenshot scrShot = (TakesScreenshot) webDriver;
            final byte[] screenshot = scrShot.getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenarioFileName);
        }
    }

    private boolean isHomepage() {
        for (int numberOfTries = 0; numberOfTries < 4; numberOfTries++) {
            try {
                homePage.getCreateNewButton().getText();
                return true;
            } catch (NoSuchElementException nfEx) {
                communicationInteractionPage.clickBackButton();
                Utils.driverWait(1000);
            }
        }
        return false;
    }

    // TODO: Data setup/teardown needs done via the database - cannot rely on the application
    private void clearNeedsAndProvisions() {
        homePage.clickDelete();
        webDriver.switchTo().alert().accept();
        Utils.driverWait(1000);
    }

    @When("I navigate to the Overview page")
    public void navigate_to_overview() {
        basePage.clickOverviewLink();    
    }

    @When("the Overview page contains the associated data")
    public void overview_page_has_data() {
        Assert.assertEquals(OVERVIEW_TITLE, overviewPage.getPageTitle());
    }


    @Given("the Needs page has the following accordion sections:")
    public void needs_page_has_sections(List<String> accordionSections) {
        List<WebElement> sections = styleOfCommunicationPage.getAccordionSection().findElements(By.className("accordion-button"));
        assertHeadingsMatchExpected(sections, accordionSections);
    }

    @Given("the following assessment area headings are displayed:")
    public void the_assessment_area_headings_are_displayed(List<String> areaHeadings) {
        List<WebElement> webElements = basePage.getSummaryAssessment().findElements(By.tagName("h2"));
        assertHeadingsMatchExpected(webElements, areaHeadings);
    }

    @Given("page title contains the text {string}")
    public void page_title_displayed(String pageTitle) {
        Assert.assertTrue("Page title not correct - expected '" + pageTitle + "' but found '" + basePage.getPageTitle() + "'",
                basePage.getPageTitle().contains(pageTitle));
    }



    private void assertHeadingsMatchExpected(List<WebElement> webElements, List<String> areaHeadings) {
        Assert.assertEquals("Expected number of elements incorrect", areaHeadings.size(), webElements.size());

        List<String> elementHeadings = new ArrayList<>();
        for (WebElement element : webElements) {
            elementHeadings.add(element.getText());
        }
        elementHeadings.removeAll(areaHeadings);
        Assert.assertEquals("Unexpected headings were found: " + elementHeadings, 0, elementHeadings.size());
    }

    @Given("all sub-area Needs links are visible and enabled")
    public void sub_area_needs_links_visible_enabled() {
        assertLinkDisplayedEnabled(communicationInteractionPage.getAllNeedsLinks(), true);
        assertLinkDisplayedEnabled(cognitionLearningPage.getAllNeedsLinks(), true);
        assertLinkDisplayedEnabled(physicalSensoryPage.getAllNeedsLinks(), true);
        assertLinkDisplayedEnabled(socialEmotionalMentalPage.getAllNeedsLinks(), true);
    }

    @Given("all sub-area Provisions links are visible and disabled")
    public void sub_area_provisions_visible_disabled() {
        assertLinkDisplayedEnabled(communicationInteractionPage.getAllProvisionsLinks(), false);
        assertLinkDisplayedEnabled(cognitionLearningPage.getAllProvisionsLinks(), false);
        assertLinkDisplayedEnabled(physicalSensoryPage.getAllProvisionsLinks(), false);
        assertLinkDisplayedEnabled(socialEmotionalMentalPage.getAllProvisionsLinks(), false);
    }

    private void assertLinkDisplayedEnabled(List<WebElement> elementList, boolean isEnabled) {
        for (WebElement link : elementList) {
            Assert.assertTrue(link.getText() + " - link was not displayed", link.isDisplayed());
            if (isEnabled) {
                Assert.assertNull(link.getText() + " - link was not enabled", link.getAttribute("disabled"));
            } else {
                Assert.assertEquals(link.getText() + " - link should be disabled", "true", link.getAttribute("disabled"));
            }
        }
    }

    @Given("user views assessment without any Needs or Provisions selected")
    public void views_assessment_without_needs_provisions() {
        homePage.load();
        Utils.driverWait(2000);
        homePage.clickViewEdit();
    }

    @Given("user views assessment without any Needs or Provisions selected in phase two")
    public void views_assessment_without_needs_provisions_in_phase_two() {
        homePage.setPhaseTwo(true);       
        homePage.load();
        Utils.driverWait(2000);
        homePage.clickViewEdit();
    }

    @Given("assessment with Cognition and learning Need {string} selected")
    public void assessment_with_cognition_learning_need(String needLevel) {
        homePage.load();
        //This only runs in the first scenario. Wait needed to get page loaded for first time.
        Utils.driverWait(2000);
        homePage.clickViewEdit();

        assessment.setNeedsPage(NeedsPage.ALE);
        assessment.setAllProvisions(cognitionLearningPage.getAleSummaryProvisions());

        cognitionLearningPage.clickAleNeedsLink();
        Utils.driverWait(2000);

        selectQualificationEmployableNeedLevel(needLevel);
        basePage.clickSaveButton();
        basePage.clickBackButton();
    }

    @Given("assessment with Communication and interaction Need {string} selected")
    public void assessment_with_comm_interaction_need(String needLevel) {
        homePage.load();
        homePage.clickViewEdit();
        communicationInteractionPage.clickElcNeedsLink();
        Utils.driverWait(2000);

        selectNeedLevel(needLevel);

        basePage.clickSaveButton();
        basePage.clickBackButton();
    }

    @When("user selects Qualifications and employability {string}")
    public void selectQualificationEmployableNeedLevel(String level) {
        qualificationsEmployabilityPage.selectNeedsLevel(level);
    }

    @When("user selects Style of communication {string}")
    public void selectNeedLevel(String level) {
        styleOfCommunicationPage.selectNeedsLevel(level);
    }

    @Given("I am logged in as an approved user")
    public void user_successfully_logged_in() {
        homePage.load();
    }

    @When("user clicks Create new assessment")
    public void user_clicks_enter() {
        homePage.clickCreateNew();
    }

    @Given("the following UPN exists: {string}")
    public void the_following_upn_exists(String string) {
        Assert.assertTrue("Expected text not found in string: " + homePage.getAssessmentUPN().getText(), homePage.getAssessmentUPN().getText().contains(ASSESSMENT_UPN));
    }

    @Given("user clicks Delete link under assessment")
    public void user_click_delete_link_under_assessment() {
        homePage.clickDelete();
    }

    @Given("popup confirmation message displays corresponding UPN: {string}")
    public void popup_confirmation_message_displays_corresponding_upn(String string) {
        String alertText = webDriver.switchTo().alert().getText();
        Assert.assertEquals("Are you sure you want to permanently delete the assessment for UPN: " + ASSESSMENT_UPN + "?" , alertText);

    }

    @Given("assessment is removed with UPN: {string}")
    public void assessment_is_removed_with_upn(String assessmentId) {
        try {
            homePage.getAssessmentUPN().getText();
            Assert.fail("Assessment was not removed as expected: " + assessmentId);
        } catch (NoSuchElementException ex) {
            Assert.assertTrue(true);
        }
    }

    @Given("user clicks View or edit assessment")
    public void user_clicks_view_or_edit_assessment() {
        homePage.clickViewEdit();
    }

    @When("I click OK in the confirmation popup")
    public void click_ok_in_confirmation_popup() {
        webDriver.switchTo().alert().accept();
        assessment.saveTransientValues();
        Utils.driverWait(2000);
    }

    @When("I click Cancel in the confirmation popup")
    public void click_cancel_in_confirmation_popup() {
        webDriver.switchTo().alert().dismiss();
        Utils.driverWait(2000);
    }

    @When("user clicks the Save button")
    public void clicks_the_save_button() {
        basePage.clickSaveButton();
        Utils.driverWait(2000);
    }

    @When("user clicks the Back button")
    public void clicks_the_back_button() {
        basePage.clickBackButton();
        Utils.driverWait(2000);
    }
}
