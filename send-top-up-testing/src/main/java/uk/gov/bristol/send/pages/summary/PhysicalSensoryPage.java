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
public class PhysicalSensoryPage extends BasePage {

    @FindBy(id = "needshref_physical")
    private WebElement physicalNeedsLink;

    @FindBy(id = "needshref_vision")
    private WebElement visionNeedsLink;

    @FindBy(id = "needshref_hearing")
    private WebElement hearingNeedsLink;

    @FindBy(id = "needshref_sensoryProcessing")
    private WebElement sensoryNeedsLink;

    @FindBy(id = "provisionhref_physical")
    private WebElement physicalProvisionsLink;

    @FindBy(id = "provisionhref_vision")
    private WebElement visionProvisionsLink;

    @FindBy(id = "provisionhref_hearing")
    private WebElement hearingProvisionsLink;

    @FindBy(id = "provisionhref_sensoryProcessing")
    private WebElement sensoryProvisionsLink;

    @FindBy(id = "provisionPageLink_physical")
    private WebElement medicalSelectedProvisions;

    @FindBy(id = "provisionPageLink_vision")
    private WebElement visionSelectedProvisions;

    @FindBy(id = "provisionPageLink_hearing")
    private WebElement hearingSelectedProvisions;

    @FindBy(id = "provisionPageLink_sensoryProcessing")
    private WebElement sensorySelectedProvisions;

    public PhysicalSensoryPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public List<WebElement> getAllNeedsLinks() {
        return new ArrayList<>(List.of(physicalNeedsLink, visionNeedsLink, hearingNeedsLink, sensoryNeedsLink));
    }

    public List<WebElement> getAllProvisionsLinks() {
        return new ArrayList<>(List.of(physicalProvisionsLink, visionProvisionsLink, hearingProvisionsLink, sensoryProvisionsLink));
    }

    public void clickPhysicalNeedsLink() {
        physicalNeedsLink.click();
    }

    public void clickVisionNeedsLink() {
        visionNeedsLink.click();
    }

    public void clickHearingNeedsLink() {
        hearingNeedsLink.click();
    }

    public void clickSensoryNeedsLink() {
        sensoryNeedsLink.click();
    }

    public void clickPhysicalProvisionsLink() {
        physicalProvisionsLink.click();
    }

    public void clickVisionProvisionsLink() {
        visionProvisionsLink.click();
    }

    public void clickHearingProvisionsLink() {
        hearingProvisionsLink.click();
    }

    public void clickSensoryProvisionsLink() {
        sensoryProvisionsLink.click();
    }

    public Map<String, String> getPhysicalSummaryProvisions() {
        return getSummaryProvisions(medicalSelectedProvisions);
    }

    public Map<String, String> getVisionSummaryProvisions() {
        return getSummaryProvisions(visionSelectedProvisions);
    }

    public Map<String, String> getHearingSummaryProvisions() {
        return getSummaryProvisions(hearingSelectedProvisions);
    }

    public Map<String, String> getSensorySummaryProvisions() {
        return getSummaryProvisions(sensorySelectedProvisions);
    }

}
