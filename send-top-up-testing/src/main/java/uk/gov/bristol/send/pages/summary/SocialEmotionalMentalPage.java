package uk.gov.bristol.send.pages.summary;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;
import uk.gov.bristol.send.pages.BasePage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class SocialEmotionalMentalPage extends BasePage {

    @FindBy(id = "needshref_socialUnderstandingAndInteraction")
    private WebElement suiNeedsLink;

    @FindBy(id = "provisionhref_socialUnderstandingAndInteraction")
    private WebElement suiProvisionsLink;

    @FindBy(id = "needshref_behaviourEmotionalAndMentalHealthNeeds")
    private WebElement bemNeedsLink;

    @FindBy(id = "provisionhref_behaviourEmotionalAndMentalHealthNeeds")
    private WebElement bemProvisionsLink;

    @FindBy(id = "provisionPageLink_socialUnderstandingAndInteraction")
    private WebElement suiSelectedProvisions;

    @FindBy(id = "provisionPageLink_behaviourEmotionalAndMentalHealthNeeds")
    private WebElement bemSelectedProvisions;

    public SocialEmotionalMentalPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public List<WebElement> getAllNeedsLinks() {
        return new ArrayList<>(List.of(suiNeedsLink, bemNeedsLink));
    }

    public List<WebElement> getAllProvisionsLinks() {
        return new ArrayList<>(List.of(suiProvisionsLink, bemProvisionsLink));
    }

    public void clickSuiNeedsLink() {
        suiNeedsLink.click();
    }

    public void clickSuiProvisionsLink() {
        suiProvisionsLink.click();
    }

    public void clickBemNeedsLink() {
        bemNeedsLink.click();
    }

    public void clickBemProvisionsLink() {
        bemProvisionsLink.click();
    }

    public Map<String, String> getSuiSummaryProvisions() {
        return getSummaryProvisions(suiSelectedProvisions);
    }

    public Map<String, String> getBemSummaryProvisions() {
        return getSummaryProvisions(bemSelectedProvisions);
    }
}
