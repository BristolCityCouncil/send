package uk.gov.bristol.send.stepdefs;


import io.cucumber.java.After;
import org.openqa.selenium.NoAlertPresentException;
import uk.gov.bristol.send.AppConfig;
import uk.gov.bristol.send.Assessment;
import uk.gov.bristol.send.Utils;
import uk.gov.bristol.send.pages.BasePage;
import uk.gov.bristol.send.pages.ELCNeedsPage;
import uk.gov.bristol.send.pages.ELCProvisionPage;
import uk.gov.bristol.send.pages.HomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import uk.gov.bristol.send.pages.elcneeds.StyleOfCommunicationPage;
import uk.gov.bristol.send.pages.elcprovision.SpecialistProfessionalPage;
import uk.gov.bristol.send.pages.summary.CommunicationInteractionPage;

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
    private StyleOfCommunicationPage styleOfCommunicationPage;

    @After
    public void tearDown() {
        assessment.clearValues();
        clearNeedsAndProvisions();
    }

    // TODO: Data setup/teardown needs done via the database - cannot rely on the application
    private void clearNeedsAndProvisions() {
        homePage.load();
        homePage.clickViewEdit();
        communicationInteractionPage.clickElcNeedsLink();
        Utils.driverWait(2000);
        styleOfCommunicationPage.clickAccordionBtn();
        Utils.driverWait(2000);
        styleOfCommunicationPage.clickClearSelection();
        basePage.clickSaveButton();
        try {
            webDriver.switchTo().alert().accept();
            Utils.driverWait(2000);
        } catch (NoAlertPresentException ex) {
        }
        basePage.clickBackButton();
    }

    @Given("user views assessment without any Needs or Provisions selected")
    public void views_assessment_without_needs_provisions() {
        homePage.load();
        homePage.clickViewEdit();
    }

    @Given("assessment wih Communication and interaction Need {string} selected")
    public void assessment_with_comm_interaction_need(String needLevel) {
        homePage.load();
        homePage.clickViewEdit();
        communicationInteractionPage.clickElcNeedsLink();
        Utils.driverWait(2000);
        styleOfCommunicationPage.clickAccordionBtn();
        Utils.driverWait(2000);

        selectNeedLevel(needLevel);
        basePage.clickSaveButton();
        basePage.clickBackButton();
    }

    @When("user selects Style of communication {string}")
    public void selectNeedLevel(String level) {
        styleOfCommunicationPage.selectStatementLevel(styleOfCommunicationPage, level);
    }

    @Given("I am logged in as an approved user")
    public void user_successfully_logged_in() {
        homePage.load();
    }

    @When("user clicks Create new assessment")
    public void user_clicks_enter() {
        homePage.clickCreateNew();
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
    }

    @When("user clicks the Back button")
    public void clicks_the_back_button() {
        basePage.clickBackButton();
        Utils.driverWait(2000);
    }
}
