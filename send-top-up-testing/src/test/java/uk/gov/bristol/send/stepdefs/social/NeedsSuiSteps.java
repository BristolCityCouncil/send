package uk.gov.bristol.send.stepdefs.social;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.bristol.send.SENDException;
import uk.gov.bristol.send.pages.AccordionPage;
import uk.gov.bristol.send.pages.socialemotional.needssui.*;
import uk.gov.bristol.send.stepdefs.StepUtils;

import java.util.ArrayList;
import java.util.List;

public class NeedsSuiSteps extends StepUtils {

    @Autowired
    private SocialInterestPage socialInterestPage;

    @Autowired
    private SocialAnxietyPage socialAnxietyPage;

    @Autowired
    private SocialUnderstandingPage socialUnderstandingPage;

    @Autowired
    private SocialInteractionPage socialInteractionPage;

    @Autowired
    private BelongingPage belongingPage;

    @Autowired
    private RomanticPage romanticPage;

    @Autowired
    private GenderPage genderPage;

    private static final String SOCIAL_INTEREST = "Social interest";
    private static final String SOCIAL_ANXIETY = "Social anxiety";
    private static final String SOCIAL_UNDERSTANDING = "Social understanding";
    private static final String SOCIAL_INTERACTION = "Social interaction and communication";
    private static final String SOCIAL_INCLUSION = "Belonging and social inclusion";
    private static final String ROMANTIC_RELATIONSHIPS = "Sex and romantic relationships";
    private static final String GENDER_IDENTITY = "Gender identity";

    @When("user selects Social understanding and interaction need - {string} - {string}")
    public void selectNeedLevel(String needPage, String level) {
        switch (needPage) {
            case SOCIAL_INTEREST: socialInterestPage.selectNeedsLevel(level);
                break;
            case SOCIAL_ANXIETY: socialAnxietyPage.selectNeedsLevel(level);
                break;
            case SOCIAL_UNDERSTANDING: socialUnderstandingPage.selectNeedsLevel(level);
                break;
            case SOCIAL_INTERACTION: socialInteractionPage.selectNeedsLevel(level);
                break;
            case SOCIAL_INCLUSION: belongingPage.selectNeedsLevel(level);
                break;
            case ROMANTIC_RELATIONSHIPS: romanticPage.selectNeedsLevel(level);
                break;
            case GENDER_IDENTITY: genderPage.selectNeedsLevel(level);
                break;
            default : throw new SENDException("Needs sub-area was not found");
        }

    }

    @Then("the Social Understanding Level of need is identified as {string}")
    public void the_social_understanding_level_of_need_is_identified_as(String string) {
        Assert.assertEquals(string, socialInterestPage.getSelectedNeedLevel());
    }

    @Then("the Social Understanding level of need has been cleared")
    public void the_social_understanding_level_of_need_has_been_cleared() {
        assertSelectionCleared(socialInterestPage);
    }

    @When("all selected Social understanding and interaction need levels are displayed at the bottom of page")
    public void selected_saved_need_level_displayed() {
        List<AccordionPage> pages = new ArrayList<>(List.of(belongingPage, genderPage, romanticPage, socialAnxietyPage,
                socialInteractionPage, socialInterestPage, socialUnderstandingPage));
        for (AccordionPage page : pages) {
            selectAllAccordionLevels(page);
        }
    }
}
